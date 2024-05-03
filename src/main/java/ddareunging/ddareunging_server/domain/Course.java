package ddareunging.ddareunging_server.domain;

import ddareunging.ddareunging_server.dto.MakeNewCourseRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long course_id;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;
    private int theme;
    private String course_name;
    private float distance;
    private int time;
    private int kcal;
    private String detail;
    private String course_image;
    private int course_like;

    public static Course of(MakeNewCourseRequest makeNewCourseRequest){
        LocalDateTime currentDateTime = LocalDateTime.now(); // 현재 시간을 가져옴
        return Course.builder().created_at(currentDateTime)
                .theme(makeNewCourseRequest.theme())
                .course_name(makeNewCourseRequest.course_name())
                .distance(makeNewCourseRequest.distance())
                .time(makeNewCourseRequest.time())
                .kcal(makeNewCourseRequest.kcal())
                .detail(makeNewCourseRequest.detail())
                .course_image(makeNewCourseRequest.course_image())
                .course_like(0) // 코스 제작시 좋아요 개수는 0으로 초기화
                .build();
    }
}