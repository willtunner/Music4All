package com.music4all.Music4All.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String cellphone;

    private String gender;

    private Integer age = 0;

    private LocalDateTime created = LocalDateTime.now();

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean deleted = Boolean.FALSE;

    // Mudar roles
    /*
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
     */

    // Mudar emails
    /*
     @OneToMany(mappedBy = "user")
     private List<Email> emails;
     */

    // Mudar bands

    @ManyToMany(mappedBy = "musics")
    private List<Band> bands;


    // Mudar discs
    @ManyToMany(mappedBy = "musicsByMusic")
    private List<Music> musics;


    // Seguidores

    // Um usuário pode ter muitos seguidores - followers
    @OneToMany(mappedBy="to")
    private List<Followers> followers;

    // Um usuário pode seguir vários usuários - following
    @OneToMany(mappedBy="from")
    private List<Followers> following;

}
