package schoolservice.service.course;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;

import schoolservice.domain.model.Event;
import schoolservice.domain.model.EventStore;
import schoolservice.domain.model.OptimisticLockingException;
import schoolservice.domain.model.course.Course;
import schoolservice.domain.model.course.NonSufficientVacantsException;
import schoolservice.service.Retrier;
import com.google.common.eventbus.EventBus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class CourseService {

    private final EventStore eventStore;
    private final EventBus eventBus;
    private final Retrier conflictRetrier;

    public CourseService(EventStore eventStore, EventBus eventBus) {
        this.eventStore = checkNotNull(eventStore);
        this.eventBus = checkNotNull(eventBus);
        int maxAttempts = 3;
        this.conflictRetrier = new Retrier(singletonList(OptimisticLockingException.class), maxAttempts);
    }

    public Optional<Course> loadAccount(UUID id) {
        List<Event> eventStream = eventStore.load(id);
        if (eventStream.isEmpty()) return Optional.empty();
        return Optional.of(new Course(id, eventStream));
    }

    public Course process(OpenCourseCommand command) {
        Course account = new Course(randomUUID());
        storeAndPublishEvents(account);
        return account;
    }

    private Course process(UUID accountId, Consumer<Course> consumer)
            throws CourseNotFoundException, OptimisticLockingException {

        return conflictRetrier.get(() -> {
            Optional<Course> possibleAccount = loadAccount(accountId);
            Course account = possibleAccount.orElseThrow(() -> new CourseNotFoundException(accountId));
            consumer.accept(account);
            storeAndPublishEvents(account);
            return account;
        });
    }

    private void storeAndPublishEvents(Course account) throws OptimisticLockingException {
        eventStore.store(account.getId(), account.getNewEvents(), account.getBaseVersion());
        account.getNewEvents().forEach(eventBus::post);
    }
}
