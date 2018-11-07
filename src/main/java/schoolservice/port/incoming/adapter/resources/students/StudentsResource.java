package schoolservice.port.incoming.adapter.resources.students;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.UriBuilder.fromResource;

import schoolservice.domain.model.student.Email;
import schoolservice.domain.model.student.Student;
import schoolservice.service.student.StudentService;
import schoolservice.service.student.EnrollStudentCommand;

import java.net.URI;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/students")
public class StudentsResource {

    private StudentService clientService;

    public StudentsResource(StudentService clientService) {
        this.clientService = checkNotNull(clientService);
    }

    @POST
    public Response post(@Valid StudentDto newStudentDto) {
        EnrollStudentCommand enrollClientCommand = new EnrollStudentCommand(
        		newStudentDto.getName(), new Email(newStudentDto.getEmail()));
        Student student = clientService.process(enrollClientCommand);
        URI studentUri = fromResource(StudentResource.class).build(student.getId());
        return Response.created(studentUri).build();
    }
}
