package com.example.UrlShorter.transformaer;

import com.example.UrlShorter.dto.FullUrl;
import com.example.UrlShorter.model.UrlEntity;

import java.time.LocalDate;

public class UrlEntityTransformer {
    public static UrlEntity FullUrlToUrlEntity(FullUrl fullUrl, String shortUrl) {
        return UrlEntity.builder()
                .fullUrl(fullUrl.getFullUrl())
                .shortenUrl(shortUrl)
                .build();
    }
}
