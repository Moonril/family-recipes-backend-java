package it.moonril.family_recipes_backend.repository;

import it.moonril.family_recipes_backend.models.Ingredient;
import it.moonril.family_recipes_backend.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer>, PagingAndSortingRepository<Ingredient, Integer> {
    Optional<Ingredient> findByName(String name);
    @Query("SELECT n FROM Ingredient n WHERE LOWER(n.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Ingredient> findByNameContainingIgnoreCase(@Param("name") String name);
}
