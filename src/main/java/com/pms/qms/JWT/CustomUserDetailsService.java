package com.pms.qms.JWT;

import com.pms.qms.company.entities.Account;
import com.pms.qms.company.entities.Role;
import com.pms.qms.company.repository.AccountRepository;
import com.pms.qms.company.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    AccountRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    private Account account;


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

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        log.info("loadUserByUsername");
        account=userRepository.findByEmailId(username);
        if(!Objects.isNull(account)){
//            User user=new User(account.getEmail(),account.getPassword(),true,true,true,true,getAuthorities(account.getRoles()));
//            log.info("user {}",user);
            CustomUserDetails customUserDetails=new CustomUserDetails(account);
//            System.out.println("user sout "+customUserDetails);
            return customUserDetails;
        }else {
            throw new UsernameNotFoundException("User not found");
        }
//        Account account1 = userRepository.findByEmailId(username);
//        if (account1 == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new CustomUserDetails(account1);
//        account=userRepository.findByEmailId(username);
//        String username1 = account.getName();
//        String password = account.getPassword();
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//
////        String authorities = account.getRoles().stream()
////                .map(GrantedAuthority::getAuthority)
////                .collect(Collectors.joining(","));
//
//        Account account1=new Account();
//        account1.setEmail(username1);
//        account1.setPassword(password);
//        account1.setEnabled(true);
//        return account1;
    }

    private Set<SimpleGrantedAuthority> getAuthority(Account account) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        account.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }

//    private Collection<? extends GrantedAuthority> getAuthorities(
//            Collection<Role> roles) {
//
//        return getGrantedAuthorities(getPrivileges(roles));
//    }



    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

//    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
//        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//        return mapRoles;
//    }
}
