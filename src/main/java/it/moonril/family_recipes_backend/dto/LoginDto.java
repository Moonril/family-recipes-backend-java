package it.moonril.family_recipes_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @NotEmpty(message = "The field username cannot be empty")
    private String username;
    @NotEmpty(message = "The field password cannot be empty")
    private String password;
}
