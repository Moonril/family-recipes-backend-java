package it.moonril.family_recipes_backend.controller;

import it.moonril.family_recipes_backend.dto.IngredientDto;
import it.moonril.family_recipes_backend.dto.RecipeDto;
import it.moonril.family_recipes_backend.exceptions.NotFoundException;
import it.moonril.family_recipes_backend.exceptions.ValidationException;
import it.moonril.family_recipes_backend.models.Ingredient;
import it.moonril.family_recipes_backend.models.Recipe;
import it.moonril.family_recipes_backend.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public Page<Ingredient> getAllIngredient(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        return ingredientService.getAllIngredients(page, size, sortBy);
    }

    @GetMapping("/id")
    public Ingredient getIngredientById(@PathVariable int id) throws NotFoundException {
        return ingredientService.getIngredient(id);
    }

    @GetMapping("/name")
    public List<Ingredient> getIngredientsByName(@PathVariable String name) throws NotFoundException {
        return ingredientService.getIngredientsByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Ingredient saveIngredient(@RequestBody @Validated IngredientDto ingredientDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (e, s) -> e + s));
        }
        return ingredientService.saveIngredient(ingredientDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Ingredient updateIngredient(@PathVariable int id, @RequestBody @Validated IngredientDto ingredientDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (e, s) -> e + s));
        }
        return ingredientService.updateIngredient(id, ingredientDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteIngredient(@PathVariable int id) throws NotFoundException {
        ingredientService.deleteIngredient(id);
    }
}
