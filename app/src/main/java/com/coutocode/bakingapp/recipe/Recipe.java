package com.coutocode.bakingapp.recipe;

import com.coutocode.bakingapp.step.RecipeStep;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private final int id;
    final String name;
    public final List<Ingredient> ingredients;
    final ArrayList<RecipeStep> steps;
    final int servings;
    final String image;

    Recipe(int id, String name, List<Ingredient> ingredients, List<RecipeStep> steps,
           int servings, String image){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = (ArrayList<RecipeStep>) steps;
        this.servings = servings;
        this.image = image;
    }
}
