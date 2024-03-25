package it.unibo.DB.model;

public class ListR {
    
    private int range_Start;
    private int range_Stop;
    private String price;

    public ListR(final int range_Start, final int range_Stop, final String price) {
        this.range_Start = range_Start;
        this.range_Stop = range_Stop;
        this.price = price;
    }
    
    public int getRange_Start() {
        return range_Start;
    }
    
    public String getPrice() {
        return price;
    }
    
    public int getRange_Stop() {
        return range_Stop;
    }
}
