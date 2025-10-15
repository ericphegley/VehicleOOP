package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Van;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.VehicleRepository;

@Service
public class VehicleService {
	@Autowired
	VehicleRepository vehicleRepository;

	
    public List<Vehicle> getAllVehicles() {
    	List<Vehicle> vehicles = vehicleRepository.findAll();
    	for(Vehicle v : vehicles) {
    		System.out.println(v.print());
    	}
        return vehicles;
    }
    
    
    public Vehicle save(Van van) {
    	return vehicleRepository.save(van);
    }
    
    
}
