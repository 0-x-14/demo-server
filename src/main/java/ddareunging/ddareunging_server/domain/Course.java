package ddareunging.ddareunging_server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import ddareunging.ddareunging_server.domain.common.BaseEntity;
import ddareunging.ddareunging_server.domain.enums.CourseTheme;
import ddareunging.ddareunging_server.dto.RegisterNewCourseRequestDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


  
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Course")
public class Course extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_image")
    private String courseImage;
    @Column(name = "course_like")
    private Integer courseLike;
    public void setCourseLike(Integer courseLike) { this.courseLike = courseLike; }

    @Column(name = "course_name")
    private String courseName;
    private String detail;
  
    @Enumerated(EnumType.STRING)
    private CourseTheme theme;
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


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Reply> replies = new ArrayList<>();


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Spot> spots = new ArrayList<>();

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public Integer getCourseLike() {
        return courseLike;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public CourseTheme getTheme() {
        return theme;
    }

    public void setTheme(CourseTheme theme) {
        this.theme = theme;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Integer getKcal() {
        return kcal;
    }

    public void setKcal(Integer kcal) {
        this.kcal = kcal;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
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
