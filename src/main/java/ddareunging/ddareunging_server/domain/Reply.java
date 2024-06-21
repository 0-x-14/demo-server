package ddareunging.ddareunging_server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ddareunging.ddareunging_server.dto.ReplyRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "Reply")

public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "reply_content")
    private String replyContent;

    @Column(name = "reply_time")
    private LocalDateTime replyTime;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public LocalDateTime getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(LocalDateTime replyTime) {
        this.replyTime = replyTime;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // 돌아가는 기존코드!!!
    public static Reply createReply(User user, Course course, ReplyRequestDto replyRequest) {
        Reply reply = new Reply();
        reply.setReplyContent(replyRequest.getReplyContent());
        reply.setCourse(course);
        reply.setUser(user);
        reply.setReplyTime(LocalDateTime.now());
        return reply;
    }

    // 동적으로 되는지 실험
//    public static Reply createReply(User user, Course course, ReplyRequestDto replyRequestDto) {
//        Reply reply = new Reply();
//        reply.setUser(user);
//        reply.setCourse(course);
//        reply.setReplyContent(replyRequestDto.getReplyContent());
//        reply.setReplyTime(LocalDateTime.now());
//        return reply;
//    }


}
