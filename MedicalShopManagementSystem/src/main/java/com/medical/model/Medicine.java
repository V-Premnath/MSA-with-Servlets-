package com.medical.model;

import java.time.LocalDateTime;

public class Medicine {
    // Properties
    private Integer medicineId;
    private String name;
    private String image;
    private String description;
    private String dosage;
    private String manufacturer;
    private boolean requiresPrescription;
    private float price;
    private Integer quantityInStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default Constructor
    public Medicine() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Parameterized Constructor
    public Medicine(String name, String description, String dosage,
                   String manufacturer, boolean requiresPrescription,
                   float price, Integer quantityInStock) {
        this.name = name;
        this.description = description;
        this.dosage = dosage;
        this.manufacturer = manufacturer;
        this.requiresPrescription = requiresPrescription;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

    }

    public Medicine(Medicine singleMedicine) {
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
    	this.image=image;
    }

    public String getImage() {
    	return image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public boolean isRequiresPrescription() {
        return requiresPrescription;
    }

    public void setRequiresPrescription(boolean requiresPrescription) {
        this.requiresPrescription = requiresPrescription;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Utility Methods
    public boolean isInStock() {
        return this.quantityInStock > 0;
    }

    public boolean isLowInStock(int threshold) {
        return this.quantityInStock < threshold;
    }

    public void updateStock(int quantity) {
        this.quantityInStock += quantity;
        this.updatedAt = LocalDateTime.now();
    }

    public void decreaseStock(int quantity) throws IllegalArgumentException {
        if (this.quantityInStock < quantity) {
            throw new IllegalArgumentException("Insufficient stock available");
        }
        this.quantityInStock -= quantity;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "medicineId=" + medicineId +
                ", name='" + name + '\'' +
                ", dosage='" + dosage + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", requiresPrescription=" + requiresPrescription +
                ", price=" + price +
                ", quantityInStock=" + quantityInStock +
                '}';
    }

    // Example usage with validation


    // Business logic methods
    public boolean canBeSoldWithoutPrescription() {
        return !this.requiresPrescription;
    }

    public float calculateTotalPrice(int quantity) {
    	float out=this.price*quantity;
        return out;
    }
}