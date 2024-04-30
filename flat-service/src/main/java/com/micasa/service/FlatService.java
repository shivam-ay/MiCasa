package com.micasa.service;

import com.micasa.dto.FlatDto;

public interface FlatService
{
    FlatDto addNewFlat(FlatDto flatDto);
    FlatDto findFlatByFlatId(String flatId);
}
