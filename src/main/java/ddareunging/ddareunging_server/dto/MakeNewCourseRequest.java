package ddareunging.ddareunging_server.dto;

public record MakeNewCourseRequest(int theme, String course_name, float distance, int time, int kcal, String detail, String course_image) {
}