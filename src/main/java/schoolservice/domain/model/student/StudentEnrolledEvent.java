package schoolservice.domain.model.student;

import static com.google.common.base.Preconditions.checkNotNull;

import schoolservice.domain.model.Event;
import java.util.UUID;
import org.joda.time.DateTime;

public class StudentEnrolledEvent extends Event {

    private final String name;
    private final String email;

    public StudentEnrolledEvent(UUID aggregateId, DateTime timestamp, int version, String name, Email email) {
        super(aggregateId, timestamp, version);
        this.name = checkNotNull(name);
        this.email = checkNotNull(email).getValue();
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return new Email(email);
    }
}
