package dailyfarm.user;

import dailyfarm.user.dto.LoginRequest;
import dailyfarm.user.dto.RegisterRequest;
import dailyfarm.user.entity.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public abstract class UserService<T extends User> {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository<T> userRepository;
    private final UserFactory<T> userFactory;

    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new EntityExistsException("Email already exists");
        }
        T identity = userFactory.create();
        identity.setEmail(registerRequest.email());
        identity.setPassword(passwordEncoder.encode(registerRequest.password()));
        userRepository.save(identity);
    }

    public void login(LoginRequest loginRequest) {
        T identity = userRepository.findByEmail(loginRequest.email()).orElseThrow(
            () -> new EntityNotFoundException("Email does not exist")
        );
        if (!passwordEncoder.matches(loginRequest.password(), identity.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
    }

    public void logout() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void changePassword(String password) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void resetPassword() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void refreshToken(String refreshToken) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
