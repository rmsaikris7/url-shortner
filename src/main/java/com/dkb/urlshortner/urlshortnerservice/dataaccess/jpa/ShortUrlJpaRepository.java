package com.dkb.urlshortner.urlshortnerservice.dataaccess.jpa;

import com.dkb.urlshortner.urlshortnerservice.types.model.ShortUrlBE;
import java.time.Instant;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlJpaRepository extends JpaRepository<ShortUrlBE, Long> {

    ShortUrlBE findByKey(String key);

    Stream<ShortUrlBE> findAllByExpiresAtLessThan(Instant date);

    void deleteByKey(String key);
}
