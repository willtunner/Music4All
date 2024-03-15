package com.music4all.Music4All.model;

import com.fasterxml.jackson.annotation.*;
import com.music4all.Music4All.model.imagesModels.UserImageProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Common {

    @NotBlank(message = "{name.not.blank}")
    private String name;

    @Column(name = "image_profile_id")
    private Long imageProfileId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_profile_id", insertable=false, updatable=false)
    @JsonIgnore
    private UserImageProfile image;

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

    @JsonGetter(value = "password")
    public String getPwd() {
        return null;
    }

    @JsonSetter(value = "password")
    public void setPassword(String password) {
        this.password = password;
    }
}
