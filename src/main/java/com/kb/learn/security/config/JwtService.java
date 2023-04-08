package com.kb.learn.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;


    public <T> T extractClaim(final String jwtToken, final Function<Claims, T> claimsResolverFunction) {
        log.debug("Extracting claims from JWt token {}", jwtToken);
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolverFunction.apply(claims);
    }

    public String extractUsername(final String jwtToken) {
        log.debug("Extracting username from JWt token {}", jwtToken);
        return extractClaim(jwtToken, Claims :: getSubject);
    }

    public String generateJwtToken(final UserDetails userDetails) {
        log.debug("Generate JWT token from username  {}", userDetails.getUsername());
        return generateJwtToken(new HashMap<>(), userDetails);
    }

    public String generateJwtToken(final Map<String, Object> extraClaims, final UserDetails userDetails) {
        log.debug("Generate JWT token from claims {} and username {}", extraClaims, userDetails.getUsername());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(final String jwtToken, final UserDetails userDetails) {
        log.debug("Validating JWT token for username  {}", userDetails.getUsername());
        final String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(final String jwtToken) {
        log.debug("Validating JWT token is not Expired.");
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(final String jwtToken) {
        return extractClaim(jwtToken, Claims :: getExpiration);
    }

    private Claims extractAllClaims(final String jwtToken) {
        log.debug("Extracting AllClaims from JWt token {}", jwtToken);
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
