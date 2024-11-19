package com.dkb.urlshortner.urlshortnerservice.business.strategy;

import com.dkb.urlshortner.urlshortnerservice.dataaccess.ShortUrlRepository;
import com.github.kagkarlsson.shaded.cronutils.utils.VisibleForTesting;
import java.security.SecureRandom;
import java.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MD5HashShorteningStrategy implements ShorteningStrategy {

    private final Logger logger = LoggerFactory.getLogger(MD5HashShorteningStrategy.class);

    private final ShortUrlRepository repository;

    public static final int HASH_LENGTH = 7;

    public MD5HashShorteningStrategy(ShortUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public String shorten(String url) {

        final var randomNumber = new SecureRandom().nextLong();
        final var salt = Objects.toString(randomNumber);

        final var saltedString = url + salt;

        final var hash = DigestUtils.md5Hex(saltedString).substring(0, HASH_LENGTH);

        if (isDuplicateHash(hash)) {
            logger.warn("Computed hash conflict detected");
            shorten(url);
        }
        return hash;
    }

    @VisibleForTesting
    private boolean isDuplicateHash(String hash) {
        return !Objects.isNull(repository.findByKey(hash));
    }
}
