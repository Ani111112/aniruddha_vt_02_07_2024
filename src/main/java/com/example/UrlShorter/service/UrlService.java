package com.example.UrlShorter.service;

import com.example.UrlShorter.common.CommonUtils;
import com.example.UrlShorter.dto.FullUrl;
import com.example.UrlShorter.dto.ShortenUrl;
import com.example.UrlShorter.exception.InvalidUrlException;
import com.example.UrlShorter.exception.UrlAlreadyPresentException;
import com.example.UrlShorter.exception.UrlDoesNotExitsException;
import com.example.UrlShorter.model.UrlEntity;
import com.example.UrlShorter.repository.UrlRepository;
import com.example.UrlShorter.transformaer.ShortUrlTransformer;
import com.example.UrlShorter.transformaer.UrlEntityTransformer;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Objects;

/**
 * Service layer for URL shortening and related operations.
 */
@Service
public class UrlService {
    private static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int BASE62_LENGTH = BASE62.length();
    private static long counter = 0;

    private UrlRepository urlRepository;
    private CommonUtils commonUtils;
    @Value("${url}")
    private String urlPath;

    /**
     * Constructor for UrlService.
     *
     * @param urlRepository Repository for URL entities
     * @param commonUtils Utility class for common operations
     */

    @Autowired
    public UrlService(UrlRepository urlRepository, CommonUtils commonUtils) {
        this.urlRepository = urlRepository;
        this.commonUtils = commonUtils;
    }

    /**
     * Shortens a given full URL and saves it to the database.
     *
     * @param fullUrl The full URL to be shortened
     * @return A ShortenUrl object containing the shortened URL
     * @throws InvalidUrlException If the URL is invalid
     * @throws UrlAlreadyPresentException If the URL is already present in the database
     */

    public ShortenUrl shortenUrl(FullUrl fullUrl) {
        if (!isValidURL(fullUrl.getFullUrl())) throw new InvalidUrlException("Please Enter A Valid Url");
        urlRepository.findByFullUrl(fullUrl.getFullUrl()).ifPresent(ex -> {
                throw new UrlAlreadyPresentException("url already exists in the database. skipped generate");
            }
        );
        String rendomGeneratedString = generateUniqueString();
        String finalShortenString = urlPath.concat(rendomGeneratedString);
        UrlEntity urlEntity = UrlEntityTransformer.FullUrlToUrlEntity(fullUrl, finalShortenString);
        urlEntity.setExpirationTime(commonUtils.getOrUpdateExpirationDate(10, null));
        UrlEntity savedUrlEntity = urlRepository.save(urlEntity);
        return ShortUrlTransformer.UrlEntityToShortenUrl(savedUrlEntity);
    }

    /**
     * Validates a given URL.
     *
     * @param url The URL to be validated
     * @return True if the URL is valid, otherwise false
     */

    private boolean isValidURL(String url) {
        UrlValidator validator = new UrlValidator();
        return validator.isValid(url);
    }

    /**
     * Updates the destination URL of an existing short URL.
     *
     * @param shortUrl The short URL to be updated
     * @param destinationUrl The new destination URL
     * @return True if the update was successful
     * @throws InvalidUrlException If the destination URL is invalid
     */

    public Boolean updateDestinationUrl(String shortUrl, String destinationUrl) {
        String finalShortUrl = urlPath.concat(shortUrl);
        if (!isValidURL(destinationUrl)) throw new InvalidUrlException("Please Enter A Valid Url");
        UrlEntity savedUrlEntity = urlRepository.findByShortenUrl(finalShortUrl);
        savedUrlEntity.setFullUrl(destinationUrl);
        urlRepository.save(savedUrlEntity);
        return true;
    }

    /**
     * Retrieves the destination URL of a given short URL.
     *
     * @param shortUrl The short URL whose destination URL is to be retrieved
     * @param result A map to store the destination URL
     * @throws UrlDoesNotExitsException If the short URL does not exist
     */

    public void getDestinationUrl(String shortUrl, Map<String, Object> result) {
        String finalShortUrl = urlPath.concat(shortUrl);
        UrlEntity savedUrlEntity = urlRepository.findByShortenUrl(finalShortUrl);
        if (ObjectUtils.isEmpty(savedUrlEntity)) throw new UrlDoesNotExitsException("Entered Wrong URL");
        result.put("Destination Url", savedUrlEntity.getFullUrl());
    }

    /**
     * Updates the expiration date of a given short URL.
     *
     * @param shortUrl The short URL to be updated
     * @param day The number of days to extend the expiration date
     * @return True if the update was successful
     * @throws UrlDoesNotExitsException If the short URL does not exist
     */

    public Boolean updateExpirationDate(String shortUrl, int day) {
        String finalShortUrl = urlPath.concat(shortUrl);
        UrlEntity savedUrlEntity = urlRepository.findByShortenUrl(finalShortUrl);
        if (ObjectUtils.isEmpty(savedUrlEntity)) throw new UrlDoesNotExitsException("Entered Wrong URL");
        savedUrlEntity.setExpirationTime(commonUtils.getOrUpdateExpirationDate(day, savedUrlEntity.getExpirationTime()));
        urlRepository.save(savedUrlEntity);
        return true;
    }

    /**
     * Generates a unique string for the shortened URL.
     *
     * @return A unique string
     */

    public static synchronized String generateUniqueString() {
        long currentValue = counter++;
        StringBuilder uniqueString = new StringBuilder(8);
        while (currentValue > 0) {
            uniqueString.append(BASE62.charAt((int) (currentValue % BASE62_LENGTH)));
            currentValue /= BASE62_LENGTH;
        }
        while (uniqueString.length() < 8) {
            uniqueString.append('A');
        }

        return uniqueString.toString();
    }
}
