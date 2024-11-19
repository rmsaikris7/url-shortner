package com.dkb.urlshortner.urlshortnerservice.types.mapper;

import com.dkb.urlshortner.urlshortnerservice.types.data.ShortUrlDO;
import com.dkb.urlshortner.urlshortnerservice.types.transport.CreateShortUrlTO;
import com.dkb.urlshortner.urlshortnerservice.types.transport.ShortUrlResponseTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ShortUrlMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "key", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    ShortUrlDO mapToDO(CreateShortUrlTO source);

    ShortUrlResponseTO mapToTO(ShortUrlDO source);
}
