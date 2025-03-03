package dailyfarm.token.dto;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {}
