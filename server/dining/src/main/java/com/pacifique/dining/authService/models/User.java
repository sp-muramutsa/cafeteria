package com.pacifique.dining.authService.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Email(message = "email should be a valid email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@mcneese\\.edu$", message = "Email should be a valid McNeese Email ending in @mcneese.edu")
    @Column(unique = true)
    private String email;

    @Size(min = 9, max = 9, message = "mcneeseId must be exactly 9 characters")
    @Column(unique = true)
    private String mcneeseId;

    @NotBlank(message = "firstname cannot be null or blank")
    private String firstname;

    @NotBlank(message = "lastname cannot be null or blank")
    private String lastname;

    @Enumerated(EnumType.STRING)
    private Dorm dorm;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
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
        return true;
    }
}
