package com.animal.animalShelter.domain.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "zone")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "zone", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Animal> animals;

    public Zone() {
    }

    public Zone(UUID id, String name, Integer size, String description, List<Animal> animals) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.description = description;
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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Zone zone = (Zone) o;
        return Objects.equals(id, zone.id) && Objects.equals(name, zone.name) && Objects.equals(size, zone.size) && Objects.equals(description, zone.description) && Objects.equals(animals, zone.animals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, size, description, animals);
    }

    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", description='" + description + '\'' +
                ", animals=" + animals +
                '}';
    }
}
