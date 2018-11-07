package schoolservice.domain.model.course;

import schoolservice.domain.model.Event;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class CourseCreditsChangedEvent extends Event {
    
    private final int credits;

    public CourseCreditsChangedEvent(UUID aggregateId, DateTime timestamp, int version, int credits) {
        super(aggregateId, timestamp, version);
        this.credits = checkNotNull(credits);
    }

    public int getCredits() {
        return credits;
    }
}
