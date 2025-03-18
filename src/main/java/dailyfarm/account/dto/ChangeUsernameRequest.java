package dailyfarm.account.dto;

public record ChangeUsernameRequest(
    String username
) {

    public ChangeUsernameRequest {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
    }
}
