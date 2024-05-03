package ddareunging.ddareunging_server.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class Weather {
    private Double temp; // 기온
    private Double rainAmount; // 강수량
    private String lastUpdateTime; // 마지막 갱신 시각
}
