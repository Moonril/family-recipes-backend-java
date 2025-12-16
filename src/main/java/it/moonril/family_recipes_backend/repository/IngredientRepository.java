package it.moonril.family_recipes_backend.repository;

import it.moonril.family_recipes_backend.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer>, PagingAndSortingRepository<Ingredient, Integer> {
}
