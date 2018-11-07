package schoolservice.service.course;

import java.util.UUID;

import static java.lang.String.format;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(UUID id) {
        super(format("Account with id '%s' could not be found", id));
    }
}
