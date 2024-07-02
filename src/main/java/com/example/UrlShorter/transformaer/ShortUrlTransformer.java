package com.example.UrlShorter.transformaer;

import com.example.UrlShorter.dto.ShortenUrl;
import com.example.UrlShorter.model.UrlEntity;

public class ShortUrlTransformer {
    public static ShortenUrl UrlEntityToShortenUrl(UrlEntity urlEntity) {
        return ShortenUrl.builder()
                .shortUrl(urlEntity.getShortenUrl())
                .build();
    }
}
