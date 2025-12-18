package it.moonril.family_recipes_backend.repository;

import it.moonril.family_recipes_backend.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>, PagingAndSortingRepository<Recipe, Integer> {
    Optional<Recipe> findByTitle(String name);
}
