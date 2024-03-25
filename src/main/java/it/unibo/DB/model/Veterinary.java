package it.unibo.DB.model;

import java.util.Date;

public class Veterinary {
    private String id;
    private String firstName;
    private String lastName;
    private String number;
    private String email;
    private Date hireDate;
    private String specialization;

    
    public Veterinary(String id, String name, String lastName, String number, String email, Date hirDate, String specialization) {
        this.id = id;
        this.firstName = name;
        this.lastName = lastName;
        this.number = number;
        this.email = email;
        this.hireDate = hirDate;
        this.specialization = specialization;
    }

    public String getId() {
        return this.id;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }

    public String getNumber() {
        return this.number;
    }

    public String getEmail() {
        return this.email;
    }
    
    public Date getHireDate() {
        return hireDate;
    }
    
    public String getSpecialization() {
        return specialization;
    }
}
