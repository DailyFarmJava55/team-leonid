package dailyfarm.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtService {

    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    private JwtService() {}

    public static String generateToken(Authentication authentication) {
        List<String> authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).toList();

        return generateToken(authentication.getName(), authorities);
    }

    public static String generateToken(String username, Collection<String> authorities) {
        long currentTimeMillis = System.currentTimeMillis();

        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date(currentTimeMillis))
            .expiration(new Date(currentTimeMillis + 1000 * 60 * 15)) // 15 minutes
            .claim("authorities", authorities)
            .signWith(secretKey)
            .compact();
    }

    public static Authentication authenticate(String token) {
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

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, grantedAuthorities);
    }
}
