package com.music4all.Music4All.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "{name.not.blank}")
    private String name;

    @Column(name = "likes", columnDefinition = "integer default 0")
    private Integer like = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer dislike = 0;

    private String genre;

    @NotBlank(message = "{bandCountry.not.blank}")
    private String country;

    @NotBlank(message = "{bandCity.not.blank}")
    private String city;

    @NotBlank(message = "{bandState.not.blank}")
    private String state;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean deleted = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name = "users_band",
            joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    @JsonBackReference
    private List<User> musics;

    @OneToMany(mappedBy = "band", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Disc> discs;

    @Column(name = "creator_id")
    private Long creatorId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", insertable = false, updatable = false)
    @JsonIgnore
    @JsonBackReference
    @JsonProperty("creator")
    private User creator;

}
