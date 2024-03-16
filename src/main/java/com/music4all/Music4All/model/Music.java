package com.music4all.Music4All.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.music4all.Music4All.dtos.MusicDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Music extends  Common {

    public Music() {
    }

    public Music(MusicDTO musicDTO) {
        this.nameMusic = musicDTO.getNameMusic();
        this.lyric = musicDTO.getLyric();
        this.description = musicDTO.getDescription();
        this.discId = musicDTO.getDiscId();
    }

    private String nameMusic;

    private String duration;

    @Column(columnDefinition = "TEXT")
    private String lyric;

    @Column(name = "favorite", columnDefinition = "integer default 0")
    private Integer favorite;

    private String description;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime releaseDate;

    @Column(name = "likes", columnDefinition = "integer default 0")
    private Integer like = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer dislike = 0;

    private Integer auditions;

    @Column(name = "disc_id")
    private Long discId;

    @ManyToOne
    @JoinColumn(name = "disc_id", insertable = false, updatable = false)
    @JsonIgnore
    private Disc disc;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "members_by_music",
            joinColumns = @JoinColumn(name = "music_id"),
            inverseJoinColumns = @JoinColumn(name = "user_music_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<User> membersByMusic;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private byte[] music;

    @Column(name = "mime_type")
    private String mineType;

    private String musicLink;

}
