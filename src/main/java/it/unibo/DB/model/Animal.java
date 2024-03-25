package it.unibo.DB.model;

import java.util.Date;
import java.util.Objects;

public class Animal {
    private final String id;
    private final String name;
    private final String race;
    private final String animal_species;
    private final Date birthday;
    private final String gender;
    private final String id_m;
    
    public Animal(final String id, final String firstName, final String race, final String animal_species, final String gender, final String id_master, final Date birthDate) {
        this.id = id;
        this.name = Objects.requireNonNull(firstName);
        this.race= Objects.requireNonNull(race);
        this.animal_species = Objects.requireNonNull(animal_species);
        this.gender = Objects.requireNonNull(gender);
        this.id_m = Objects.requireNonNull(id_master);
        this.birthday = Objects.requireNonNull(birthDate);
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }

    public String getRace() {
        return this.race;
    }

    public String getAnimal_species() {
        return this.animal_species;
    }
    
    public String getGender() {
        return this.gender;
    }

    public String getId_m() {
        return this.id_m;
    }
    
    public Date getBirthday() {
        return birthday;
    }
   
    @Override
    public boolean equals(final Object other) {
        return (other instanceof Animal)
                && ((Animal) other).getId() == this.getId()
                && ((Animal) other).getName().equals(this.getName())
                && ((Animal) other).getRace().equals(this.getRace())
                && ((Animal) other).getAnimal_species().equals(this.getAnimal_species())
                && ((Animal) other).getGender().equals(this.getGender());
    }
}


