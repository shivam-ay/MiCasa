package com.micasa.mapper;

import com.micasa.dto.FlatDto;
import com.micasa.model.Flat;
import org.mapstruct.Mapper;

/**
 * Mapper class for Flat and FlatDto.
 */
@Mapper(componentModel = "spring")
public interface FlatMapper
{
    /**
     * This function maps flatDto to flat.
     * @param flatDto : FlatDto object.
     * @return : Flat object.
     */
    Flat flatDtoToFlatEntity(FlatDto flatDto);
    /**
     * This function maps flat to flatDto.
     * @param flat : Flat object.
     * @return : FlatDto object.
     */
    FlatDto flatEntityToFlatDto(Flat flat);
}
