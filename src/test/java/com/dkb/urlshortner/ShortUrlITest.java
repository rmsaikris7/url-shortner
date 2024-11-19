package com.dkb.urlshortner;

import com.dkb.urlshortner.urlshortnerservice.types.model.ShortUrlBE;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

class ShortUrlITest extends AbstractITest {

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
    }
}
