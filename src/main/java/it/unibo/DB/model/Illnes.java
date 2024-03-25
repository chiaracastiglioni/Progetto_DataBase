package it.unibo.DB.model;

public class Illnes {
    
    private String id;
    private String name;
    private String common_symptom;

    public Illnes(String id, String name, String common_symptom) {
        this.id = id;
        this.name = name;
        this.common_symptom = common_symptom;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCommon_symptom() {
        return common_symptom;
    }

}
