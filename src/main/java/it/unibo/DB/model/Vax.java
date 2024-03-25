package it.unibo.DB.model;

public class Vax {
    
    
    private String id;
    private String recall;
    private String mandatory;
    private String animal_species;
    private String illness;
    

    public Vax(String id, String recall, String mandatory, String animal_species, String illness) {
        this.id = id;
        this.recall = recall;
        this.mandatory = mandatory;
        this.animal_species = animal_species;
        this.illness = illness;
    }
    
    public String getId() {
        return id;
    }

    public String getRecall() {
        return recall;
    }
    
    public String getMandatory() {
        return mandatory;
    }
    
    public String getAnimal_species() {
        return animal_species;
    }
    
    public String getIllness() {
        return illness;
    }
}
