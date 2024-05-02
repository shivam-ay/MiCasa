package com.micasa.societyservice.controller;

import com.micasa.societyservice.model.Society;
import com.micasa.societyservice.service.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SocietyController
{
    private final SocietyService societyService;

    @Autowired
    public SocietyController(SocietyService societyService)
    {
        this.societyService = societyService;
    }

    @PostMapping("/add/society")
    public Society addSociety(@RequestBody Society society)
    {
        return societyService.addSociety(society);
    }

    @GetMapping("/get/society/{societyId}")
    public Society getSociety(@PathVariable String societyId)
    {
        return this.societyService.getSocietyById(societyId);
    }

    @DeleteMapping("/delete/society/{societyId}")
    public void deleteSocietyById(@PathVariable String societyId)
    {
        this.societyService.deleteSocietyById(societyId);
    }
}
