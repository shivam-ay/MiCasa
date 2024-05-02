package com.micasa.controller;

import com.micasa.dto.FlatDto;
import com.micasa.mapper.FlatMapper;
import com.micasa.model.Flat;
import com.micasa.service.FlatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * @return : saved flatDto, HttpStatus 201.
     */
    @PostMapping(value = "/add/flat")
    public ResponseEntity<FlatDto> addFlat(@RequestBody  FlatDto flatDto)
    {
        log.info("Received request to add a new flat");
        Flat flat = this.flatMapper.flatDtoToFlatEntity(flatDto);
        Flat savedFlat = this.flatService.addFlat(flat);
        FlatDto flatDtoSaved = this.flatMapper.flatEntityToFlatDto(savedFlat);
        return ResponseEntity.status(HttpStatus.CREATED).body(flatDtoSaved);
    }

    /**
     * Function to find a flat by using flatId.
     * Structure:
     *  1. Use flatService to look for a flat with passed flatId.
     *  2. Return flatDto of the found flat.
     * @param flatId : Look for flat with this flatId.
     * @return : flatDto of the flat found, HttpStatus 200.
     */
    @GetMapping("/find/flatId/{flatId}")
    public ResponseEntity<FlatDto> findFlatById(@PathVariable String flatId)
    {
        log.info("Received request to find a flat with id {}", flatId);
        Flat flat = this.flatService.findFlatByFlatId(flatId);
        FlatDto flatDto = this.flatMapper.flatEntityToFlatDto(flat);
        return ResponseEntity.status(HttpStatus.OK).body(flatDto);
    }

    /**
     * Function to update occupied status of a flat.
     * Structure:
     *  1. Use flatService to update occupied status.
     *  2. Return flatDto of updated flat.
     * @param flatId : FlatId for which occupied status will be updated.
     * @param occupiedStatus : This occupied status will be set.
     * @return : updated flatDto, HttpStatus 200.
     */
    @PatchMapping("/update/occupied-status/{flatId}/{occupiedStatus}")
    public ResponseEntity<FlatDto> updateOccupiedStatus(@PathVariable String flatId, @PathVariable boolean occupiedStatus)
    {
        log.info("Received request to update occupied status to {}, of flat with id {}",occupiedStatus, flatId);
        Flat flat = this.flatService.updateOccupiedStatus(flatId,occupiedStatus);
        FlatDto flatDto = this.flatMapper.flatEntityToFlatDto(flat);
        return ResponseEntity.status(HttpStatus.OK).body(flatDto);
    }

    /**
     * Function to delete a flat with flatId.
     * Structure:
     *  1. Use flatService to delete flat.
     *  2. Return response.
     * @param flatId : Flat with this flatId will be deleted.
     * @return : response, HttpStatus 204.
     */
    @DeleteMapping("/delete/flat/{flatId}")
    public ResponseEntity<Void> deleteFlatByFlatId(@PathVariable String flatId)
    {
        log.info("Received request to delete a flat with id {}", flatId);
        this.flatService.deleteFlatById(flatId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
