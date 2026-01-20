package it.moonril.family_recipes_backend.service;

import it.moonril.family_recipes_backend.dto.RecipeDto;
import it.moonril.family_recipes_backend.exceptions.RecipeAlreadyExistsException;
import it.moonril.family_recipes_backend.exceptions.NotFoundException;
import it.moonril.family_recipes_backend.models.Ingredient;
import it.moonril.family_recipes_backend.models.Recipe;
import it.moonril.family_recipes_backend.repository.IngredientRepository;
import it.moonril.family_recipes_backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public Recipe saveRecipe(RecipeDto recipeDto) throws NotFoundException {
        Optional<Recipe> existingRecipe = recipeRepository.findByTitle(recipeDto.getTitle());

        if (existingRecipe.isPresent()) {
            throw new RecipeAlreadyExistsException(recipeDto.getTitle());
        }
        Recipe recipe = new Recipe();

        recipe.setImage(recipeDto.getImage());
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setRecipeType(recipeDto.getRecipeType());


        List<Ingredient> recipeIngredients = new ArrayList<>();

        for (String ingredientName : recipeDto.getIngredients()) {

            Ingredient ingredient = ingredientRepository
                    .findByNameIgnoreCase(ingredientName.trim())
                    .orElseGet(() -> {
                        Ingredient newIngredient = new Ingredient();
                        newIngredient.setName(ingredientName.trim());
                        return ingredientRepository.save(newIngredient);
                    });

            recipeIngredients.add(ingredient);
        }

        recipe.setIngredients(recipeIngredients);

        return recipeRepository.save(recipe);
    }

    public Page<Recipe> getAllRecipes(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return recipeRepository.findAll(pageable);
    }

    public Recipe getRecipe(int id) throws NotFoundException{
        return recipeRepository.findById(id).orElseThrow(() -> new NotFoundException("Recipe with id: " + id + " not found"));
    }

    public List<Recipe> getRecipesByTitle(String title) throws NotFoundException{
        List<Recipe> recipes = recipeRepository.findByTitleContainingIgnoreCase(title);

        if (recipes.isEmpty()) {
            throw new NotFoundException("Nessuna ricetta trovata con questo nome: " + title);
        }

        return recipes;
    }

    public Recipe updateRecipe(int id, RecipeDto recipeDto) throws NotFoundException{
        Recipe recipeToUpdate = getRecipe(id);

        recipeToUpdate.setImage(recipeDto.getImage());
        recipeToUpdate.setTitle(recipeDto.getTitle());
        recipeToUpdate.setDescription(recipeDto.getDescription());
        recipeToUpdate.setRecipeType(recipeDto.getRecipeType());

        return recipeRepository.save(recipeToUpdate);
    }

    public void deleteRecipe(int id) throws NotFoundException{
        Recipe recipeToDelete = getRecipe(id);

        recipeRepository.delete(recipeToDelete);
    }

}
