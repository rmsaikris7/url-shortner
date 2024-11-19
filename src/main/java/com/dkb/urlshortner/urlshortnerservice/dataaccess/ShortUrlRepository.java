package com.dkb.urlshortner.urlshortnerservice.dataaccess;

import com.dkb.urlshortner.urlshortnerservice.types.data.ShortUrlDO;
import java.time.Instant;
import java.util.stream.Stream;

public interface ShortUrlRepository {

    ShortUrlDO create(ShortUrlDO shortUrl);

    ShortUrlDO findByKey(String key);

    Stream<ShortUrlDO> findAllExpiredOnDate(Instant date);

    void deleteShortUrlByKey(String key);
}
