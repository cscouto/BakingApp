package com.coutocode.bakingapp.model;

public class Ingredient {
    float quantity;
    String measure;
    String ingredient;

    Ingredient(float quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}