package com.micasa.societyservice.service;

import com.micasa.societyservice.model.Society;
import com.micasa.societyservice.repository.SocietyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "societyService")
@Transactional
public class SocietyServiceImpl implements SocietyService
{
    private final SocietyRepository societyRepository;

    @Autowired
    public SocietyServiceImpl(SocietyRepository societyRepository)
    {
        this.societyRepository = societyRepository;
    }

    @Override
    public Society addSociety(Society society)
    {
        return this.societyRepository.save(society);
    }

    @Override
    public Society updateSociety(Society society) {
        return null;
    }

    @Override
    public void deleteSocietyById(String societyId) {
        this.societyRepository.deleteById(societyId);
    }

    @Override
    public List<Society> getAllSocieties() {
        return List.of();
    }

    @Override
    public Society getSocietyById(String societyId)
    {
        return this.societyRepository.findById(societyId).get();
    }
}
