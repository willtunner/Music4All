package com.music4all.Music4All.model.imagesModels;

import com.music4all.Music4All.model.Band;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ImageBandLogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String filename;

    @Column(name = "mime_type")
    private String mineType;

    private byte[] data;

    @Column(name = "band_id")
    private Long bandId;

    @OneToOne
    @JoinColumn(name = "band_id", insertable=false, updatable=false)
    private Band band;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

    private String link;

}
