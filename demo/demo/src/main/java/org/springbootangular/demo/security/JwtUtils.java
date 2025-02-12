package org.springbootangular.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class JwtUtils {
    private  static final Long EXPIRIATION_TIME_IN_MILLISEC= 100L * 60L * 60L * 24L * 30L * 6L ;

    private SecretKey secretKey;

    @Value("${secretJwtString}")
    private String secretJwtString;

    @PostConstruct
    public void init() {
        byte[] keyByte = secretJwtString.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(keyByte, "HmacSHA256");
    }
    public  String generateToken (String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRIATION_TIME_IN_MILLISEC))
                .signWith(secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
    }
    public boolean isTokenValid(String token , UserDetails userdetals){
        final String username = getUsernameFromToken(token);
        return ( username.equals(userdetals.getUsername()) && !isTokenExpired(token) );
    }

    private boolean isTokenExpired(String token){
        return extractClaims(token, Claims :: getExpiration).before(new Date());
    }

}
