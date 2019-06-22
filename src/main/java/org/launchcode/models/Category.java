package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15, message="Name must be 3--15 characters.")
    private String name;

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Cheese> cheeses = new ArrayList<>();
    /* Declared as List<> because in Java it is a good idea to declare the most general type (highest in the hierarchy)
    as possible. This is because Java creates a space in memory that can only be filled by objects of the type declared.
    You can only put objects of that general type or a species of that type in the space in memory that is created. If
    you were to declare this as an ArrayList, you could only switch it to another ArrayList or a subtype of ArrayList.
    By declaring it as a List, you can switch it to a broader range of data types--List or any of its children/subtypes.
     */

    public Category(){

    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }
}

