package ddareunging.ddareunging_server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "spot_type")
    private SpotType spotType;
  
    @Column(name = "spot_name")
    private String spotName;

    @Column(name = "spot_lat")
    private Double spotLat;

    @Column(name = "spot_lng")
    private Double spotLng;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId", referencedColumnName = "courseId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonBackReference
    private Course course;

    // Getters and setters


    public Long getSpot_id() {
        return spot_id;
    }

    public void setSpot_id(Long spot_id) {
        this.spot_id = spot_id;
    }

    public String getSpotType() {
        return spotType;
    }

    public void setSpotType(String spotType) {
        this.spotType = spotType;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public Double getSpotLat() {
        return spotLat;
    }

    public void setSpotLat(Double spotLat) {
        this.spotLat = spotLat;
    }

    public Double getSpotLng() {
        return spotLng;
    }

    public void setSpotLng(Double spotLng) {
        this.spotLng = spotLng;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
