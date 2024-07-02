package com.example.UrlShorter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortenUrl {
    private String shortUrl;
}
