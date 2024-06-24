package ddareunging.ddareunging_server.dto;

import ddareunging.ddareunging_server.domain.enums.CourseTheme;
import lombok.Builder;

import java.util.List;

@Builder
public record FindCoursesResponseDTO(CourseTheme theme, List<CourseDTO> courses, String message) {
}
