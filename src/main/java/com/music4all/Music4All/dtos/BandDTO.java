package com.music4all.Music4All.dtos;

import com.music4all.Music4All.model.Disc;
import com.music4all.Music4All.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BandDTO {

    private Long id;
    private String name;
    private String genre;
    private String country;
    private String city;
    private String state;
    private Long creatorId;
    private String logo;
    private LocalDateTime created;
    private List<User> like;
    private List<User> dislike;
    private List<User> favourite;
    private List<Disc> discs;
    private List<User> members;

}
