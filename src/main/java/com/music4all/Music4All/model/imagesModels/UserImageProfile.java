package com.music4all.Music4All.model.imagesModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.music4all.Music4All.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class UserImageProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;

    @Column(name = "mime_type")
    private String mineType;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private byte[] data;

    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

    private String link;
}
