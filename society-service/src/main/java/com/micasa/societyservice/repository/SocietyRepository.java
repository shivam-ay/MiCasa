package com.micasa.societyservice.repository;

import com.micasa.societyservice.model.Society;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "societyRepository")
public interface SocietyRepository extends JpaRepository<Society, String>
{
}
