package it.moonril.family_recipes_backend.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {

        super(message);
    }
}
