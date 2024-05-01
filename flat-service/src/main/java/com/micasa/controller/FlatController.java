package com.micasa.controller;

import com.micasa.dto.FlatDto;
import com.micasa.mapper.FlatMapper;
import com.micasa.model.Flat;
import com.micasa.service.FlatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller layer to expose endpoints for flat-service.
 * Methods:
 *  1. addFlat(FlatDto) : Add a new flat to database.
 *  2. findFlatById(String) : Look for a flat with flatId.
 *  3. updateOccupiedStatus(String, boolean) : Update occupied status of a flat.
 *  4.
 */
@Slf4j
@RestController
public class FlatController
{
    //FlatService instance.
    private final FlatService flatService;
    //FlatMapper instance.
    private final FlatMapper flatMapper;

    @Autowired
    public FlatController(FlatService flatService, FlatMapper flatMapper)
    {
        this.flatService = flatService;
        this.flatMapper = flatMapper;
    }

    /**
     * Function to add a new flat to database.
     * Structure:
     *  1. Take a flatDto
     *  2. Map flatDto to flat.
     *  3. Use to save flat.
     *  4. Map flat to FlatDto.
     *  5. Return saved flatDto.
     * @param flatDto : Flat to save in database.
     * @return : saved flatDto.
     */
    @PostMapping(value = "/add/flat")
    public FlatDto addFlat(@RequestBody  FlatDto flatDto)
    {
        log.info("Received request to add a new flat");
        Flat flat = this.flatMapper.flatDtoToFlatEntity(flatDto);
        Flat savedFlat = this.flatService.addFlat(flat);
        return flatMapper.flatEntityToFlatDto(savedFlat);
    }

    /**
     * Function to find a flat by using flatId.
     * Structure:
     *  1. Use flatService to look for a flat with passed flatId.
     *  2. Return flatDto of the found flat.
     * @param flatId : Look for flat with this flatId.
     * @return : flatDto of the flat found.
     */
    @GetMapping("/find/flatId/{flatId}")
    public FlatDto findFlatById(@PathVariable String flatId)
    {
        log.info("Received request to find a flat with id {}", flatId);
        Flat flat = this.flatService.findFlatByFlatId(flatId);
        return this.flatMapper.flatEntityToFlatDto(flat);
    }

    /**
     * Function to update occupied status of a flat.
     * Structure:
     *  1. Use flatService to update occupied status.
     *  2. Return flatDto of updated flat.
     * @param flatId : FlatId for which occupied status will be updated.
     * @param occupiedStatus : This occupied status will be set.
     * @return : updated flatDto.
     */
    @PatchMapping("/update/occupied-status/{flatId}/{occupiedStatus}")
    public FlatDto updateOccupiedStatus(@PathVariable String flatId, @PathVariable boolean occupiedStatus)
    {
        log.info("Received request to update occupied status to {}, of flat with id {}",occupiedStatus, flatId);
        Flat flat = this.flatService.updateOccupiedStatus(flatId,occupiedStatus);
        return this.flatMapper.flatEntityToFlatDto(flat);
    }
}
