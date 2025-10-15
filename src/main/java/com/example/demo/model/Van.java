package com.example.demo.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("VanRed")
public class Van extends Vehicle {

	public Van() {
		
	}
    public Van(String manufacturer, int price) {
        super(manufacturer, price);
    }

    @Override
    public String print() {
        return "I am a " + manufacturer + " Van for $" + price;
    }

}
