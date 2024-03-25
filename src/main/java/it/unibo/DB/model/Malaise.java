package it.unibo.DB.model;

import java.util.Date;
import java.util.Optional;

public class Malaise {
    
    private String id;
    private Date date_start;
    private Optional<Date> date_finish;
    private String symptom;
    private String microchip;

    public Malaise(final String id, final Date date_start, final Optional<Date> date_finish, final String symptom, final String microchip) {
        this.id = id;
        this.date_start = date_start;
        this.date_finish = date_finish;
        this.symptom = symptom;
        this.microchip = microchip;
    }

    public Malaise(final String id, final Date date_start, final String symptom, final String microchip) {
        this(id, date_start,  Optional.empty(), symptom, microchip);
    }

    public String getId() {
        return id;
    }

    public Date getDate_start() {
        return date_start;
    }

    public Optional<Date> getOptionalDate_finish() {
        return date_finish;
    }

    public Date getDate_finish() {
        if(date_finish != null) {
            return date_finish.get();
        }
        else {
            return null;
        }
    }

    public String getSymptom() {
        return symptom;
    }

    public String getMicrochip() {
        return microchip;
    }
    
    public void setDate_finish(final Optional<Date> dateF) {
        this.date_finish = dateF;
    }
}
