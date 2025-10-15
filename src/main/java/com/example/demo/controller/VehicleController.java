package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Van;
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
		return ResponseEntity.status(HttpStatus.FOUND).body(vehicleService.getAllVehicles());
	}
	
	
	@PostMapping("/vehicles")
	public ResponseEntity<?> saveVehicle(@RequestBody Van van){
		return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.save(van));
		
	}
}
