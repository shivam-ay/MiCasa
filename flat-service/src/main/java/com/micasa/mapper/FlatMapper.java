package com.micasa.mapper;

import com.micasa.dto.FlatDto;
import com.micasa.model.Flat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlatMapper
{
    Flat flatDtoToFlatEntity(FlatDto flatDto);
    FlatDto flatEntityToFlatDto(Flat flat);
}
