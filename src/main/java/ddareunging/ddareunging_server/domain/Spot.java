package ddareunging.ddareunging_server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import ddareunging.ddareunging_server.domain.enums.SpotType;
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
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spotId;

    @Enumerated(EnumType.STRING)
    private SpotType spotType;

    private String spotName;
    private Double spotLat;
    private Double spotLng;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId", referencedColumnName = "courseId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Course course;
}