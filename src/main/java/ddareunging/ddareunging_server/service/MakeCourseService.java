package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Course;
import ddareunging.ddareunging_server.dto.MakeNewCourseRequest;
import ddareunging.ddareunging_server.dto.MakeNewCourseResponse;
import ddareunging.ddareunging_server.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
        log.info("service 실행 -1");
        Course course = courseRepository.save(Course.of(makeNewCourseRequest));
        log.info("service 실행 -2");
        return new MakeNewCourseResponse(course.getCourse_id());
    }
}