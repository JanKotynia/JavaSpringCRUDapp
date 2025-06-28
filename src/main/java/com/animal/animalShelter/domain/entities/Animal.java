package com.animal.animalShelter.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "admission_date")
    private LocalDateTime admissionDate;

    @Column(name = "spicies")
    private Species species;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_keeper_id")
    private AnimalKeeper animalKeeper;

    public Animal() {
    }

    public Animal(UUID id, String name, String description, LocalDateTime admissionDate, Species species, Zone zone, AnimalKeeper animalKeeper) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.admissionDate = admissionDate;
        this.species = species;
        this.zone = zone;
        this.animalKeeper = animalKeeper;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getAdmissionDate() {
        return admissionDate;
    }

    public Species getSpecies() {
        return species;
    }

    public UUID getZone() {
        return zone.getId();
    }

    public UUID getAnimalKeeper() {
        return animalKeeper.getId();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAdmissionDate(LocalDateTime admissionDate) {
        this.admissionDate = admissionDate;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setAnimalKeeper(AnimalKeeper animalKeeper) {
        this.animalKeeper = animalKeeper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id) && Objects.equals(name, animal.name) && Objects.equals(description, animal.description) && Objects.equals(admissionDate, animal.admissionDate) && species == animal.species && Objects.equals(zone, animal.zone) && Objects.equals(animalKeeper, animal.animalKeeper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, admissionDate, species, zone, animalKeeper);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", admissionDate=" + admissionDate +
                ", species=" + species +
                ", zone=" + zone +
                ", animalKeeper=" + animalKeeper +
                '}';
    }
}
