package schoolservice.service.student;

import java.util.UUID;

import static java.lang.String.format;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(UUID id) {
        super(format("Client with id '%s' could not be found", id));
    }
}
