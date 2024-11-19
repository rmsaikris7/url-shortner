package com.dkb.urlshortner.urlshortnerservice.facade.resource;

import com.dkb.urlshortner.urlshortnerservice.business.service.ShortUrlService;
import com.dkb.urlshortner.urlshortnerservice.types.mapper.ShortUrlMapper;
import com.dkb.urlshortner.urlshortnerservice.types.transport.CreateShortUrlTO;
import com.dkb.urlshortner.urlshortnerservice.types.transport.ErrorTO;
import com.dkb.urlshortner.urlshortnerservice.types.transport.ShortUrlResponseTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shorturl")
@Tag(name = "ShortUrl")
public class ShortUrlResource {

    private final ShortUrlService shortUrlService;
    private final ShortUrlMapper mapper;

    public ShortUrlResource(ShortUrlService shortUrlService, ShortUrlMapper mapper) {
        this.shortUrlService = shortUrlService;
        this.mapper = mapper;
    }

    @Operation(summary = "Create short url")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "ShortUrl created",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ShortUrlResponseTO.class))
                        })
            })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortUrlResponseTO> createShortUrl(@RequestBody CreateShortUrlTO createShortUrl) {
        final var shortUrl = mapper.mapToDO(createShortUrl);
        final var response = shortUrlService.create(shortUrl);
        return new ResponseEntity<>(mapper.mapToTO(response), HttpStatus.CREATED);
    }

    @Operation(summary = "Get short url")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Short url found",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ShortUrlResponseTO.class))
                        }),
                @ApiResponse(
                        responseCode = "400",
                        description = "Short url expired",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorTO.class))
                        }),
                @ApiResponse(
                        responseCode = "404",
                        description = "Short url not found",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorTO.class))
                        })
            })
    @GetMapping("/{key}")
    public ResponseEntity<ShortUrlResponseTO> getShortUrl(@PathVariable String key) {
        final var shortUrl = shortUrlService.findShortUrlByKey(key);
        final var response = mapper.mapToTO(shortUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
