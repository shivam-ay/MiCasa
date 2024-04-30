package com.micasa.repository;

import com.micasa.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository(value = "flatRepository")
public interface FlatRepository extends JpaRepository<Flat, String>
{
}
