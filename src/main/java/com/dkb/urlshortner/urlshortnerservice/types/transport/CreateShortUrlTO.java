package com.dkb.urlshortner.urlshortnerservice.types.transport;

import com.dkb.urlshortner.urlshortnerservice.facade.validation.URL;

public record CreateShortUrlTO(@URL String longUrl) {}
