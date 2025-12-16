package it.moonril.family_recipes_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IngredientDto {
    @NotBlank(message = "The name field cannot be empty")
    private String name;
}
