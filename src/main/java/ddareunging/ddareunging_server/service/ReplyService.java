package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Course;
import ddareunging.ddareunging_server.domain.Reply;
import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.dto.ReplyRequestDto;
import ddareunging.ddareunging_server.repository.CourseRepository;
import ddareunging.ddareunging_server.repository.ReplyRepository;
import ddareunging.ddareunging_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Reply> findByCourseId(Long courseId) {
        return replyRepository.findByCourseCourseId(courseId);
    }

    // 돌아가는 기존코드!!
    public Reply createReply(ReplyRequestDto replyRequestDto) {
        Course course = courseRepository.findById(replyRequestDto.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found"));
        User user = userRepository.findById(replyRequestDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Reply reply = Reply.createReply(user, course, replyRequestDto);
        return replyRepository.save(reply);
    }

    //실험용
//    public Reply createReply(ReplyRequestDto replyRequestDto) {
//        Course course = courseRepository.findById(replyRequestDto.getCourseId())
//                .orElseThrow(() -> new RuntimeException("Course not found"));
//        User user = userRepository.findById(replyRequestDto.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        Reply reply = Reply.createReply(user, course, replyRequestDto);
//        return replyRepository.save(reply);
//    }

}
