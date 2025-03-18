package dailyfarm.account.dto;

public record ChangePasswordRequest(
    String password
) {

    public ChangePasswordRequest {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
    }
}
