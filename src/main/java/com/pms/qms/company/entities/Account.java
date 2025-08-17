package com.pms.qms.company.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

//@NamedQuery(name ="Account.getAllUser",query = "select new com.pms.PMS.Model.payroll.UserModel(id,name,contactNumber,email,status) from Account")
//
//@NamedQuery(name ="Account.getUser",query = "select new com.pms.PMS.Model.payroll.UserModel(u.id,u.name,u.contactNumber,u.email,u.status) from Account u where u.id=:id")

@Slf4j
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "contact_number")
    private String contactNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    private String status;
    private Boolean enabled;
    private Integer depid;
    private Integer eid;
    private Integer facilityid;

    @Column(name = "generate_token")
    private String generateToken;
    @Column(name = "token_expire")
    private LocalDateTime tokenExpire;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("Chat"));
    }

    private Set<SimpleGrantedAuthority> getAuthority(Account account) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        account.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Account(final Integer id) {
        super();
        this.id = id;
    }
}
