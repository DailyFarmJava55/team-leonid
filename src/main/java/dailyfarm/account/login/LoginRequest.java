package dailyfarm.account.login;

public record LoginRequest(
    String username,
    String email,
    String phone,
    String password
) {

    public LoginRequest {
        if (username == null && email == null && phone == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
    }

    public String username() {
        if (username != null) {
            return username;
        }

        if (email != null) {
            return email;
        }

        if (phone != null) {
            return phone;
        }

        throw new IllegalStateException("Username cannot be null");
    }

    // TODO: IllegalArgumentException
    // TODO: IllegalStateException
}
