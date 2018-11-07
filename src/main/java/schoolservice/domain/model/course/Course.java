package schoolservice.domain.model.course;

import schoolservice.domain.model.Aggregate;
import schoolservice.domain.model.Event;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.math.BigDecimal.ZERO;
import static org.joda.time.DateTime.now;
import static org.joda.time.DateTimeZone.UTC;

public class Course extends Aggregate {

    private int credits;    

    public Course(UUID id) {
        super(id);
        CourseOpenedEvent accountOpenedEvent = new CourseOpenedEvent(
                id, now(UTC), getNextVersion(), 0);
        applyNewEvent(accountOpenedEvent);
    }

    public Course(UUID id, List<Event> eventStream) {
        super(id, eventStream);
    }


    @SuppressWarnings("unused")
    private void apply(CourseOpenedEvent event) {        
        credits = event.getCredits();
    }

    @SuppressWarnings("unused")
    private void apply(CourseCreditsChangedEvent event) {
    	credits = event.getCredits();
    }
   
    public int getCredits() {
        return credits;
    }
    
}
