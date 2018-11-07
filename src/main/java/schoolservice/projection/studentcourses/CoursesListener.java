package schoolservice.projection.studentcourses;

import static com.google.common.base.Preconditions.checkNotNull;

import schoolservice.domain.model.course.CourseCreditsChangedEvent;
import schoolservice.domain.model.course.CourseOpenedEvent;
import com.google.common.eventbus.Subscribe;

public class CoursesListener {

    private final CoursesRepository coursesRepository;

    public CoursesListener(CoursesRepository accountsRepository) {
        this.coursesRepository = checkNotNull(accountsRepository);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handle(CourseOpenedEvent event) {
        CourseProjection accountProjection = new CourseProjection(
            event.getAggregateId(), event.getCredits(), event.getVersion());
        coursesRepository.save(accountProjection);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handle(CourseCreditsChangedEvent event) {
    	coursesRepository.updateCredits(event.getAggregateId(), event.getCredits(), event.getVersion());
    }

}
