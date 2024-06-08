package ddareunging.ddareunging_server.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FindMyLikedCoursesResponseDTO(Long userId, List<LikedCourseDTO> courses, String message) {
}