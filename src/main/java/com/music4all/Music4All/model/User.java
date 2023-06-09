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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{name.not.blank}")
    private String name;

    @Column(name = "image_profile_id")
    private Long imageProfileId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_profile_id", insertable=false, updatable=false)
    @JsonIgnore
    private UserImageProfile image;

    @Email
    @NotBlank(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
    private String email;

    @NotBlank(message = "{senha.not.blank}")
    private String password;

    private String cellphone;

    private String gender;

    private Integer age = 0;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean deleted = Boolean.FALSE;

    // Mudar roles
    /*
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
     */

    // Um usuário pode ter vários seguidores - followers
    @OneToMany(mappedBy="to")
    private List<Followers> followers;

    // Um usuário pode seguir vários usuários - following
    @OneToMany(mappedBy="from")
    private List<Followers> following;

    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    List<Band> bands;

    private String urlImageProfile;

}
