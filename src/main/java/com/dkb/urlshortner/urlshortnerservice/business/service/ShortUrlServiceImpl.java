package com.dkb.urlshortner.urlshortnerservice.business.service;

import com.dkb.urlshortner.urlshortnerservice.business.strategy.ShorteningStrategy;
import com.dkb.urlshortner.urlshortnerservice.dataaccess.ShortUrlRepository;
import com.dkb.urlshortner.urlshortnerservice.exceptionhandling.ShortUrlExpiredException;
import com.dkb.urlshortner.urlshortnerservice.exceptionhandling.ShortUrlFailedValidationException;
import com.dkb.urlshortner.urlshortnerservice.exceptionhandling.ShortUrlNotFoundException;
import com.dkb.urlshortner.urlshortnerservice.types.data.ShortUrlDO;
import jakarta.validation.Validator;
import java.time.Instant;
import java.time.Period;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ShortUrlServiceImpl implements ShortUrlService {

    private static final Period DEFAULT_EXPIRY = Period.ofDays(7);

    private final ShortUrlRepository repository;
    private final ShorteningStrategy shorteningStrategy;
    private final Validator validator;

    public ShortUrlServiceImpl(
            ShortUrlRepository repository, ShorteningStrategy shorteningStrategy, Validator validator) {
        this.repository = repository;
        this.shorteningStrategy = shorteningStrategy;
        this.validator = validator;
    }

    @Override
    @Transactional
    public ShortUrlDO create(ShortUrlDO shortUrl) {
        Objects.requireNonNull(shortUrl);
        final var exceptions = validator.validate(shortUrl);

        if (!exceptions.isEmpty()) {
            throw new ShortUrlFailedValidationException();
        }

        populateShortUrl(shortUrl);

        return repository.create(shortUrl);
    }

    @Override
    @Cacheable(value = "keyCache", key = "#key")
    public ShortUrlDO findShortUrlByKey(String key) {
        Objects.requireNonNull(key);

        final var shortUrl = repository.findByKey(key);

        if (shortUrl == null) {
            throw new ShortUrlNotFoundException(key);
        }

        if (shortUrl.getExpiresAt().isBefore(Instant.now())) {
            throw new ShortUrlExpiredException(shortUrl);
        }

        return shortUrl;
    }

    @Override
    public void forEachExpired(Consumer<ShortUrlDO> action) {
        final var currentDate = Instant.now();
        repository.findAllExpiredOnDate(currentDate).forEach(action);
    }

    @Override
    @CacheEvict(value = "keyCache", key = "#key")
    public void deleteShortUrlByKey(String key) {
        repository.deleteShortUrlByKey(key);
    }

    private void populateShortUrl(ShortUrlDO shortUrlDO) {
        final var longUrl = shortUrlDO.getLongUrl();
        final var key = shorteningStrategy.shorten(longUrl);

        final var expiryDate = Instant.now().plus(DEFAULT_EXPIRY);

        shortUrlDO.setKey(key);
        shortUrlDO.setExpiresAt(expiryDate);
    }
}
