package com.music4all.Music4All.dtos;

import com.music4all.Music4All.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicDTO {

    private Long id;
    private String nameMusic;
    private String duration;
    private String lyric;
    private Integer favorite;
    private String description;
    private LocalDateTime created;
    private LocalDateTime releaseDate;
    private Integer like;
    private Integer dislike;
    private Integer auditions;
    private Boolean deleted;
    private Long discId;
    private List<User> membersByMusic;
    private String mineType;
    private String musicLink;
}
