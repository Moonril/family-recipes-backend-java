package it.moonril.family_recipes_backend.dto;

import it.moonril.family_recipes_backend.enums.RecipeType;
import it.moonril.family_recipes_backend.models.Ingredient;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {

    @NotBlank(message = "The image field cannot be empty")
    private String image;
    @NotBlank(message = "The title field cannot be empty")
    private String title;
    @NotBlank(message = "The description field cannot be empty")
    private String description;
    @NotNull(message = "The field recipeType cannot be empty")
    private RecipeType recipeType;

    @NotEmpty
    private List<String> ingredients;
}
