package com.music4all.Music4All.model;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{name.not.blank}")
    private String name;

    @Column(length = 45, nullable = true)
    private String logo;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @JsonBackReference
    private List<User> musics;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Disc> discs;

    @Column(name = "creator_id")
    private Long creatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", insertable = false, updatable = false)
    @JsonIgnore
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "band_members",
            joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<User> members;

    public void addMembers(User member) {
        members.add(member);
    }


}
