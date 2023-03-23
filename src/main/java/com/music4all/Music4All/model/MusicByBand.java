package com.music4all.Music4All.model;

import com.music4all.Music4All.enun.Instruments;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class MusicByBand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "musician_id", insertable=false, updatable=false)
    private User musician;

    @Column(name = "musician_id")
    private Long musicianId;

    @ManyToOne
    @JoinColumn(name = "band_id",insertable=false, updatable=false)
//    @Column(insertable=false, updatable=false)
    private Band band;

    @Column(name = "band_id")
    private Long bandId;

    private Instruments instrument;


}
