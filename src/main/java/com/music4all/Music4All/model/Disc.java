package com.music4all.Music4All.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Disc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime created = LocalDateTime.now();

    private String description;

    private LocalDate release;

    @Column(name = "likes", columnDefinition = "integer default 0")
    private Integer like = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer dislike = 0;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean deleted = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name = "disc-members",
            joinColumns = @JoinColumn(name = "disc_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    public Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "disc")
    private List<Music> musics;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "band_id")
    private Band band;


}
