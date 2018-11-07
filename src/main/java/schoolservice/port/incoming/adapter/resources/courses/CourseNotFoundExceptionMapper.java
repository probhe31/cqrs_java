package schoolservice.port.incoming.adapter.resources.courses;

import schoolservice.service.course.CourseNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class CourseNotFoundExceptionMapper implements ExceptionMapper<CourseNotFoundException> {

    @Override
    public Response toResponse(CourseNotFoundException exception) {
        return Response.status(NOT_FOUND).build();
    }
}
