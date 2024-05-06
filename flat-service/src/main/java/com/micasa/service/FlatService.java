package com.micasa.service;

import com.micasa.model.Flat;
import com.micasa.model.User;

import java.util.List;

public interface FlatService
{
    Flat addFlat(Flat flat);
    Flat updateOccupiedStatus(String flatId, boolean occupiedStatus);
    void deleteFlatById(String flatId);
    Flat findFlatByFlatId(String flatId);
    List<Flat> findFlatByBlockNumber(String blockNumber);
    List<Flat> findFlatByFlatNumber(String flatNumber);
    Flat findFlatByBlockNumberAndFlatNumber(String blockNumber, String flatNumber);
    Flat addUserToFlat(String flatId, User user);
}
