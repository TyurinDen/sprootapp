package com.example.demo.db.models;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
@NamedQuery(name = "getAllUsers", query = "SELECT cu FROM CustomUser cu")
@NoArgsConstructor
@RequiredArgsConstructor
public class CustomUser implements UserDetails {
    public static final String GET_ALL_USERS = "getAllUsers";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "LOGIN", nullable = false, unique = true, updatable = false, length = 30)
    @NotEmpty(message = "*Please provide your login")
    private String login;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "PERMISSIONS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles;

    @Column(name = "PASSWORD", nullable = false)
    @NotEmpty(message = "*Please provide your password")
    @Length(message = "*Password must have at least 5 characters")
    private String password;

    @Column(name = "NON_EXP", nullable = false, columnDefinition = "tinyint default 1")
    private boolean accountNonExpired = true;

    @Column(name = "NON_LOCKED", nullable = false, columnDefinition = "tinyint default 1")
    private boolean accountNonLocked = true;

    @Column(name = "CRED_NON_EXP", nullable = false, columnDefinition = "tinyint default 1")
    private boolean credentialsNonExpired = true;

    @Column(name = "ENABLED", nullable = false, columnDefinition = "tinyint default 1")
    private boolean enabled = true;

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        CustomUser that = (CustomUser) other;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return "CustomUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", roles=" + roles +
                ", password='" + password + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                '}';
    }
}
