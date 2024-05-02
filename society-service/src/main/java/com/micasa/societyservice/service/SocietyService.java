package com.micasa.societyservice.service;

import com.micasa.societyservice.model.Society;

import java.util.List;

public interface SocietyService
{
    Society addSociety(Society society);
    Society updateSociety(Society society);
    void deleteSocietyById(String societyId);
    List<Society> getAllSocieties();
    Society getSocietyById(String societyId);
}
