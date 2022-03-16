package com.istl.elasticsearch.controller;

import com.istl.elasticsearch.model.Vehicle;
import com.istl.elasticsearch.search.SearchRequestDTO;
import com.istl.elasticsearch.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public boolean save(@RequestBody final Vehicle vehicle){
        return vehicleService.index(vehicle);
    }

    @GetMapping("/{id}")
    public Vehicle save(@PathVariable String id) throws IllegalArgumentException, IOException {
        return vehicleService.getById(id);
    }

    @PostMapping("/searchVehicle")
    public List<Vehicle> searchVehicle(@RequestBody final SearchRequestDTO dto){
        System.out.println(dto);
        return vehicleService.search(dto);
    }
}
