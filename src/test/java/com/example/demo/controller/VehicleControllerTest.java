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
	
	
	//testing findall
	@Test
	void findAll() throws Exception {
		when(vehicleService.getAllVehicles()).thenReturn(
				List.copyOf(vehicleMap.values())
		);
		mock.perform(get("/vehicles"))
	       .andExpect(status().isOk());
	}
	
	//testing findall when no list
	@Test
	void findAllEmptyList() throws Exception {
	    when(vehicleService.getAllVehicles()).thenReturn(List.of());

	    mock.perform(get("/vehicles"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$").isEmpty());
	}
	
	//testing findbyid
	@Test
	void findByIdValid() throws Exception {
	    Van van = new Van("Ford", 2000);
	    when(vehicleService.getById(1L)).thenReturn(van);

	    mock.perform(get("/vehicles/1"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.manufacturer").value("Ford"))
	        .andExpect(jsonPath("$.price").value(2000));
	}
	
	//testing findbyid not found
	@Test
	void findByIdNotFound() throws Exception {
	    when(vehicleService.getById(99L)).thenReturn(null);

	    mock.perform(get("/vehicles/99"))
	        .andExpect(status().isNotFound())
	        .andExpect(jsonPath("$").value("vehicle with id doesn't exist"));
	}
	

	
	//testing saving van
	@Test
	void saveVan() throws Exception {
	    Van van = new Van("Chevy", 10000);
	    when(vehicleService.save(any(Van.class))).thenReturn(van);

	    mock.perform(post("/vehicles/van")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(van)))
	        .andExpect(status().isCreated())
	        .andExpect(jsonPath("$.manufacturer").value("Chevy"))
	        .andExpect(jsonPath("$.price").value(10000));

	    verify(vehicleService).save(any(Van.class));
	}
}
