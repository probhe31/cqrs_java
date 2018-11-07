package schoolservice.bootstrap;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.logging.Level.INFO;
import static java.util.logging.Logger.getLogger;
import static org.glassfish.jersey.logging.LoggingFeature.DEFAULT_LOGGER_NAME;
import static org.glassfish.jersey.logging.LoggingFeature.Verbosity.PAYLOAD_ANY;

import schoolservice.domain.model.EventStore;

import schoolservice.port.incoming.adapter.resources.OptimisticLockingExceptionMapper;
import schoolservice.port.incoming.adapter.resources.courses.CourseNotFoundExceptionMapper;
import schoolservice.port.incoming.adapter.resources.courses.CourseResource;
import schoolservice.port.incoming.adapter.resources.students.StudentResource;
import schoolservice.port.incoming.adapter.resources.students.StudentsResource;
import schoolservice.port.incoming.adapter.resources.courses.CoursesResource;
import schoolservice.port.outgoing.adapter.eventstore.InMemoryEventStore;
import schoolservice.projection.studentcourses.InMemoryCoursesRepository;
import schoolservice.projection.studentcourses.CoursesListener;
import schoolservice.projection.studentcourses.CoursesRepository;
import schoolservice.projection.studentcourses.StudentCoursesResource;
import schoolservice.service.course.CourseService;
import schoolservice.service.student.StudentService;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.logging.LoggingFeature;

public class SchoolServiceApplication extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new SchoolServiceApplication().run(args);
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        registerFilters(environment);
        registerExceptionMappers(environment);
        registerHypermediaSupport(environment);
        registerResources(environment);
    }

    private void registerFilters(Environment environment) {
        environment.jersey().register(new LoggingFeature(getLogger(DEFAULT_LOGGER_NAME), INFO, PAYLOAD_ANY, 1024));
    }

    private void registerExceptionMappers(Environment environment) {
        environment.jersey().register(CourseNotFoundExceptionMapper.class);
        environment.jersey().register(OptimisticLockingExceptionMapper.class);
    }

    private void registerHypermediaSupport(Environment environment) {
        environment.jersey().getResourceConfig().register(DeclarativeLinkingFeature.class);
    }

    private void registerResources(Environment environment) {
        EventStore eventStore = new InMemoryEventStore();
        EventBus eventBus = new AsyncEventBus(newSingleThreadExecutor());

        // domain model
        CourseService courseService = new CourseService(eventStore, eventBus);
        environment.jersey().register(new CoursesResource(courseService));
        environment.jersey().register(new CourseResource(courseService));

        StudentService studentService = new StudentService(eventStore);
        environment.jersey().register(new StudentsResource(studentService));
        environment.jersey().register(new StudentResource(studentService));

        // read model
        CoursesRepository coursesRepository = new InMemoryCoursesRepository();
        eventBus.register(new CoursesListener(coursesRepository));
        environment.jersey().register(new StudentCoursesResource(coursesRepository));
    }
}
