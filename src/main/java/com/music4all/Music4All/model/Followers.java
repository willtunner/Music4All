package com.music4all.Music4All.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Followers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="from_user_fk")
    private User from;

    @ManyToOne
    @JoinColumn(name="to_user_fk")
    private User to;
}
