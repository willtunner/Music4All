package com.music4all.Music4All.model;
import com.fasterxml.jackson.annotation.*;
import com.music4all.Music4All.dtos.bandDtos.BandDtoRecord;
import com.music4all.Music4All.model.imagesModels.ImageBandLogo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Entity
public class Band extends Common {

    public Band() {
    }
    public Band(BandDtoRecord bandDtoRecord) {
        this.name = bandDtoRecord.name();
        this.genre = bandDtoRecord.genre();
        this.country = bandDtoRecord.country();
        this.city = bandDtoRecord.city();
        this.state = bandDtoRecord.state();
        this.creatorId = bandDtoRecord.creatorId();
    }

    @NotBlank(message = "{name.not.blank}")
    private String name;

    @Column(name = "logo_band_id")
    private Long logoBandId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "logo_band_id", insertable=false, updatable=false)
    @JsonIgnore
    private ImageBandLogo logo;

    private String bandImageUrl = null;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "like_band",
            joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<User> like;

    @ManyToMany
    @JoinTable(
            name = "dislike_band",
            joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<User> dislike;

    @ManyToMany
    @JoinTable(
            name = "favourite_band",
            joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<User> favourite;

    private String genre;

    @NotBlank(message = "{bandCountry.not.blank}")
    private String country;

    @NotBlank(message = "{bandCity.not.blank}")
    private String city;

    @NotBlank(message = "{bandState.not.blank}")
    private String state;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
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

    public void addDisc(Disc disc) {
        discs.add(disc);
    }

    public void likeUsers(User user) { like.add(user); }

    public void dislikeUsers(User user) { dislike.add(user); }

    public void favouriteUsers(User user) { favourite.add(user); }




}
