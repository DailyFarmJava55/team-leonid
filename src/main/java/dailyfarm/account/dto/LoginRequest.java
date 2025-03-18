package dailyfarm.account.dto;

public record LoginRequest(
    String username,
    String password
) {

    public LoginRequest {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
    }
}
