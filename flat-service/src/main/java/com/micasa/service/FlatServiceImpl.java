package com.micasa.service;

import com.micasa.constants.ExceptionMessages;
import com.micasa.exception.BadRequestException;
import com.micasa.model.Flat;
import com.micasa.model.User;
import com.micasa.repository.FlatRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class implementation for flatService.
 * Methods:
 *     1. addFlat(Flat); Add a new flat to database.
 *     2. updateOccupiedStatus(String, boolean); Update occupied status of a flat
 *     3. deleteFlatById(String); Delete a flat by flatId.
 *     4. findFlatByFlatId(String); Find a flat by flatId.
 *     5. findFlatByBlockNumber(String); Find a flat by block number.
 *     6. findFlatByFlatNumber(String); Find a flat by flat number.
 *     7. findFlatByBlockNumberAndFlatNumber(String, String); Find a flat by block number and flat number.
 *     8. Flat addUserToFlat(String, User); Add a user to flat.
 */
@Slf4j
@Service(value = "flatService")
@Transactional
public class FlatServiceImpl implements FlatService
{
    //Flat repository instance.
    private final FlatRepository flatRepository;

    @Autowired
    public FlatServiceImpl(FlatRepository flatRepository)
    {
        this.flatRepository = flatRepository;
    }

    /**
     * Function to add a new flat to database.
     * Structure:
     *  1. Take a flat
     *  2. Save to database.
     * @param flat : Flat object
     * @return Saved Flat object.
     */
    @Override
    public Flat addFlat(Flat flat)
    {
        Flat savedFlat = this.flatRepository.save(flat);
        log.debug("Added new flat: {}", savedFlat);
        return savedFlat;
    }

    /**
     * Function to update occupied status of a flat.
     * Structure:
     *  1. Look for a flat with passed flatId
     *      If found
     *          1.1 Set new occupied status.
     *          1.2 Update in database.
     *          1.3 Return updated flat.
     *      Else
     *          1.1 Throw BadRequestException
     * @param flatId : FlatId for which occupied status has to be updated.
     * @param occupiedStatus : This occupied status will be set.
     * @return : updated flat object.
     */
    @Override
    public Flat updateOccupiedStatus(String flatId, boolean occupiedStatus)
    {
        Flat flat = this.findFlatByFlatId(flatId);
        flat.setOccupied(occupiedStatus);
        flat = this.flatRepository.save(flat);
        log.info("Flat with flatId: {} updated with occupied status: {}", flatId, occupiedStatus);
        return flat;
    }

    /**
     * Function to delete a flat by flatId.
     * Structure:
     *  1. Look for flat with passed flatId.
     *      If found
     *          1.1 Delete the flat.
     *      Else
     *          1.1 Throw BadRequestException.
     * @param flatId : Flat with this flatId will be deleted.
     */
    @Override
    public void deleteFlatById(String flatId)
    {
        Flat flat = this.findFlatByFlatId(flatId);
        this.flatRepository.delete(flat);
        log.info("Flat with flatId: {} deleted", flatId);
    }

    /**
     * Function to find a flat by flatId.
     * Structure:
     *  1. Look for a flat with passed flatId
     *      If found
     *          1.1 Return updated flat.
     *      Else
     *          1.1 Throw BadRequestException
     * @param flatId : Search for flat with this flatId.
     * @return : Found flat object.
     */
    @Override
    public Flat findFlatByFlatId(String flatId)
    {
        Optional<Flat> flatOptional = this.flatRepository.findById(flatId);
        return flatOptional.orElseThrow(() -> {
            log.error("Flat not found with flat Id: {}", flatId);
            return BadRequestException.builder()
                    .message(ExceptionMessages.NO_RECORD_PRESENT_MSG)
                    .fieldValue(flatId)
                    .timestamp(System.currentTimeMillis())
                    .build();
        });
    }

    /**
     * Function to look for flats with block number.
     * Structure:
     *  1. Use flatRepository to get flats with passed block number.
     *      If found:
     *          2.1 Return found flats.
     *      Else:
     *          2.1 throw BadRequestException.
     * @param blockNumber : blockNumber to look for flats.
     * @return : List of flats with passed block number.
     */
    @Override
    public List<Flat> findFlatByBlockNumber(String blockNumber)
    {
        Optional<List<Flat>> flatListOptional = this.flatRepository.findByFlatBlockOrderByFlatNumberDesc(blockNumber);
        return flatListOptional.orElseThrow(() -> {
                log.error("No flats present with block number: {}", blockNumber);
                return BadRequestException.builder()
                        .message(ExceptionMessages.NO_RECORD_PRESENT_MSG)
                        .fieldValue(blockNumber)
                        .timestamp(System.currentTimeMillis())
                        .build();
            });
    }

    /**
     * Function to look for flats with flat number.
     * Structure:
     *  1. Use flatRepository to get flats with passed flat number.
     *      If found:
     *          2.1 Return found flats.
     *      Else:
     *          2.1 throw BadRequestException.
     * @param flatNumber : flatNumber to look for flats.
     * @return : List of flats with passed flat number.
     */
    @Override
    public List<Flat> findFlatByFlatNumber(String flatNumber)
    {
        Optional<List<Flat>> flatListOptional = this.flatRepository.findByFlatNumberOrderByFlatBlockDesc(flatNumber);
        return flatListOptional.orElseThrow(() -> {
            log.error("No flats present with flat number: {}", flatNumber);
            return BadRequestException.builder()
                    .message(ExceptionMessages.NO_RECORD_PRESENT_MSG)
                    .fieldValue(flatNumber)
                    .timestamp(System.currentTimeMillis())
                    .build();
        });
    }

    /**
     * Function to look for flat with flat number and block number.
     * Structure:
     *  1. Use flatRepository to get flats with passed flat number and block number.
     *      If found:
     *          2.1 Return found flat.
     *      Else:
     *          2.1 throw BadRequestException.
     * @param blockNumber : blockNumber to look for flat.
     * @param flatNumber : flatNumber to look for flat.
     * @return : Flats with passed flat number and block number.
     */
    @Override
    public Flat findFlatByBlockNumberAndFlatNumber(String flatNumber, String blockNumber)
    {
        Optional<Flat> flatOptional = this.flatRepository.findByFlatNumberAndFlatBlock(flatNumber,blockNumber);
        return flatOptional.orElseThrow(() -> {
            log.error("No flats present with flat number: {} and block number: {}", flatNumber,blockNumber);
            return BadRequestException.builder()
                    .message(ExceptionMessages.NO_RECORD_PRESENT_MSG)
                    .fieldValue(flatNumber)
                    .timestamp(System.currentTimeMillis())
                    .build();
        });
    }

    /**
     * This function adds a user to flat.
     * Structure:
     *  1. Look for flat with passed flat id.
     *      If found:
     *          1.1 Add user to flat.
     *          1.2 Save to database.
     *          1.3 Return updated flat.
     *      Else:
     *          1.1 throw BadRequestException.
     * @param flatId : FlatId on which user will be added.
     * @param user : user that has to be added to flat.
     * @return : updated flat object.
     */
    @Override
    public Flat addUserToFlat(String flatId, User user)
    {
        Flat flat = this.findFlatByFlatId(flatId);
        flat.setUser(user);
        return this.flatRepository.save(flat);
    }
}
