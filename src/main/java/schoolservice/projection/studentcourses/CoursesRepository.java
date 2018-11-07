package schoolservice.projection.studentcourses;

import java.util.List;
import java.util.UUID;

public interface CoursesRepository {

    void save(CourseProjection courseProjection);

    void updateCredits(UUID courseId, int credits, int version);

    List<CourseProjection> getCourses(UUID studentId);
}
