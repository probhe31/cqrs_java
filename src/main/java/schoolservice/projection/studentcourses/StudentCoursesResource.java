package schoolservice.projection.studentcourses;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import io.dropwizard.jersey.params.UUIDParam;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("students/{id}/courses")
public class StudentCoursesResource {

    private final CoursesRepository coursesRepository;

    public StudentCoursesResource(CoursesRepository coursesRepository) {
        this.coursesRepository = checkNotNull(coursesRepository);
    }

    @GET
    public Response get(@PathParam("id") UUIDParam studentId) {
        List<CourseProjection> accounts = coursesRepository.getCourses(studentId.get());
        return Response.ok(accounts).build();
    }
}
