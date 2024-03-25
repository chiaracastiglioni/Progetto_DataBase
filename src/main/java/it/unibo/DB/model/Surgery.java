package it.unibo.DB.model;

public class Surgery {
    
    private String id;
    private String body;
    private String description;

   

    public void setDescription(String description) {
        this.description = description;
    }

    public Surgery(String id, String body, String description) {
        this.id = id;
        this.body = body;
        this.description = description;
    }
    
    public String getId() {
        return id;
    }
    
    public String getBody() {
        return body;
    }
    
    public String getDescription() {
        return description;
    }
}
