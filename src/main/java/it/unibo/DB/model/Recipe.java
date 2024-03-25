package it.unibo.DB.model;

import java.util.Date;

public class Recipe {

    private int id;
    private String id_master;
    private String animal_name;
    private Date dateA;
    private String microchip;
    private String id_drug;
    private int clinic;
    private String duration;
    private String dose;
    private Date dateS;

    public Recipe(final int id, final String id_master, final String animal_name, final Date dateA,
    final String microchip, final String id_drug, final int clinic, final String duration, final String dose, final Date dateS) {
        this(id_master, animal_name, dateA, microchip, id_drug, clinic, duration, dose, dateS);
        this.id = id;
    }

    public Recipe(final String id_master, final String animal_name, final Date dateA,
    final String microchip, final String id_drug, final int clinic, final String duration, final String dose, final Date dateS) {
        this.id_master = id_master;
        this.animal_name = animal_name;
        this.dateA = dateA;
        this.microchip = microchip;
        this.id_drug = id_drug;
        this.clinic = clinic;
        this.duration = duration;
        this.dose = dose;
        this.dateS = dateS;
    }

    public int getId() {
        return id;
    }
    
    public String getId_master() {
        return id_master;
    }
    
    public String getAnimal_name() {
        return animal_name;
    }
    
    public Date getDateA() {
        return dateA;
    }
    
    public String getMicrochip() {
        return microchip;
    }
    
    public String getId_drug() {
        return id_drug;
    }
    
    public int getClinic() {
        return clinic;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public String getDose() {
        return dose;
    }
    
    public Date getDateS() {
        return dateS;
    }
}
