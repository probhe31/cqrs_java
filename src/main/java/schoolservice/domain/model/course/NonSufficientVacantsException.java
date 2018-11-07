package schoolservice.domain.model.course;

import java.math.BigDecimal;
import java.util.UUID;

import static java.lang.String.format;

public class NonSufficientVacantsException extends RuntimeException {

    public NonSufficientVacantsException(UUID courseId, int vacants, int amount) {
        super(format("Not enought vacants, there is only '%s' in course '%s'", vacants, courseId));
    }
}
