package dailyfarm.account.dto;

public record RefreshTokenRequest(String refreshToken) {

    public RefreshTokenRequest {
        if (refreshToken == null) {
            throw new IllegalStateException("Refresh token is null");
        }
    }
}
