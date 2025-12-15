package it.moonril.family_recipes_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ingredients")

public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_seq")
    @SequenceGenerator(name = "ingredient_seq", sequenceName = "ingredient_sequence", initialValue = 100, allocationSize = 1)
    private Integer id;

    @Column(unique = true)
    private String name;
}
