package ddareunging.ddareunging_server.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    @Id
    @Column(name = "region_id")
    private Long id;
    
    @Column(name = "district")
    private String district; // 구

    private int nx; // 위도
    private int ny; // 경도

    @Embedded
    private Weather weather; // 지역 날씨 정보

    @Embedded
    private Dust dust; // 지역 미세먼지 정보

    // 지역 생성
    public Region(Long id, String district, int nx, int ny) {
        this.id = id;
        this.district = district;
        this.nx = nx;
        this.ny = ny;
    }

    // 날씨 정보 갱신
    public void updateRegionWeather(Weather weather) {
        this.weather = weather;
    }

    // 미세먼지 정보 갱신
    public void updateRegionDust(Dust dust) {this.dust = dust;}
    @Override
    public String toString() {
        return district;
        // 사용자의 현재 위치
        // ㅇㅇ구 ㅇㅇ동
    }
}
