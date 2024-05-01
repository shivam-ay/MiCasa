package com.micasa.repository;

import com.micasa.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "flatRepository")
public interface FlatRepository extends JpaRepository<Flat, String>
{
    Optional<List<Flat>> findByFlatNumberOrderByFlatBlockDesc(String flatNumber);
    Optional<List<Flat>> findByFlatBlockOrderByFlatNumberDesc(String flatBlock);
    Optional<Flat> findByFlatNumberAndFlatBlock(String flatNumber, String flatBlock);
}
