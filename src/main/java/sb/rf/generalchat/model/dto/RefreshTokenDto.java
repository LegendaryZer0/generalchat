package sb.rf.generalchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenDto implements Serializable {
    private String deviceName;

    private UUID refreshToken;

    private Long userId;

}
