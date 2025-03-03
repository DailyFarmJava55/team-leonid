package dailyfarm.user.dto;

public record RegisterRequest(
    String email, // TODO: validate
    String password // TODO: validate
) {}
