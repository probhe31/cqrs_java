package schoolservice.port.incoming.adapter.resources.courses;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import schoolservice.domain.model.course.Course;
import schoolservice.service.course.CourseService;
import io.dropwizard.jersey.params.UUIDParam;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/courses/{id}")
public class CourseResource {

    private final CourseService accountService;

    public CourseResource(CourseService accountService) {
        this.accountService = checkNotNull(accountService);
    }

    @GET
    public Response get(@PathParam("id") UUIDParam accountId) {
        Optional<Course> possibleAccount = accountService.loadAccount(accountId.get());
        if (!possibleAccount.isPresent()) return Response.status(NOT_FOUND).build();
        CourseDto accountDto = toDto(possibleAccount.get());
        return Response.ok(accountDto).build();
    }

    private CourseDto toDto(Course course) {
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setCredits(course.getCredits());        
        return dto;
    }
}
