package it.unibo.DB.model;

import java.util.Date;

public class Intervention {

    private int id;
    private String id_master;
    private String animal_name;
    private Date date;
    private String microchip;
    private String id_operation;
    private int clinic;
    private String motivation;

    public Intervention(final int id, final String id_master, final String animal_name, final Date date,
            final String microchip, final String id_operation, final int clinic, final String motivation) {
        this(id_master, animal_name, date, microchip, id_operation, clinic, motivation);
        this.id = id;

    }

    public Intervention(final String id_master, final String animal_name, final Date date, final String microchip,
            final String id_operation, final int clinic, final String motivation) {
        this.id_master = id_master;
        this.animal_name = animal_name;
        this.date = date;
        this.microchip = microchip;
        this.id_operation = id_operation;
        this.clinic = clinic;
        this.motivation = motivation;
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

    public String getId_operation() {
        return id_operation;
    }

    public int getClinic() {
        return clinic;
    }

    public String getMotivation() {
        return motivation;
    }
}
