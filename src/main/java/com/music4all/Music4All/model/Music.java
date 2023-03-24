package com.music4all.Music4All.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameMusic;

    private String duration;

    private String lyric;

    private String description;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

    private LocalDate releaseDate;

    @Column(name = "likes", columnDefinition = "integer default 0")
    private Integer like = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer dislike = 0;

    private Integer auditions;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "disc_id")
    private Long discId;
    @ManyToOne
    @JoinColumn(name = "disc_id", insertable = false, updatable = false)
    @JsonIgnore
    @JsonBackReference
    @JsonProperty("disc")
    private Disc disc;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "members_by_music",
            joinColumns = @JoinColumn(name = "music_id"),
            inverseJoinColumns = @JoinColumn(name = "user_music_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<User> membersByMusic;
}
