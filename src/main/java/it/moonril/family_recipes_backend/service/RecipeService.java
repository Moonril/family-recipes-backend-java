package it.moonril.family_recipes_backend.service;

import it.moonril.family_recipes_backend.dto.RecipeDto;
import it.moonril.family_recipes_backend.exceptions.EmailAlreadyExistsException;
import it.moonril.family_recipes_backend.exceptions.NotFoundException;
import it.moonril.family_recipes_backend.models.Recipe;
import it.moonril.family_recipes_backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe saveRecipe(RecipeDto recipeDto) throws NotFoundException {
        Optional<Recipe> existingRecipe = recipeRepository.findByTitle(recipeDto.getTitle());

        if (existingRecipe.isPresent()) {
            throw new EmailAlreadyExistsException(recipeDto.getTitle());
        }
        Recipe recipe = new Recipe();

        recipe.setImage(recipeDto.getImage());
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setRecipeType(recipeDto.getRecipeType());

        return recipeRepository.save(recipe);
    }
}
