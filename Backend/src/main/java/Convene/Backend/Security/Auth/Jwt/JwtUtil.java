package Convene.Backend.Security.Auth.Jwt;

import Convene.Backend.AppUser.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    public String setTokenAttributes(Map<String, Object> claims, AppUser appUser){
        Date iat = new Date();
        Date exp = new Date(iat.getTime() + JWT_TOKEN_VALIDITY);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setId(appUser.getId().toString())
                .setSubject(appUser.getEmail())
                .setIssuedAt(iat)
                .setIssuer("Convene Application v.1")
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    public String generateToken(AppUser appUser){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", appUser.getId());
        claims.put("projects", appUser.getAuthorities());
        return setTokenAttributes(claims, appUser);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date getExpirationDateFromToken(String token){
        return getSpecificClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getSpecificClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public String getEmailFromToken(String token){
        return getSpecificClaimFromToken(token, Claims::getSubject);
    }

    public String getIdFromToken(String token) {
        return getSpecificClaimFromToken(token, Claims::getId);
    }


    public Boolean validateToken(String token, AppUser appUser){
        final String email = getEmailFromToken(token);
        return (email.equals(appUser.getEmail()) && !isTokenExpired(token) &&
                appUser.getId().toString().equals(getSpecificClaimFromToken(token, Claims::getId)));
    }


}
