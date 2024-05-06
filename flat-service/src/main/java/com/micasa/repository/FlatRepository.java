package com.micasa.repository;

import com.micasa.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository to interact with database for database queries related to flat.
 */
@Repository(value = "flatRepository")
public interface FlatRepository extends JpaRepository<Flat, String>
{
    /**
     * This function finds all flat with passed flat number and returns in descending order wrt flatBlock.
     * @param flatNumber : FlatNumber for which flats will be searched.
     * @return : List of found flats.
     */
    Optional<List<Flat>> findByFlatNumberOrderByFlatBlockDesc(String flatNumber);
    /**
     * This function finds all flat with passed flat block and returns in descending order wrt flatNumber.
     * @param flatBlock : FlatBlock for which flats will be searched.
     * @return : List of found flats.
     */
    Optional<List<Flat>> findByFlatBlockOrderByFlatNumberDesc(String flatBlock);

    /**
     * This flat finds a flat with passed flatNumber and flatBlock.
     * @param flatNumber : FlatNumber for which flat will be searched.
     * @param flatBlock : FlatBlock for which flat will be searched.
     * @return : Found flat.
     */
    Optional<Flat> findByFlatNumberAndFlatBlock(String flatNumber, String flatBlock);
}