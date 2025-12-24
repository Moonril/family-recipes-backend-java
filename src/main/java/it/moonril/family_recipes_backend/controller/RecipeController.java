package it.moonril.family_recipes_backend.controller;

import it.moonril.family_recipes_backend.dto.RecipeDto;
import it.moonril.family_recipes_backend.exceptions.NotFoundException;
import it.moonril.family_recipes_backend.exceptions.ValidationException;
import it.moonril.family_recipes_backend.models.Recipe;
import it.moonril.family_recipes_backend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.NotActiveException;
import java.util.List;

@RestController
@RequestMapping(path = "/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public Page<Recipe> getAllRecipes(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return recipeService.getAllRecipes(page, size, sortBy);
    }

    @GetMapping("/id")
    public Recipe getRecipeById(@PathVariable int id) throws NotFoundException {
        return recipeService.getRecipe(id);
    }

    @GetMapping("/title")
    public List<Recipe> getRecipesByTitle(@PathVariable String title) throws NotFoundException {
        return recipeService.getRecipesByTitle(title);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')") //todo cambia gestione authority
    public Recipe saveRecipe(@RequestBody @Validated RecipeDto recipeDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (e, s) -> e + s));
        }
        return recipeService.saveRecipe(recipeDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Recipe updateRecipe(@PathVariable int id, @RequestBody @Validated RecipeDto recipeDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (e, s) -> e + s));
        }
        return recipeService.updateRecipe(id, recipeDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRecipe(@PathVariable int id) throws NotFoundException {
        recipeService.deleteRecipe(id);
    }

}
