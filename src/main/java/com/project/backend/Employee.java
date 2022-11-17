package com.project.backend;

public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private String photo;

    public Employee(String id, String firstName, String lastName, String photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return this.id + "," + this.firstName + "," + this.lastName + "," + this.photo;
    }
}
