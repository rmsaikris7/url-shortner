package com.dkb.urlshortner.urlshortnerservice.dataaccess.converter;

import com.dkb.urlshortner.urlshortnerservice.types.data.ShortUrlDO;
import com.dkb.urlshortner.urlshortnerservice.types.model.ShortUrlBE;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ShortUrlConverter {

    ShortUrlDO mapToDO(ShortUrlBE source);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ShortUrlBE mapToBE(ShortUrlDO source);
}
