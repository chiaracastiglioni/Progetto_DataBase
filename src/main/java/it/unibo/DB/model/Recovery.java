package it.unibo.DB.model;


public class Recovery {

    private String microchip;
    private String date_Start;
    private String date_Stop;
    private Integer range_Start;
    private Integer range_Stop;


    public Recovery(final String microchip, final String date_Start, final String date_Stop, final Integer range_Start, final Integer range_Stop) {
        this.microchip = microchip;
        this.date_Start = date_Start;
        this.date_Stop = date_Stop;
        this.range_Start = range_Start;
        this.range_Stop = range_Stop;
    }
    
    public String getMicrochip() {
        return microchip;
    }
    
    public String getDate_Start() {
        return date_Start;
    }

    public String getDate_Stop() {
        return date_Stop;
    }
    
    public Integer getRange_Start() {
        return range_Start;
    }
    
    public Integer getRange_Stop() {
        return range_Stop;
    }

}
