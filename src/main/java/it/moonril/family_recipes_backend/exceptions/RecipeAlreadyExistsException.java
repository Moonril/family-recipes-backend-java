package it.moonril.family_recipes_backend.exceptions;

public class RecipeAlreadyExistsException extends RuntimeException {
    public RecipeAlreadyExistsException(String recipe) {
        super("This recipe already exists: " + recipe);
    }
}
