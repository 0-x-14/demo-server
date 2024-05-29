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
public class Course extends BaseEntity {

    @Id
    private Long course_id;
    private String course_image;
    private Integer course_like;
    private String course_name;
    private Integer theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @Transient
    private String user_nickname;

    public void setUserNickname(String nickname) {
        this.user_nickname = nickname;
    }
}