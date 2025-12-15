package it.moonril.family_recipes_backend.models;

import it.moonril.family_recipes_backend.enums.RecipeType;
import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "recipes")

public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_seq")
    @SequenceGenerator(name = "recipe_seq", sequenceName = "recipe_sequence", initialValue = 100, allocationSize = 1)
    private Integer id;

    private String image;
    @Column(unique = true)
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private RecipeType recipeType;


}
