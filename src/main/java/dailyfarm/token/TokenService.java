package dailyfarm.token;

import dailyfarm.token.dto.TokenResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private final static String SECRET_KEY = "secret-key"; // TODO: environment
    private final static long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // TODO: property
    private final static long REFRESH_TOKEN_EXPIRATION = 60L * 24 * 60 * 60 * 1000; // TODO: property

    public TokenResponse generateToken(String email, Date issuedAt) {
        String accessToken = generateAccessToken(email, issuedAt);
        String refreshToken = generateRefreshToken(email, issuedAt);
        return new TokenResponse(accessToken, refreshToken);
    }

    private String generateAccessToken(String email, Date issuedAt) {
        return Jwts.builder()
            .subject(email)
            .issuedAt(issuedAt)
            .expiration(new Date(issuedAt.getTime() + ACCESS_TOKEN_EXPIRATION))
            .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
            .compact();
    }

    private String generateRefreshToken(String email, Date issuedAt) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void revokeToken(String refreshToken) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public TokenResponse refreshToken(String refreshToken) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
