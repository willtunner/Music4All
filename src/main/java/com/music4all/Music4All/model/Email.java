package com.music4all.Music4All.model;

import com.music4all.Music4All.enun.EmailType;
import com.music4all.Music4All.enun.StatusEmail;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TB_EMAIL")
public class Email {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long emailId;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
    private Boolean deleted = false;
    private Integer count = 0;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail = StatusEmail.WAITING;
    private EmailType emailType;
    private Long contextId;
}
