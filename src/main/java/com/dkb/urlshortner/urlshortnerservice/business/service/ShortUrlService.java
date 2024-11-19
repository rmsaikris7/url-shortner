package com.dkb.urlshortner.urlshortnerservice.business.service;

import com.dkb.urlshortner.urlshortnerservice.types.data.ShortUrlDO;
import java.util.function.Consumer;

public interface ShortUrlService {

    ShortUrlDO create(ShortUrlDO shortUrlDO);

    ShortUrlDO findShortUrlByKey(String key);

    void forEachExpired(Consumer<ShortUrlDO> action);

    void deleteShortUrlByKey(String key);
}
