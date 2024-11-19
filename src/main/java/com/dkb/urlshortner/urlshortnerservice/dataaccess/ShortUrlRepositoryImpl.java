package com.dkb.urlshortner.urlshortnerservice.dataaccess;

import com.dkb.urlshortner.urlshortnerservice.dataaccess.converter.ShortUrlConverter;
import com.dkb.urlshortner.urlshortnerservice.dataaccess.jpa.ShortUrlJpaRepository;
import com.dkb.urlshortner.urlshortnerservice.types.data.ShortUrlDO;
import java.time.Instant;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class ShortUrlRepositoryImpl implements ShortUrlRepository {

    private final ShortUrlJpaRepository jpaRepository;
    private final ShortUrlConverter converter;

    public ShortUrlRepositoryImpl(ShortUrlJpaRepository jpaRepository, ShortUrlConverter converter) {
        this.jpaRepository = jpaRepository;
        this.converter = converter;
    }

    @Override
    public ShortUrlDO create(ShortUrlDO shortUrl) {
        final var shortUrlBE = converter.mapToBE(shortUrl);
        final var savedEntity = jpaRepository.save(shortUrlBE);
        return converter.mapToDO(savedEntity);
    }

    @Override
    public ShortUrlDO findByKey(String key) {
        final var shortUrlBE = jpaRepository.findByKey(key);
        return converter.mapToDO(shortUrlBE);
    }

    @Override
    public Stream<ShortUrlDO> findAllExpiredOnDate(Instant date) {
        final var shortUrlStream = jpaRepository.findAllByExpiresAtLessThan(date);
        return shortUrlStream.map(converter::mapToDO);
    }

    @Override
    public void deleteShortUrlByKey(String key) {
        jpaRepository.deleteByKey(key);
    }
}
