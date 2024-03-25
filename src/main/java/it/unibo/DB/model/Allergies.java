package it.unibo.DB.model;

import java.util.Date;
import java.util.Optional;

public class Allergies {
    
    private String id;
    private Optional<String> antihistamine;
    private Date date;
    private String year;
    private int level_gravity;
    private String sympton;
    private String microchip;

    public Allergies(final String id, final Optional<String> antihistamine, final String year, final int level, final String sympton, final String microchip, final Date date) {
        this.id = id;
        this.antihistamine = antihistamine;
        this.date = date;
        this.year = year;
        this.level_gravity = level;
        this.sympton = sympton;
        this.microchip = microchip;
    }

    public Allergies(final String id, final String year, final int level, final String sympton, final String microchip, final Date date) {
       this(id, Optional.empty(), year, level, sympton, microchip, date);
    }
    
    
    public String getId() {
        return id;
    }
    
    public Optional<String> getAntihistamine() {
        return antihistamine;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String getYear() {
        return year;
    }
    
    public int getLevel_gravity() {
        return level_gravity;
    }
    
    public String getSympton() {
        return sympton;
    }
    
    public String getMicrochip() {
        return microchip;
    }
}
