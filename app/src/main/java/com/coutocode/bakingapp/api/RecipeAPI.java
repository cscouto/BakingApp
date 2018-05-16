package com.coutocode.bakingapp.api;

import com.coutocode.bakingapp.recipe.Recipe;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;

public interface RecipeAPI {
    @GET("baking.json")
    Call<List<Recipe>> listRecipe();
}
