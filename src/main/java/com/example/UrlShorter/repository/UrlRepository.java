package com.example.UrlShorter.repository;

import com.example.UrlShorter.model.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByFullUrl(String fullUrl);
    UrlEntity findByShortenUrl(String shortUrl);
}
