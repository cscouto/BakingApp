package com.coutocode.bakingapp.recipe;

public class Ingredient {
    final float quantity;
    final String measure;
    public final String ingredient;

    Ingredient(float quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}
