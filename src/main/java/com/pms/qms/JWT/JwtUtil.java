package com.pms.qms.JWT;

import com.pms.qms.company.entities.Account;
import com.pms.qms.company.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Slf4j
@Service
public class JwtUtil {
    private static final String SECRET_KEY="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String extractUserName(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

//    public String generateToken(String username,String role){
//        Map<String,Object> claims=new HashMap<>();
//        claims.put("role",role);
//        return createToken(claims,username);
//    }

    public String generateToken(Account account) {
        return generateToken(new HashMap<>(),account);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
//            authorities.addAll(role.getPrivileges()
//                    .stream()
//                    .map(p -> new SimpleGrantedAuthority(p.getName()))
//                    .collect(Collectors.toList()));
        }
        return authorities;
    }
    public String generateToken(Map<String,Object> extraClaims,Account account){
//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        log.info("email {}",account.getUsername());
//        log.info("generateToken 000");
//        account.getRoles().stream().forEach(rol -> {
//            log.info("getAuthorities 111 {}",rol.getName());
//            authorities.add(new SimpleGrantedAuthority(rol.getName()));
//        });
//

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .claim("role",getAuthorities(account.getRoles()))
                .setSubject(account.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    private String createToken(Map<String,Object> claims, String subject){
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 60 * 5))
//                .signWith(getSignInKey(),SignatureAlgorithm.ES256)
//                .compact();
//    }

    private Key getSignInKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username=extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
