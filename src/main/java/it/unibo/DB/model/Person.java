package it.unibo.DB.model;

public class Person {
    private String id;
    private String firstName;
    private String lastName;
    private String number;
    private String email;

    

    public Person(String id, String name, String lastName, String number, String email) {
        this.id = id;
        this.firstName = name;
        this.lastName = lastName;
        this.number = number;
        this.email = email;
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

}
