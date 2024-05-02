package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Course;
import ddareunging.ddareunging_server.dto.MakeNewCourseRequest;
import ddareunging.ddareunging_server.dto.MakeNewCourseResponse;
import ddareunging.ddareunging_server.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MakeCourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public MakeCourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public MakeNewCourseResponse makeCourse(MakeNewCourseRequest makeNewCourseRequest) {
        Course course = courseRepository.save(Course.of(makeNewCourseRequest));
        return new MakeNewCourseResponse(course.getCourse_id());
    }
}