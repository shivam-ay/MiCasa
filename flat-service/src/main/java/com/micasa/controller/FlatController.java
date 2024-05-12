package com.micasa.controller;

import com.micasa.dto.FlatDto;
import com.micasa.dto.UserDto;
import com.micasa.mapper.FlatMapper;
import com.micasa.mapper.UserMapper;
import com.micasa.model.Flat;
import com.micasa.model.User;
import com.micasa.service.FlatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller layer to expose endpoints for flat-service.
 * Methods:
 * 1. addFlat(@RequestBody FlatDto) : Add a new flat to database.
 * 2. findFlatById(@PathVariable String) : Look for a flat with flatId.
 * 3. updateOccupiedStatus(@PathVariable String, @PathVariable boolean) : Update occupied status of a flat.
 * 4. deleteFlatByFlatId(@PathVariable String flatId); : delete flat by flatId.
 * 5. updateFlat(@RequestBody UserDto, @PathVariable String); : update user to a flat.
 * 6. List<FlatDto> findFlatByFlatNumber(@PathVariable String); : Find flats by flat number.
 * 7. List<FlatDto> findFlatByFlatBlock(@PathVariable String); : Find flats by block number.
 * 8. FlatDto findFlatByFlatAndBlockNumber(@PathVariable String, @PathVariable String); : Find flat by flat and block number.
 */
@Slf4j
@RestController
public class FlatController
{
    //FlatService instance.
    private final FlatService flatService;
    //FlatMapper instance.
    private final FlatMapper flatMapper;
    //UserMapper instance.
    private final UserMapper userMapper;

    @Autowired
    public FlatController(FlatService flatService, FlatMapper flatMapper, UserMapper userMapper)
    {
        this.flatService = flatService;
        this.flatMapper = flatMapper;
        this.userMapper = userMapper;
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
    @GetMapping("/get/flat-id/{flatId}")
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

    /**
     * Function to add a user to a flat.
     * Structure:
     *  1. Map userDto to user.
     *  2. Use flat service to add user to flat.
     *  3. return flatDto.
     * @param userDto : userDto object.
     * @param flatId : flatId on which user will be added
     * @return : FlatDto object.
     */
    @PutMapping("/update/flat/{flatId}")
    public FlatDto updateFlat(@RequestBody UserDto userDto, @PathVariable String flatId)
    {
        log.info("Received request to update a flat with id {}", flatId);
        User user = this.userMapper.userDtoToUser(userDto);
        Flat flat = this.flatService.addUserToFlat(flatId, user);
        return this.flatMapper.flatEntityToFlatDto(flat);
    }

    /**
     * Function to find a flat with flatNumber.
     *  Structure:
     *      1. User flatRepository to find flat number.
     *      2. Map flatList to FlatDto list.
     *      3. return flatDto list.
     * @param flatNumber : FlatNumber with which flat will be searched.
     * @return : List of found flats.
     */
    @GetMapping("/get/flat-number/{flatNumber}")
    public List<FlatDto> findFlatByFlatNumber(@PathVariable String flatNumber)
    {
        log.info("Received request to find a flat with flat number {}", flatNumber);
        List<Flat> flatList = this.flatService.findFlatByFlatNumber(flatNumber);
        return this.flatMapper.flatEntityListToFlatDtoList(flatList);
    }

    /**
     * Function to find a flat with flatBlock.
     *  Structure:
     *      1. User flatRepository to find flat block.
     *      2. Map flatList to FlatDto list.
     *      3. return flatDto list.
     * @param flatBlock : FlatBlock with which flat will be searched.
     * @return : List of found flats.
     */
    @GetMapping("/get/block-number/{flatBlock}")
    public List<FlatDto> findFlatByFlatBlock(@PathVariable String flatBlock)
    {
        log.info("Received request to find a flat with flat block {}", flatBlock);
        List<Flat> flatList = this.flatService.findFlatByBlockNumber(flatBlock);
        return this.flatMapper.flatEntityListToFlatDtoList(flatList);
    }

    /**
     * Function to look for flat with flat number and block number.
     * Structure:
     *  1. Use flatService to get flat with passed flat number and block number.
     *  2. Map flat to flatDto.
     *  3. return flatDto.
     * @param flatBlock : blockNumber to look for flat.
     * @param flatNumber : flatNumber to look for flat.
     * @return : Flats with passed flat number and block number.
     */
    @GetMapping("/get/flat-number/{flatNumber}/block-number/{flatBlock}")
    public FlatDto findFlatByFlatAndBlockNumber(@PathVariable String flatNumber, @PathVariable String flatBlock)
    {
        log.info("Received request to find a flat with flat number {} and block number {}", flatNumber, flatBlock);
        Flat flat = this.flatService.findFlatByBlockNumberAndFlatNumber(flatNumber, flatBlock);
        return this.flatMapper.flatEntityToFlatDto(flat);
    }
}
