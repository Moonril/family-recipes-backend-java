package it.moonril.family_recipes_backend.repository;

import it.moonril.family_recipes_backend.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>, PagingAndSortingRepository<Recipe, Integer> {
    Optional<Recipe> findByTitle(String name);
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Recipe> findByTitleContainingIgnoreCase(@Param("title") String name);
}
