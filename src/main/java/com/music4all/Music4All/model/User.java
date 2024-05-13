package com.music4all.Music4All.model;

import com.fasterxml.jackson.annotation.*;
import com.music4all.Music4All.enun.Role;
import com.music4all.Music4All.model.imagesModels.UserImageProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Data
@AllArgsConstructor
public class User extends Common implements UserDetails {

    public User() {
    }

    public User(String name, String email, String password, String cellphone, String gender, Integer age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cellphone = cellphone;
        this.gender = gender;
        this.age = age;
    }

    @NotBlank(message = "{name.not.blank}")
    private String name;

    @Column(name = "image_profile_id")
    private Long imageProfileId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_profile_id", insertable=false, updatable=false)
    @JsonIgnore
    private UserImageProfile image;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl = null;

    @Email
    @NotBlank(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "{senha.not.blank}")
    private String password;

    private String cellphone;

    private String gender;

    private Integer age = 0;

    // A user can have multiple followers - followers
    @OneToMany(mappedBy="to")
    private List<Followers> followers;

    // One user can follow multiple users - following
    @OneToMany(mappedBy="from")
    private List<Followers> following;

    @OneToMany()
    List<Band> bands;

    private String urlImageProfile;

    private Role role;

    @JsonGetter(value = "password")
    public String getPwd() {
        return null;
    }

    @JsonSetter(value = "password")
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
