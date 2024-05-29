package ddareunging.ddareunging_server.dto;

import ddareunging.ddareunging_server.domain.Course;
import lombok.Builder;

import java.util.List;

@Builder
public record FindCoursesResponseDTO(Integer theme, List<Course> courses) {
}
