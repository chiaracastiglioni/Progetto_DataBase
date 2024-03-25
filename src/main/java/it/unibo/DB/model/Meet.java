package it.unibo.DB.model;

import java.util.Date;

public class Meet {
    
    private String id_master;
    private Date date;
    private String animalName;
    private Double hours;
    private String performance_performed;
    private String id_vet;
    private String hoursString;

    

    public Meet(String id_master, String aniamalName, Double hours, String performance_p, String id_vet, Date date) {
        this.id_master = id_master;
        this.date = date;
        this.animalName = aniamalName;
        this.hours = hours;
        this.performance_performed = performance_p;
        this.id_vet = id_vet;
    }

    public String getId_master() {
        return id_master;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String getAnimalName() {
        return animalName;
    }
    
    public Double getHours() {
        return hours;
    }
    
    public String getPerformance_performed() {
        return performance_performed;
    }
    
    public String getId_vet() {
        return id_vet;
    }

    public String getHoursString() {
        return hoursString;
    }

    public void setHoursString(String hoursString) {
        String h = hoursString.substring(0, 2);
        int dimension = hoursString.length();
        if (dimension == 5) {
            String m = hoursString.substring(3, 5);
            String hours = h + ":" + m;
            this.hoursString = hours;
        }
        else {
            String m = hoursString.substring(3, 4);
            String hours = h + ":" + m + "0";
            this.hoursString = hours;
        }
    }
}
