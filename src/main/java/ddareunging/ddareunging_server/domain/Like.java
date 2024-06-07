package ddareunging.ddareunging_server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import ddareunging.ddareunging_server.domain.common.BaseEntity;
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
@Table(name = "`like`") // 예약어 Like와 충돌하지 않게 하기 위함
public class Like extends BaseEntity {

    @Id
    private Long like_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @Transient
    private String user_nickname;

    public void setUserNickname(String nickname) {
        this.user_nickname = nickname;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId", referencedColumnName = "courseId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Course course;
}