package ddareunging.ddareunging_server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ddareunging.ddareunging_server.dto.ReplyRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import ddareunging.ddareunging_server.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
  
@Entity
@Getter
@Builder
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Reply")
@AttributeOverrides({
        @AttributeOverride(name = "created_at", column = @Column(name = "reply_time"))
})
public class Reply extends BaseEntity {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "reply_content")
    private String replyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId", referencedColumnName = "courseId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonBackReference
    private Course course;

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
        //reply.setReplyTime(LocalDateTime.now());
        return reply;
    }


}