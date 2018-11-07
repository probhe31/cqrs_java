package schoolservice.port.incoming.adapter.resources.courses;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static java.math.BigDecimal.ROUND_HALF_UP;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.UUID;

public class CourseDto {

    @JsonProperty(access = READ_ONLY)
    private UUID id;

    @JsonProperty(access = READ_ONLY)
    private int credits;


    @SuppressWarnings("unused")
    public UUID getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(UUID id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public int getCredits() {
        return credits;
    }

    @SuppressWarnings("unused")
    public void setCredits(int credits) {
        this.credits = credits;
    }

}
