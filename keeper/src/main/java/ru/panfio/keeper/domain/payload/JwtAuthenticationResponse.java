package ru.panfio.keeper.domain.payload;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long userId;

    public JwtAuthenticationResponse(String accessToken,
                                     Long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }
}
