package com.pms.qms.JWT;

import com.pms.qms.company.projection.AccountAuthProjection;
import com.pms.qms.company.repository.AccountRepository;
import com.pms.qms.company.repository.RoleRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    Claims claims = null;
    private String userName = null;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getServletPath().matches("/api/user/login|/api/user/signup|/api/user/forgotPassword|/api/user/passwordToken|/api/datacenter/add")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String token = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                userName = jwtUtil.extractUserName(token);
                log.info("doFilterInternal {}",token);
                claims=jwtUtil.extractAllClaims(token);
            }

            if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails=customUserDetailsService.loadUserByUsername(userName);
//                Account account=accountRepository.findByEmailId(userName);
                List<AccountAuthProjection> accountAuthProjections=accountRepository.findByAccountUserName(userName);
//                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_STAFF");
//                SimpleGrantedAuthority authority1 = new SimpleGrantedAuthority("ROLE_USER");
//                List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
//                updatedAuthorities.add(authority);
//                updatedAuthorities.add(authority1);
                log.info("userDetails 111 {}",userName);
                if(jwtUtil.validateToken(token,userDetails)){
//                    log.info("userDetails 222 {}",account.getRoles());
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,getAuthorities(accountAuthProjections));
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                    );
                    log.info("userDetails 333 {}",usernamePasswordAuthenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
//                CustomUserDetails customUserDetails=customUserDetailsService.loadUserByUsername(userName);
//                if(jwtUtil.validateToken(token,customUserDetails)){
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                            new UsernamePasswordAuthenticationToken(customUserDetails,null,customUserDetails.getAuthorities());
//                    usernamePasswordAuthenticationToken.setDetails(
//                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
//                    );
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }

    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<AccountAuthProjection> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (AccountAuthProjection role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRolename()));
//            authorities.addAll(role.getPrivileges()
//                    .stream()
//                    .map(p -> new SimpleGrantedAuthority(p.getName()))
//                    .collect(Collectors.toList()));
        }
        return authorities;
    }

    public String tokenexp(){
        return (String) claims.get("expire");
    }

//    public Boolean isAdmin(){
//        log.info("isAdmin {}",(String) claims.get("role[0]"));
//        return "admin".equalsIgnoreCase((String) claims.get("role[0].authority"));
//    }
//
//    public Boolean isUser(){
//        return "user".equalsIgnoreCase((String) claims.get("role.authority"));
//    }

    public String getCurrentuser(){
        return userName;
    }
}
