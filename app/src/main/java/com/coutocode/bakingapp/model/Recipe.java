package com.coutocode.bakingapp.model;

import com.coutocode.bakingapp.step.RecipeStep;

import java.util.List;

public class Recipe {
    public int id;
    String name;
    List<Ingredient> ingredients;
    List<RecipeStep> steps;
    int servings;
    String image;

    Recipe(int id, String name, List<Ingredient> ingredients, List<RecipeStep> steps,
           int servings, String image){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }
}
