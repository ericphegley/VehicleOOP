package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Van;
import com.example.demo.model.Vehicle;
import com.example.demo.service.VehicleService;

@RestController
public class VehicleController {
	@Autowired
	private VehicleService vehicleService;
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);
	
	@GetMapping("/vehicles")
	public ResponseEntity<?> getVehicles(){
        logger.trace("TRACE log");  //detailed
        logger.debug("DEBUG log"); //debugging information //for production
        logger.info("INFO log");  //runtime info
        logger.warn("WARN log");	//unexpected events
        logger.error("ERROR log");  //exceptions and failures
		return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getAllVehicles());
	}
	
	
	@PostMapping("/vehicles/van")
	public ResponseEntity<?> saveVan(@RequestBody Van van){
		return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.save(van));
		
	}
	
	@PostMapping("/vehicles")
	public ResponseEntity<?> saveVehicle(@RequestBody Vehicle van){
		return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.save(van));
		
	}
	
	@GetMapping("/vehicles/{id}")
	public ResponseEntity<?> getVehicle(@PathVariable Long id){
		Vehicle v = vehicleService.getById(id);
		if(v != null)
			return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getById(id));
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("vehicle with id doesn't exist");
		}
		
	}

}
