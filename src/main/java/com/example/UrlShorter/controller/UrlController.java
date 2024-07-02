package com.example.UrlShorter.controller;

import com.example.UrlShorter.dto.FullUrl;
import com.example.UrlShorter.dto.ShortenUrl;
import com.example.UrlShorter.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Rest Controller for managing URL shortening and related operations.
 */

@RestController
public class UrlController {
    private UrlService urlService;

    /**
     * Constructor for UrlController.
     *
     * @param urlService Service layer for URL operations
     */
    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * Endpoint to shorten a given full URL.
     *
     * @param fullUrl The full URL to be shortened
     * @return A ResponseEntity containing the shortened URL or an error message
     */
    @PostMapping("/short/url")
    public ResponseEntity shortenUrl(@RequestBody FullUrl fullUrl) {
        try {
            ShortenUrl shortenUrl = urlService.shortenUrl(fullUrl);
            return new ResponseEntity<>(shortenUrl, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to update the destination URL of an existing short URL.
     *
     * @param shortUrl The short URL whose destination is to be updated
     * @param destinationUrl The new destination URL
     * @return A ResponseEntity containing the status of the update operation
     */
    @PostMapping("update/destination-url/{shortUrl}")
    public ResponseEntity updateDestinationUrl(@PathVariable String shortUrl, @RequestParam String destinationUrl) {
        try {
            Boolean isDestinationUrlUpdated = urlService.updateDestinationUrl(shortUrl, destinationUrl);
            return new ResponseEntity<>(isDestinationUrlUpdated, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to retrieve the destination URL of a given short URL.
     *
     * @param shortUrl The short URL whose destination URL is to be retrieved
     * @return A ResponseEntity containing the destination URL or an error message
     */
    @GetMapping("get/destination-url/{shortUrl}")
    public ResponseEntity getDestinationUrl(@PathVariable String shortUrl) {
        try {
            Map<String, Object> result = new HashMap<>();
            urlService.getDestinationUrl(shortUrl, result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to update the expiration date of a short URL.
     *
     * @param shortUrl The short URL whose expiration date is to be updated
     * @param day The number of days to extend the expiration date
     * @return A ResponseEntity containing the status of the update operation
     */
    @PostMapping("update/expiry/{shortUrl}")
    public ResponseEntity updateExpirationDate(@PathVariable String shortUrl, @RequestParam int day) {
        try {
            Boolean isDestinationUrlUpdated = urlService.updateExpirationDate(shortUrl, day);
            return new ResponseEntity<>(isDestinationUrlUpdated, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
