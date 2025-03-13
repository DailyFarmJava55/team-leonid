package dailyfarm.authentication;

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

public class JwtTokenProvider {

    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    public static String generateAccessToken(Authentication authentication, Long currentTimeMillis) {
        List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
            .subject(authentication.getName())
            .issuedAt(new Date(currentTimeMillis))
            .expiration(new Date(currentTimeMillis + 1000 * 60 * 15)) // 15 minutes
            .claim("authorities", authorities)
            .signWith(key)
            .compact();
    }

    public static Authentication authentication(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        @SuppressWarnings("unchecked")
        List<String> authorities = claims.get("authorities", List.class);
        List<GrantedAuthority> authorities1 = authorities.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(
            claims.getSubject(), null, authorities1
        );
    }

    // TODO: Resources
    // TODO: ExpiredJwtException
    // TODO: SignatureException
}
