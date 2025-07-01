package com.animal.animalShelter.domain.entities;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Entity
@Table(name = "animal_keeper")
public class AnimalKeeper {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "pesel", nullable = false)
    private String pesel;

    @OneToMany(mappedBy = "animalKeeper", cascade = {CascadeType.REMOVE, CascadeType.PERSIST })
    private List<Animal> animals;

    public AnimalKeeper() {
    }

    public AnimalKeeper(UUID id, String name, String surname, String pesel, List<Animal> animals) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.animals = animals;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalKeeper that = (AnimalKeeper) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(pesel, that.pesel) && Objects.equals(animals, that.animals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, pesel, animals);
    }

    @Override
    public String toString() {
        return "AnimalKeeper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", pesel='" + pesel + '\'' +
                ", animals=" + animals +
                '}';
    }
}
