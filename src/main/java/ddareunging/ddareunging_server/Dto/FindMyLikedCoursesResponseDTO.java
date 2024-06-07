package ddareunging.ddareunging_server.dto;

import ddareunging.ddareunging_server.domain.Course;
import lombok.Builder;

import java.util.List;

@Builder
public record FindMyLikedCoursesResponseDTO(Long userId, List<CourseDTO> courses, String message) {
}