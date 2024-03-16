package com.music4all.Music4All.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicDTO {

    private String nameMusic;
    private String lyric;
    private String description;
    private Long discId;
}
