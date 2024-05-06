package com.example.library.model;


import com.example.library.model.enums.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

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

    public User(String fullName, Date birth, Role type) {
        this.fullName= fullName;
        this.birth = birth;
        this.type = type;
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



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public java.util.List<Book> getBook() {
        return book;
    }

    public void setBook(java.util.List<Book> book) {
        this.book = book;
    }

    public Long getUsersId() {
        return userId;
    }

    public void setUsersId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Role getType() {
        return type;
    }

    public void setType(Role type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                "username= " + username + '\'' +
                ", FullName='" + fullName + '\'' +
                ", Birth='" + birth + '\'' +
                ", Type='" + type + '\'' +
                '}';
    }

}