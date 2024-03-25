package it.unibo.DB.model;

import java.util.Date;

public class Vacination {

    private int id;
    private String id_master;
    private String animal_name;
    private Date date;
    private String microchip;
    private String id_vax;
    private int clinic;
    private Date dateR;

    public Vacination(final int id, final String id_master, final String animal_name, final Date date, final String microchip, final String id_vax, final int clinic, final Date dateR) {
        this(id_master, animal_name, date, microchip, id_vax, clinic, dateR);
        this.id = id;
        
    }

    public Vacination(final String id_master, final String animal_name, final Date date, final String microchip, final String id_vax, final int clinic, final Date dateR) {
        this.id_master = id_master;
        this.animal_name = animal_name;
        this.date = date;
        this.microchip = microchip;
        this.id_vax = id_vax;
        this.clinic = clinic;
        this.dateR = dateR;
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

    public Date getDate() {
        return date;
    }

    public String getMicrochip() {
        return microchip;
    }

    public String getId_vax() {
        return id_vax;
    }
    
    public int getClinic() {
        return clinic;
    }
    
    public Date getDateR() {
        return dateR;
    }
}
