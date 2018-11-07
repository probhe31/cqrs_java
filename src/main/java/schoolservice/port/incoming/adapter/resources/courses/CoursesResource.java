package schoolservice.port.incoming.adapter.resources.courses;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.UriBuilder.fromResource;

import schoolservice.domain.model.course.Course;
import schoolservice.service.course.CourseService;
import schoolservice.service.course.OpenCourseCommand;
import java.net.URI;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/courses")
public class CoursesResource {

    private final CourseService accountService;

    public CoursesResource(CourseService accountService) {
        this.accountService = checkNotNull(accountService);
    }

    @POST
    public Response post(@Valid CourseDto courseDto) {
        OpenCourseCommand command = new OpenCourseCommand();
        Course course = accountService.process(command);
        URI courseUri = fromResource(CourseResource.class).build(course.getId());
        return Response.created(courseUri).build();
    }
}
