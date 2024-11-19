package com.dkb.urlshortner.urlshortnerservice.types.data;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

public class ShortUrlDO implements Serializable {

    private Long id;

    @NotNull private String longUrl;

    private String key;

    private Instant createdAt;

    private Instant expiresAt;

    public ShortUrlDO() {}

    public ShortUrlDO(Long id, String longUrl, String key, Instant createdAt, Instant expiresAt) {
        this.id = id;
        this.longUrl = longUrl;
        this.key = key;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
