package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.repository.CourseRepository;
import ddareunging.ddareunging_server.repository.LikeRepository;
import ddareunging.ddareunging_server.repository.ReplyRepository;
import ddareunging.ddareunging_server.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Slf4j

// 사용자 정보 저장 서비스

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public User saveUser(HashMap<String, Object> userInfo) {
        User user = new User();

        user.setUser_id(Long.parseLong(userInfo.get("id").toString()));
        user.setUser_name(userInfo.get("name").toString());
        user.setEmail(userInfo.get("email").toString());
        user.setProfile_image(userInfo.get("profileImage").toString());
        user.setUser_status(0);  // 활성 상태를 나타내는 0 사용

        return userRepository.save(user);  // 데이터베이스에 저장
    }

    @Transactional
    public User updateUserProfile(User user, String nickname) throws IOException {
        boolean isUpdated = false;

        if (nickname != null && !nickname.isBlank()) {
            user.setNickname(nickname);
            isUpdated = true;
        }

        if (isUpdated) {
            return userRepository.save(user);  // 변경 사항이 있을 때만 저장
        }

        return user;
    }

    // 회원 가입 유저의 닉네임 업데이트
    @Transactional
    public User updateMyPage(User user, String nickname) {
        if (nickname != null && !nickname.isBlank()) {
            user.setNickname(nickname);
            userRepository.save(user);
        }
        return user;
    }

    // 카카오 탈퇴
    @Transactional
    public void deleteUser(User user) {
        Long userId = user.getUser_id();
        // 유저와 관련된 Like 엔티티 삭제
        //likeRepository.deleteLikesByUserId(user.getUser_id());
        likeRepository.deleteLikesByUserId(userId);
        // 유저와 관련된 Reply 엔티티 삭제
        replyRepository.deleteRepliesByUserId(userId);
        // 유저와 관련된 Course 엔티티 삭제
        courseRepository.deleteCoursesByUserId(userId);
        // 최종적으로 유저 삭제
        userRepository.delete(user);
    }

    // like 테이블의 외래키로 되어있는 유저 아이디 삭제
//    @Transactional
//    public void deleteUserAndAssociations(User user) {
//        // 관련된 like 데이터 삭제
//        likeRepository.deleteByUser(user.getUser_id());
//
//        // 사용자 삭제
//        userRepository.delete(user);
//    }

}







