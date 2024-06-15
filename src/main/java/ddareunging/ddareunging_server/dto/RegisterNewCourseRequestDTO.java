package ddareunging.ddareunging_server.dto;

import java.util.List;

public record RegisterNewCourseRequestDTO(String courseImage, String courseName, String courseDetail, Integer theme, List<SpotDTO> spots) {
    // 하나의 코스에 여러 개의 spot을 포함하므로 spot은 list 처리
}
