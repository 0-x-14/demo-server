package ddareunging.ddareunging_server.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FindMyCoursesResponseDTO(Long userId, List<CourseDTO> courses, String message) {
}