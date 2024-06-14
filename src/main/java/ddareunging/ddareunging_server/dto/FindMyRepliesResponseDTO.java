package ddareunging.ddareunging_server.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FindMyRepliesResponseDTO(Long userId, List<ReplyDTO> replies, String message) {
}
