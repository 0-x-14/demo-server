package ddareunging.ddareunging_server.domain;

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
public class Notice extends BaseEntity {

    @Id
    private Long noticeId;
    private String noticeTitle;
    private String noticeDetail;
}