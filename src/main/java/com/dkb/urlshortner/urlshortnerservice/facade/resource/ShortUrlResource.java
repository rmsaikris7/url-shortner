package com.dkb.urlshortner.urlshortnerservice.facade.resource;

import com.dkb.urlshortner.urlshortnerservice.business.service.ShortUrlService;
import com.dkb.urlshortner.urlshortnerservice.types.mapper.ShortUrlMapper;
import com.dkb.urlshortner.urlshortnerservice.types.transport.CreateShortUrlTO;
import com.dkb.urlshortner.urlshortnerservice.types.transport.ShortUrlResponseTO;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortUrlResponseTO> createShortUrl(@RequestBody CreateShortUrlTO createShortUrl) {
        final var shortUrl = mapper.mapToDO(createShortUrl);
        final var response = shortUrlService.create(shortUrl);
        return new ResponseEntity<>(mapper.mapToTO(response), HttpStatus.CREATED);
    }

    @GetMapping("/{key}")
    public ResponseEntity<ShortUrlResponseTO> getShortUrl(@PathVariable String key) {
        final var shortUrl = shortUrlService.findShortUrlByKey(key);
        final var response = mapper.mapToTO(shortUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
