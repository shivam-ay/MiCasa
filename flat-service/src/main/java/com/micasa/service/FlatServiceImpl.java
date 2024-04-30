package com.micasa.service;

import com.micasa.dto.FlatDto;
import com.micasa.mapper.FlatMapper;
import com.micasa.model.Flat;
import com.micasa.repository.FlatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service(value = "flatService")
public class FlatServiceImpl implements FlatService
{
    private final FlatRepository flatRepository;
    private final FlatMapper flatMapper;

    @Autowired
    public FlatServiceImpl(FlatRepository flatRepository, FlatMapper flatMapper)
    {
        this.flatRepository = flatRepository;
        this.flatMapper = flatMapper;
    }

    @Override
    public FlatDto addNewFlat(FlatDto flatDto)
    {
        Flat flat = this.flatMapper.flatDtoToFlatEntity(flatDto);
        Flat savedFlat = this.flatRepository.save(flat);
        return this.flatMapper.flatEntityToFlatDto(savedFlat);
    }

    @Override
    public FlatDto findFlatByFlatId(String flatId)
    {
        Optional<Flat> flatOptional = this.flatRepository.findById(flatId);
        Flat flat = flatOptional.orElseThrow();
        return this.flatMapper.flatEntityToFlatDto(flat);
    }
}
