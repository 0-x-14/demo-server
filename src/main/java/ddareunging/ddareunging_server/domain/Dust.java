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
public class Dust {
    private String pm10Value; // 미세먼지 수치
    private String pm25Value; // 초미세먼지 수치
    private int pm10Grade; // 미세먼지 등급
    private int pm25Grade; // 초미세먼지 등급
}
