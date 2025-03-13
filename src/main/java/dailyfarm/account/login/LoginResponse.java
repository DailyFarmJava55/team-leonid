package dailyfarm.account.login;

public record LoginResponse(
    String accessToken,
    String refreshToken
) {

}
