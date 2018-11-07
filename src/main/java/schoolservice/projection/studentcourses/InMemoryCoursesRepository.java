package schoolservice.projection.studentcourses;

import static java.util.Collections.emptyMap;

import com.google.common.collect.ImmutableList;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCoursesRepository implements CoursesRepository {

    private final Map<UUID, Map<UUID, CourseProjection>> studentCourses = new ConcurrentHashMap<>();
    private final Map<UUID, UUID> accountClientIndex = new ConcurrentHashMap<>();

    @Override
    public void save(CourseProjection courseProjection) {
    	
    }

    @Override
    public void updateCredits(UUID courseId, int credits, int version) {
        UUID studentId = accountClientIndex.get(courseId);
        studentCourses.get(studentId).merge(
        		courseId,
            new CourseProjection(courseId, credits, version),
            (oldValue, value) -> value.getVersion() > oldValue.getVersion() ? value : oldValue);
    }

    @Override
    public List<CourseProjection> getCourses(UUID studentId) {
        Map<UUID, CourseProjection> courses = studentCourses.getOrDefault(studentId, emptyMap());
        return ImmutableList.copyOf(courses.values());
    }

    private Map<UUID, CourseProjection> newCoursesMap(CourseProjection courseProjection) {
        return new HashMap<UUID, CourseProjection>() {
            { put(courseProjection.getCourseId(), courseProjection); }
        };
    }
}
