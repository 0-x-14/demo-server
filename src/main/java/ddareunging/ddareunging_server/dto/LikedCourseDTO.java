package ddareunging.ddareunging_server.dto;

public record LikedCourseDTO(Long likeId, Long courseId, String courseName, String courseImage, Integer courseLike, Integer theme, String userNickname) {
}