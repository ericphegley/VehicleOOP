package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.Van;
import com.example.demo.model.Vehicle;
import com.example.demo.service.VehicleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {
	@Autowired
	MockMvc mock;
	
	@MockitoBean
	VehicleService vehicleService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	Map<Integer, Vehicle> vehicleMap = new HashMap<>();
	
	@BeforeEach
	void initialize() {
		vehicleMap.put(1, new Van("Ford", 2000));
		vehicleMap.put(2, new Van("Toyota", 5000));
		vehicleMap.put(3, new Van("Hyundai", 8000));

	}
	
	@Test
	void findAll() throws Exception {
		when(vehicleService.getAllVehicles()).thenReturn(
				List.copyOf(vehicleMap.values())
		);
		mock.perform(get("/vehicles"))
	       .andExpect(status().isOk());
	}
	
	@Test
	void findByIdValid() throws Exception {
	    Van van = new Van("Ford", 2000);
	    when(vehicleService.getById((long)1)).thenReturn(van);

	    mock.perform(get("/vehicles/1"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.manufacturer").value("Ford"))
	        .andExpect(jsonPath("$.price").value(2000));
	}
}
