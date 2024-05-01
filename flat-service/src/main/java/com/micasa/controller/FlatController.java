package com.micasa.controller;

import com.micasa.dto.FlatDto;
import com.micasa.model.Flat;
import com.micasa.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class FlatController
{
    private final FlatService flatService;

    @Autowired
    public FlatController(FlatService flatService)
    {
        this.flatService = flatService;
    }

    @PostMapping(value = "/add/flat")
    public FlatDto addFlat(@RequestBody  FlatDto flatDto)
    {
        return this.flatService.addNewFlat(flatDto);
    }

    @GetMapping("/find/{flatId}")
    public FlatDto findFlatById(@PathVariable String flatId)
    {
        return this.flatService.findFlatByFlatId(flatId);
    }
}
