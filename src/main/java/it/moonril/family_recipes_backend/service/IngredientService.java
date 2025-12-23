package it.moonril.family_recipes_backend.service;

import it.moonril.family_recipes_backend.dto.IngredientDto;
import it.moonril.family_recipes_backend.dto.RecipeDto;
import it.moonril.family_recipes_backend.exceptions.EmailAlreadyExistsException;
import it.moonril.family_recipes_backend.exceptions.NotFoundException;
import it.moonril.family_recipes_backend.models.Ingredient;
import it.moonril.family_recipes_backend.models.Recipe;
import it.moonril.family_recipes_backend.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient saveIngredient(IngredientDto ingredientDto) throws NotFoundException {
        Optional<Ingredient> existingIngredient = ingredientRepository.findByName(ingredientDto.getName());

        if (existingIngredient.isPresent()) {
            throw new EmailAlreadyExistsException(ingredientDto.getName());
        }
        Ingredient ingredient = new Ingredient();

        ingredient.setName(ingredientDto.getName());


        return ingredientRepository.save(ingredient);
    }

    public Page<Ingredient> getAllIngredients(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ingredientRepository.findAll(pageable);
    }

    public Ingredient getIngredient(int id) throws NotFoundException {
        return ingredientRepository.findById(id).orElseThrow(() -> new NotFoundException("Ingredient with id: " + id + " not found"));
    }

    public List<Ingredient> getIngredientsByName(String name) throws NotFoundException{
        List<Ingredient> ingredients = ingredientRepository.findByNameContainingIgnoreCase(name);

        if (ingredients.isEmpty()) {
            throw new NotFoundException("Nessun ingrediente trovato con questo nome: " + name);
        }

        return ingredients;
    }

    public Ingredient updateIngredient(int id, IngredientDto ingredientDto) throws NotFoundException{
        Ingredient ingredientToUpdate = getIngredient(id);

        ingredientToUpdate.setName(ingredientDto.getName());


        return ingredientRepository.save(ingredientToUpdate);
    }

    public void deleteIngredient(int id) throws NotFoundException{
        Ingredient ingredientToDelete = getIngredient(id);

        ingredientRepository.delete(ingredientToDelete);
    }
}
