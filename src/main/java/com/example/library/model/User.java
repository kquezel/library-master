package com.example.library.model;


import com.example.library.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 32)
    private String username;
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth")
    private Date birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Role type;
    private Boolean enabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Book> book;

    public User(String fullName, Date birth, Boolean enabled, String password, Role type, String username) {
        this.fullName= fullName;
        this.birth = birth;
        this.enabled = enabled;
        this.password = password;
        this.type = type;
        this.username = username;
    }

    public User() {

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(type.name()));
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


    @Override
    public String toString() {
        return "Users{" +
                "id= " + id + '\'' +
                ", username= " + username + '\'' +
                ", FullName='" + fullName + '\'' +
                ", Birth='" + birth + '\'' +
                ", Enabled='" + enabled + '\'' +
                ", Password='" + password + '\'' +
                ", Type='" + type + '\'' +
                '}';
    }

}