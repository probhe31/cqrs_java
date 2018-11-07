package schoolservice.service.student;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;

import schoolservice.domain.model.Event;
import schoolservice.domain.model.EventStore;
import schoolservice.domain.model.OptimisticLockingException;
import schoolservice.domain.model.student.Student;
import schoolservice.service.Retrier;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class StudentService {

    private final EventStore eventStore;
    private final Retrier conflictRetrier;

    public StudentService(EventStore eventStore) {
        this.eventStore = checkNotNull(eventStore);
        int maxAttempts = 3;
        this.conflictRetrier = new Retrier(singletonList(OptimisticLockingException.class), maxAttempts);
    }

    public Student process(EnrollStudentCommand command) {
        Student client = new Student(randomUUID(), command.getName(), command.getEmail());
        storeEvents(client);
        return client;
    }

    public Optional<Student> loadClient(UUID id) {
        List<Event> eventStream = eventStore.load(id);
        if (eventStream.isEmpty()) return Optional.empty();
        return Optional.of(new Student(id, eventStream));
    }

    public void process(UpdateStudentCommand command) {
        process(command.getId(), c -> c.update(command.getName(), command.getEmail()));
    }

    private Student process(UUID clientId, Consumer<Student> consumer)
            throws StudentNotFoundException, OptimisticLockingException {

        return conflictRetrier.get(() -> {
            Optional<Student> possibleClient = loadClient(clientId);
            Student client = possibleClient.orElseThrow(() -> new StudentNotFoundException(clientId));
            consumer.accept(client);
            storeEvents(client);
            return client;
        });
    }

    private void storeEvents(Student client) {
        eventStore.store(client.getId(), client.getNewEvents(), client.getBaseVersion());
    }
}
