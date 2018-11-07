package schoolservice.service.student;

import static com.google.common.base.Preconditions.checkNotNull;

import schoolservice.domain.model.student.Email;

public class EnrollStudentCommand {

    private final String name;
    private final Email email;

    public EnrollStudentCommand(String name, Email email) {
        this.name = checkNotNull(name);
        this.email = checkNotNull(email);
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }
}
