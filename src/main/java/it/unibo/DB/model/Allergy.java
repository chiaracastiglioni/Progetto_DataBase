package it.unibo.DB.model;

public class Allergy {
    
    private String id;
    private String cause;

    public Allergy(String id, String cause) {
        this.id = id;
        this.cause = cause;
    }
    
    public String getId() {
        return id;
    }
    
    public String getCause() {
        return cause;
    }
}
