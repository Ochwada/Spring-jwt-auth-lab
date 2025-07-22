package com.ochwada.jwt_auth_lab.security;


import com.ochwada.jwt_auth_lab.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

/**
 * *******************************************************
 * Package: com.ochwada.jwt_auth_lab.security
 * File: JwtUtil.java
 * Author: Ochwada
 * Date: Monday, 21.Jul.2025, 3:06 PM
 * Description:  This utility class handles everything related to JWT:
 * - Creating (signing) JWT tokens
 * - Extracting information (claims) from JWT tokens
 * - Validating JWT tokens
 * *
 * It uses the JJWT (io.jsonwebtoken) library to work with tokens.
 * Objective:
 * *******************************************************
 */

@Component // Marks this class as a Spring Bean so it can be injected where needed (e.g. in controllers)
public class JwtUtil {

    /**
     * Secret key used to sign the token.
     * 🔐 IMPORTANT: In real applications, do NOT hardcode the secret key.
     * Use an environment variable or configuration file.
     * Used here: private final String SECRET_KEY = "my_secret_key";
     */
    private final String secretKey;

    /**
     * Injects the secret key from application.properties.
     *
     * @param secretKey the JWT signing key
     */
    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Converts the raw secret key string into a secure HMAC SHA-256 Key object.
     *
     * @return a Key used to sign or verify JWTs
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
                Base64.getEncoder()
                        .encode(secretKey.getBytes())
        );
    }


    /**
     * ---------------------------------
     * 1. Token Generator
     * ---------------------------------
     * Generates a signed JWT token containing the username and user roles.
     *
     * @param username the username to include in the token
     * @param roles    list of roles assigned to the user (e.g., USER, ADMIN)
     * @return a signed JWT token string
     */
    public String generateToken(String username, List<Role> roles) {
        return Jwts.builder() // Start building a token
                //.claim("roles", roles)// add custom claim 'roles' to payload
                .claim("roles", roles.stream().map(Role::name).toList()) // add custom claim 'roles' to payload
                .setSubject(username)// standard claim 'sub' = username
                .setIssuedAt(new Date())// token creation time = now
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // expires in 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();// build the token into a compact string
    }

    /**
     * ---------------------------------
     * 2a. Claims Extractor
     * ---------------------------------
     * Parses the token and extracts all claims (payload).
     *
     * @param token the JWT token
     * @return all the claims inside the token (subject, expiration, roles, etc.)
     * @throws JwtException if the token is invalid or expired
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();// extract the payload (claims)
        //.getBody(); // extract the payload (claims)
    }

    /**
     * ---------------------------------
     * 2b. Generic method to extract any claim
     * ---------------------------------
     * Generic method to extract any claim using a resolver function.
     * Helps to reduce code duplication for extracting various fields.
     *
     * @param token          the JWT token
     * @param claimsResolver a lambda function specifying which claim to extract
     * @param <T>            the type of claim to extract (e.g., String, Date, List)
     * @return the extracted claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);// get full token data

        return claimsResolver.apply(claims); // apply custom extraction logic
    }

    /**
     * ---------------------------------
     * 3a. Extract Username from Token
     * ---------------------------------
     * Extracts the username (i.e., the 'subject') from the JWT token.
     *
     * @param token the JWT token received from the client
     * @return the username stored in the token
     */
    public String extractUserName(String token) {
        // 'subject' is the standard field where we store the username
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * ---------------------------------
     * 3b. Extract expiration date from Token
     * ---------------------------------
     * Extracts the expiration date from the token.
     * This helps check whether the token is still valid.
     *
     * @param token the JWT token
     * @return expiration date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * ---------------------------------
     * 3c. Extract custom claim 'roles'  from Token
     * ---------------------------------
     * Extracts a list of {@link Role} enums from a JWT token.
     * 'roles' is a list we manually add to the token during creation.
     *
     * @param token the JWT token
     * @return list of roles (e.g., ["USER", "ADMIN"]) stored in the token
     */
    public List<Role> extractRoles(String token) {
        Claims claims = extractAllClaims(token);  // parse full payload from token

        /**
         * Alternative if the roles were stored as a list of maps (e.g., [{name: "ADMIN"}]):
         * List<Map<String, Object>> rawRoles = claims.get("roles", List.class);
         * Or safer: List<?> rawRoles = claims.get("roles", List.class);
         */
        List<String> rawRoles = claims.get("roles", List.class);
        // If no roles are present, return an empty list
        if (rawRoles == null) return Collections.emptyList();

        // Convert each role name string to its corresponding Role enum and collect as a list
        return rawRoles.stream()
                .map(Role::valueOf)
                .toList();
    }

    /**
     * ---------------------------------
     * 4a. Is Token Expired - Helper function
     * ---------------------------------
     * Checks if the token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token)
                .before(new Date());  // if expiration is before now, it’s expired
    }

    /**
     * ---------------------------------
     *4b. Token Validation
     * ---------------------------------
     * Validates a token by:
     * - Extracting the username and comparing it with expected username
     * - Ensuring the token is not expired
     *
     * @param token    the JWT token
     * @param username the expected username
     * @return true if token is valid and belongs to the username
     */
    public boolean validateToken(String token, String username) {
        return extractUserName(token)
                .equals(username)
                && !isTokenExpired(token);
    }


}
