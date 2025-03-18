package dailyfarm.account.dto;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {

}
