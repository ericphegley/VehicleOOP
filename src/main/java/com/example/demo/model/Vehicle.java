package com.example.demo.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //JOINED //TABLE_PER_CLASS 
@DiscriminatorColumn(name = "vehicle_type")
public abstract class Vehicle {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    protected String manufacturer;
    protected int price;
    
    public Vehicle() {
    	
    }

    public Vehicle(String manufacturer, int price) {
        this.manufacturer = manufacturer;
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public abstract String print();
}
