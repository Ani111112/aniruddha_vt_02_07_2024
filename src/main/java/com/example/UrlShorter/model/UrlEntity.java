package com.example.UrlShorter.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "url")
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Long id;
    @Column(name = "full_url")
    private String fullUrl;
    @Column(name = "short_url")
    private String shortenUrl;
    @Column(name = "exp_time")
    private Date expirationTime;
}
