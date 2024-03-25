package it.unibo.DB.model;

public class Frequence {
    
    private String id_ill;
    private int frequence;

    public Frequence(final String id, final int frequence) {
        this.id_ill = id;
        this.frequence = frequence;
    }
    
    public String getId_ill() {
        return id_ill;
    }
    
    public int getFrequence() {
        return frequence;
    }
}
