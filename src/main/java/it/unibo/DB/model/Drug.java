package it.unibo.DB.model;

public class Drug {
    
    private String id;
    private String name;
    private String description;
    private String price;

    public Drug(final String id, final String name, final String description, final String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPrice() {
        return price;
    }
    
    public String getDescription() {
        return description;
    }
}
