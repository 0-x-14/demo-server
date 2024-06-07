package ddareunging.ddareunging_server.dto;

import ddareunging.ddareunging_server.domain.Course;
import lombok.Builder;

import java.util.List;

@Builder
public record FindMyCoursesResponseDTO(Long userId, List<Course> courses, String message) {
}