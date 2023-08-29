package com.example.capstone2.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "the username field is required.")
    @Column(nullable = false, unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // don't show the passwords to others in response.
    @NotEmpty(message = "the password field is required.")
    @Pattern(message = "the password must contain at least seven characters, at least one number and both lower and uppercase letters and special characters", regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    @Column(nullable = true)
    private String password;

    @NotEmpty(message = "the email field is required.")
    @Email(message = "invalid email.")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private SalesPerson salesperson;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
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
        return true;
    }
}
