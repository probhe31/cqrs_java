package schoolservice.service.student;


import schoolservice.domain.model.student.Email;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class UpdateStudentCommand {

    private final UUID id;
    private final String name;
    private final Email email;

    public UpdateStudentCommand(UUID id, String name, Email email) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.email = checkNotNull(email);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }
}
