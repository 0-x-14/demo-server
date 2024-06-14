package ddareunging.ddareunging_server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import ddareunging.ddareunging_server.domain.common.BaseEntity;
import ddareunging.ddareunging_server.dto.RegisterNewCourseRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String courseImage;
    private Integer courseLike;
    public void setCourseLike(Integer courseLike) { this.courseLike = courseLike; }

    private String courseName;
    private String detail;
    private Integer theme;
    private Float distance;
    private Integer kcal;
    private Integer time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @Transient
    private String user_nickname;

    public void setUserNickname(String nickname) {
        this.user_nickname = nickname;
    }

    public static Course of(User user, RegisterNewCourseRequestDTO registerNewCourseRequestDTO) {
        // 새로운 코스를 등록
        return Course.builder().courseImage(registerNewCourseRequestDTO.courseImage())
                .courseLike(0)
                .courseName(registerNewCourseRequestDTO.courseName())
                .detail(registerNewCourseRequestDTO.courseDetail())
                .theme(registerNewCourseRequestDTO.theme())
                .distance(null)
                .kcal(null)
                .time(null)
                .user(user)
                .build();
    }
}