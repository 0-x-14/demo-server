package ddareunging.ddareunging_server.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LoginDto {
    public String accessToken;
    public String refreshToken;

}
