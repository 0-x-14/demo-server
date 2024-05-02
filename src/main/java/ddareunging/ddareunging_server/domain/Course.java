package ddareunging.ddareunging_server.domain;

import ddareunging.ddareunging_server.dto.MakeNewCourseRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Course {
    @Id
    private Long course_id;
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
        return Course.builder().theme(makeNewCourseRequest.theme())
                .course_name(makeNewCourseRequest.course_name())
                .distance(makeNewCourseRequest.distance())
                .time(makeNewCourseRequest.time())
                .kcal(makeNewCourseRequest.kcal())
                .detail(makeNewCourseRequest.detail())
                .course_image(makeNewCourseRequest.course_image())
                .course_like(0) // 코스 제작시 좋아요 개수는 0으로 초기화
                .build();
    } // 추후 created_at도 추가해야 함
}