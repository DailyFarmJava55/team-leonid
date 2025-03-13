package dailyfarm.account.register;

public record RegisterRequest(
    String username,
    String email,
    String phone,
    String password
) {

    public RegisterRequest {
        if (username == null && email == null && phone == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
    }

    // TODO: IllegalArgumentException
}
