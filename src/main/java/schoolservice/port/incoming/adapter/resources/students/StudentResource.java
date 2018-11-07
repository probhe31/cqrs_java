package schoolservice.port.incoming.adapter.resources.students;

import schoolservice.domain.model.student.Student;
import schoolservice.service.student.StudentService;
import schoolservice.service.student.UpdateStudentCommand;
import schoolservice.domain.model.student.Email;

import io.dropwizard.jersey.params.UUIDParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/students/{id}")
public class StudentResource {

    private StudentService studentService;

    public StudentResource(StudentService clientService) {
        this.studentService = checkNotNull(clientService);
    }

    @GET
    public Response get(@PathParam("id") UUIDParam clientId) {
        Optional<Student> possibleClient = studentService.loadClient(clientId.get());
        if (!possibleClient.isPresent()) return Response.status(NOT_FOUND).build();
        StudentDto clientDto = toDto(possibleClient.get());
        return Response.ok(clientDto).build();
    }

    @PUT
    public Response put(@PathParam("id") UUIDParam clientId, @Valid @NotNull StudentDto studentDto) {
        UpdateStudentCommand command = new UpdateStudentCommand(
                clientId.get(), studentDto.getName(), new Email(studentDto.getEmail()));
        studentService.process(command);
        return Response.noContent().build();
    }

    private StudentDto toDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail().getValue());
        return dto;
    }
}
