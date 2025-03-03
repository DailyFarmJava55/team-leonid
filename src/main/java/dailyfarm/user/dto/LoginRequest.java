package dailyfarm.user.dto;

public record LoginRequest(
    String email,
    String password
) {}
