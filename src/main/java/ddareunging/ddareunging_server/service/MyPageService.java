package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Notice;
import ddareunging.ddareunging_server.domain.Reply;
import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.dto.FindMyRepliesResponseDTO;
import ddareunging.ddareunging_server.dto.ReplyDTO;
import ddareunging.ddareunging_server.repository.CourseRepository;
import ddareunging.ddareunging_server.repository.NoticeRepository;
import ddareunging.ddareunging_server.repository.ReplyRepository;
import ddareunging.ddareunging_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class MyPageService {
    private final NoticeRepository noticeRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public MyPageService(NoticeRepository noticeRepository, CourseRepository courseRepository, UserRepository userRepository, ReplyRepository replyRepository) {
        this.noticeRepository = noticeRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
    }

    @Transactional
    public List<Notice> getNotice() throws Exception {
        // 공지사항 조회

        return noticeRepository.findAll();
    }

    @Transactional
    public FindMyRepliesResponseDTO getRepliesByUserId(Long userId) throws Exception {
        // 내가 작성한 댓글 조회

        User user = userRepository.findUserByUserId(userId);

        if (user == null) {
            // 사용자를 찾을 수 없는 경우
            throw new IllegalArgumentException("존재하지 않는 사용자입니다");
        }

        List<Reply> replies = replyRepository.findRepliesByUserUserId(userId);

        if (replies.isEmpty()) {
            return FindMyRepliesResponseDTO.builder()
                    .userId(userId)
                    .replies(new ArrayList<>())
                    .message("작성한 댓글이 없습니다").build();
        } //  작성한 댓글이 없는 경우

        List<ReplyDTO> myRepliesDTO = replies.stream()
                .map(reply -> {
                    return new ReplyDTO(reply.getReplyId(), reply.getCourse().getCourseName(), reply.getReplyContent());
                })
                .collect(Collectors.toList());

        return FindMyRepliesResponseDTO.builder()
                .userId(userId)
                .replies(myRepliesDTO)
                .message("내가 작성한 댓글이 정상적으로 조회되었습니다.").build();
    }
}