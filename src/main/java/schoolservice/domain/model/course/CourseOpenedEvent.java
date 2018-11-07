package schoolservice.domain.model.course;

import static com.google.common.base.Preconditions.checkNotNull;

import schoolservice.domain.model.Event;
import java.math.BigDecimal;
import java.util.UUID;
import org.joda.time.DateTime;

public class CourseOpenedEvent extends Event {
    
    private final int credits;

    public CourseOpenedEvent(UUID aggregateId, DateTime timestamp, int version, int credits) {
        super(aggregateId, timestamp, version);        
        this.credits = checkNotNull(credits);
    }
    
    public int getCredits() {
        return credits;
    }
}
