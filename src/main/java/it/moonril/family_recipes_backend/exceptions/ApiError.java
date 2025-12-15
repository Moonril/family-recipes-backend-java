package it.moonril.family_recipes_backend.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private String message;
    private LocalDateTime dataErrore;
}
