package com.coutocode.bakingapp.recipe;

public class Ingredient {
    float quantity;
    String measure;
    public String ingredient;

    Ingredient(float quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}
