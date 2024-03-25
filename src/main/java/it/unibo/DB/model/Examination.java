package it.unibo.DB.model;

import java.util.Date;

public class Examination {

    private int id;
    private String id_master;
    private String animal_name;
    private Date date;
    private String microchip;
    private String id_exam;
    private int clinic;
    private String result;
    private String exam_motivation;

    public Examination(final int id, final String id_master, final String animal_name, final Date date,
            final String microchip, final String id_exam, final int clinic, final String result,
            final String exam_motivation) {
        this(id_master, animal_name, date, microchip, id_exam, clinic, result, exam_motivation);
        this.id = id;

    }

    public Examination(final String id_master, final String animal_name, final Date date, final String microchip,
            final String id_exam, final int clinic, final String result, final String exam_motivation) {
        this.id_master = id_master;
        this.animal_name = animal_name;
        this.date = date;
        this.microchip = microchip;
        this.id_exam = id_exam;
        this.clinic = clinic;
        this.result = result;
        this.exam_motivation = exam_motivation;
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

    public String getId_exam() {
        return id_exam;
    }

    public int getClinic() {
        return clinic;
    }

    public String getResult() {
        return result;
    }

    public String getExam_motivation() {
        return exam_motivation;
    }

}
