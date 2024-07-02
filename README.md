# URL Shortener Service
This project provides a URL shortening service using Spring Boot. It offers RESTful endpoints to shorten URLs, retrieve the original URLs, update the destination URLs, and extend the expiration dates of shortened URLs.

## API Endpoints

### Shorten URL

- **URL**: `/short/url`
- **Method**: `POST`
- **Description**: Shortens a given full URL.
- **Request Body**:
  ```json
  {
    "fullUrl": "https://www.example.com"
  }

### Update Destination URL

- **URL**: `/update/destination-url/{shortUrl}`
- **Method**: `POST`
- **Description**: Updates the destination URL of an existing short URL.
- **Request Params**:
  - `destinationUrl` (String): The new destination URL.

### Get Destination URL

- **URL**: `/get/destination-url/{shortUrl}`
- **Method**: `GET`
- **Description**: Retrieves the destination URL of a given short URL.

### Update Expiry

- **URL**: `/update/expiry/{shortUrl}`
- **Method**: `POST`
- **Description**: Updates the expiration date of a short URL.
- **Request Params**:
  - `day` (int): The number of days to extend the expiration date.
