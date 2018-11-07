package schoolservice.projection.studentcourses;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class CourseProjection {

    private final UUID courseId;    
    private final int credits;
    private final int version;

    public CourseProjection(UUID accountId, int credits, int version) {
        this.courseId = checkNotNull(accountId);        
        this.credits = checkNotNull(credits);
        this.version = version;
    }

    public UUID getCourseId() {
        return courseId;
    }



    public int getCredits() {
        return credits;
    }

    public int getVersion() {
        return version;
    }
    
    
}
