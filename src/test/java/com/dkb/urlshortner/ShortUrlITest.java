package com.dkb.urlshortner;

import com.dkb.urlshortner.urlshortnerservice.dataaccess.ShortUrlRepository;
import com.dkb.urlshortner.urlshortnerservice.types.data.ShortUrlDO;
import com.dkb.urlshortner.urlshortnerservice.types.model.ShortUrlBE;
import io.restassured.RestAssured;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;

class ShortUrlITest extends AbstractITest {

    @SpyBean
    ShortUrlRepository repository;

    @Nested
    class CreateShortUrl {
        @Test
        void shouldCreateShortUrl() {
            // GIVEN
            final var testLongUrl = "https://www.example.com:8080/one/two?firstname=John&lastname=Doe";

            final var payload = """
                        {"longUrl": "$longUrl"}
                        """
                    .replace("$longUrl", testLongUrl);

            // THEN
            RestAssured.given()
                    .body(payload)
                    .contentType("application/json")
                    .when()
                    .post("/api/v1/shorturl")
                    .then()
                    .statusCode(201);
        }
    }

    @Nested
    class GetShortUrl {
        @Test
        void shouldReturnShortUrl() {
            // GIVEN
            final var testLongUrl = "https://www.example.com:8080/one/two?firstname=John&lastname=Doe";

            final var existingShortUrl =
                    new ShortUrlBE(null, testLongUrl, "a1b2c3d", Instant.now().plus(7, ChronoUnit.DAYS));

            shortUrlJpaRepository.save(existingShortUrl);

            // THEN
            RestAssured.given()
                    .when()
                    .get("/api/v1/shorturl/a1b2c3d")
                    .then()
                    .statusCode(200)
                    .body("longUrl", CoreMatchers.equalTo(testLongUrl));
        }

        @Test
        void shouldReturnNotFoundIfShortUrlNotFound() {
            // GIVEN + THEN
            RestAssured.given().when().get("/api/v1/shorturl/a1b2c3d").then().statusCode(404);
        }

        @Test
        void shouldReturnBadRequestIfShortUrlExpired() {
            // GIVEN
            final var testLongUrl = "https://www.example.com:8080/one/two?firstname=John&lastname=Doe";

            final var existingShortUrl =
                    new ShortUrlBE(null, testLongUrl, "a1b2c3d", Instant.now().minus(1, ChronoUnit.DAYS));

            shortUrlJpaRepository.save(existingShortUrl);

            // THEN
            RestAssured.given().when().get("/api/v1/shorturl/a1b2c3d").then().statusCode(400);
        }

        @Test
        void shouldReturnShortUrlFromCache() {
            // GIVEN
            final var testLongUrl = "https://www.example.com:8080/one/two?firstname=John&lastname=Doe";

            final var existingShortUrlBE =
                    new ShortUrlBE(null, testLongUrl, "a1b2c3d", Instant.now().plus(7, ChronoUnit.DAYS));

            final var existingShortUrlDO = new ShortUrlDO(
                    1L, testLongUrl, "a1b2c3d", Instant.now(), Instant.now().plus(7, ChronoUnit.DAYS));

            shortUrlJpaRepository.save(existingShortUrlBE);

            Mockito.when(repository.findByKey("a1b2c3d")).thenReturn(existingShortUrlDO);

            RestAssured.given().when().get("/api/v1/shorturl/a1b2c3d");

            // THEN
            for (int i = 0; i < 5; i++) {
                RestAssured.given().when().get("/api/v1/shorturl/a1b2c3d");
            }

            Mockito.verify(repository, Mockito.times(1)).findByKey("a1b2c3d");
        }
    }
}
