package telran.java55.teamleonid.business.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import telran.java55.teamleonid.business.dto.TokenDto;

import java.util.Date;

@Service
public class TokenService {

    private final static String SECRET_KEY = "secret-key"; // TODO: value
    private final static long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // TODO: value
    private final static long REFRESH_TOKEN_EXPIRATION = 60L * 24 * 60 * 60 * 1000; // TODO: value

    public TokenDto generateToken(String email, Date issuedAt) {
        String accessToken = generateAccessToken(email, issuedAt);
        String refreshToken = generateAccessToken(email, issuedAt);
        return new TokenDto(accessToken, refreshToken);
    }

    String generateAccessToken(String email, Date issuedAt) {
        return Jwts.builder()
            .subject(email)
            .issuedAt(issuedAt)
            .expiration(new Date(issuedAt.getTime() + ACCESS_TOKEN_EXPIRATION))
            .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
            .compact();
    }

    String generateRefreshToken(String email, Date issuedAt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void revokeToken(String refreshToken) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public TokenDto refreshToken(String refreshToken) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
