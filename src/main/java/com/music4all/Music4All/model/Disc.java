package com.music4all.Music4All.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Disc extends  Common {

    @NotBlank(message = "{name.not.blank}")
    private String name;

    private String description;

    private LocalDate release;

    private String image;

    @Column(name = "likes", columnDefinition = "integer default 0")
    private Integer like;

    @Column(columnDefinition = "integer default 0")
    private Integer dislike;

    private String discImageUrl = null;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "disc_members",
            joinColumns = @JoinColumn(name = "disc_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Set<User> members = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "disc")
    private List<Music> musics;

    @Column(name = "band_id")
    private Long bandId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "band_id", insertable = false, updatable = false)
    private Band band;

}
