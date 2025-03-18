package dailyfarm.account.dto;

public record RegisterRequest(
    String username,
    String password
) {

    public RegisterRequest {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
    }
}
