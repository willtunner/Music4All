package com.music4all.Music4All.dtos;

import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.Followers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String nome;
    private String email;
    private String cellphone;
    private String gender;
    private Integer age;
    private String linkImageProfile;
    private LocalDateTime created;
    private List<Followers> followers;
    private List<Followers> following;
    private List<Band> bands;




}
