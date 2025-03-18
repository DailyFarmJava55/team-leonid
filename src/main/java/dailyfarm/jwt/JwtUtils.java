package dailyfarm.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtils {

    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    private JwtUtils() {}

    public static String generateToken(Authentication authentication) {
        List<String> authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).toList();

        long currentTimeMillis = System.currentTimeMillis();

        return Jwts.builder()
            .subject(authentication.getName())
            .issuedAt(new Date(currentTimeMillis))
            .expiration(new Date(currentTimeMillis + 1000 * 60 * 15)) // 15 minutes
            .claim("authorities", authorities)
            .signWith(secretKey)
            .compact();
    }

    public static Authentication authentication(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        @SuppressWarnings("unchecked")
        List<String> authorities = claims.get("authorities", List.class);
        List<GrantedAuthority> grantedAuthorities = authorities.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(
            claims.getSubject(), null, grantedAuthorities
        );
    }
}
