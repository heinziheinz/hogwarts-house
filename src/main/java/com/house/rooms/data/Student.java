package com.house.rooms.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Student {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private Gender gender; // Use the Gender enum
    private House house;

    @ManyToOne // Defines the many-to-one relationship
    @JsonIgnore // Add @JsonIgnore to prevent infinite loop
    private Room room; // The room associated with the student

    // Constructors, getters, and setters

    // Getter for gender
    public House getHouse() {
        return house;
    }

    // Setter for house
    public void setHouse(House house) {
        this.house = house;
    }

    public Gender getGender() {
        return gender;
    }

    // Setter for gender
    public void setGender(Gender gender) {
        this.gender = gender;
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
