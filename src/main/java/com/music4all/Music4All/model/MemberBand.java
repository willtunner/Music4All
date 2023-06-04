package com.music4all.Music4All.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class MemberBand {

    @Column(name = "user_id")
    private Long userId;
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    private Long bandId;
    private String linkImageProfile;
    private List<Band> bands;
    private String instrument;
}
