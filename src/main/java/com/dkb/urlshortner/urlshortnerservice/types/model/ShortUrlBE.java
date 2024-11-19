package com.dkb.urlshortner.urlshortnerservice.types.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import org.hibernate.annotations.Generated;

@Entity
@Table(name = "short_url")
public class ShortUrlBE extends BaseEntity {

    @Id
    @Generated
    private Long id;

    @NotNull @NotBlank
    private String longUrl;

    @NotNull @NotBlank
    private String key;

    @NotNull private Instant expiresAt;

    public ShortUrlBE() {}

    public ShortUrlBE(Long id, String longUrl, String key, Instant expiresAt) {
        this.id = id;
        this.longUrl = longUrl;
        this.key = key;
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

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
