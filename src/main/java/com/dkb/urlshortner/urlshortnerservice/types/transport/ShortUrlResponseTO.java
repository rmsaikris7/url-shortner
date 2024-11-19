package com.dkb.urlshortner.urlshortnerservice.types.transport;

import java.time.Instant;

public record ShortUrlResponseTO(String key, String longUrl, Instant createdAt, Instant expiresAt) {}
