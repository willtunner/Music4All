package com.music4all.Music4All.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "likes", columnDefinition = "integer default 0")
    private Integer like = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer dislike = 0;

    private String genre;

    private LocalDateTime created = LocalDateTime.now();

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean deleted = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name = "users_band",
            joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> musics;

    @OneToMany(mappedBy = "band")
    private List<Disc> discs;


}
