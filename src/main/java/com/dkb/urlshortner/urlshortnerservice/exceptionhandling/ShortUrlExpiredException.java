package com.dkb.urlshortner.urlshortnerservice.exceptionhandling;

import com.dkb.urlshortner.urlshortnerservice.types.data.ShortUrlDO;

public class ShortUrlExpiredException extends BusinessException {

    private static final String MESSAGE = "The short url with key %s has expired on %s. Please create a new short link";

    public ShortUrlExpiredException(ShortUrlDO shortUrl) {
        super(String.format(MESSAGE, shortUrl.getLongUrl(), shortUrl.getExpiresAt()));
    }
}
