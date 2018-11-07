package schoolservice.domain.model.student;

import schoolservice.domain.model.Aggregate;
import schoolservice.domain.model.Event;

import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.joda.time.DateTime.now;
import static org.joda.time.DateTimeZone.UTC;

public class Student extends Aggregate {

    private String name;
    private Email email;

    public Student(UUID id, String name, Email email) {
        super(id);
        validateName(name);
        validateEmail(email);
        StudentEnrolledEvent studentEnrolledEvent = new StudentEnrolledEvent(
                id, now(UTC), getNextVersion(), name, email);
        applyNewEvent(studentEnrolledEvent);
    }

    public Student(UUID id, List<Event> eventStream) {
        super(id, eventStream);
    }

    public void update(String name, Email email) {
        StudentUpdatedEvent studentUpdatedEvent = new StudentUpdatedEvent(
                getId(), now(UTC), getNextVersion(), name, email);
        applyNewEvent(studentUpdatedEvent);
    }

    @SuppressWarnings("unused")
    public void apply(StudentEnrolledEvent event) {
        this.name = event.getName();
        this.email = event.getEmail();
    }

    @SuppressWarnings("unused")
    private void apply(StudentUpdatedEvent event) {
        this.name = event.getName();
        this.email = event.getEmail();
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    private void validateName(String name) {
        checkArgument(isNotBlank(name));
    }

    private void validateEmail(Email email) {
        checkNotNull(email);
    }
}
