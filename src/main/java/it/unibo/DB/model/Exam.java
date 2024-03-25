package it.unibo.DB.model;


public class Exam {
  
    private String id;
    private String name; 

    public Exam(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}
