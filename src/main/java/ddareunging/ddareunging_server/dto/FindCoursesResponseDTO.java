package ddareunging.ddareunging_server.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FindCoursesResponseDTO(Integer theme, List<CourseDTO> courses, String message) {
}
