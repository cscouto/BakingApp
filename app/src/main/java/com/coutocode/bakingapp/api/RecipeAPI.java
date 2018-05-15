package com.coutocode.bakingapp.api;

import com.coutocode.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;

public interface RecipeAPI {
    @GET("")
    Call<List<Recipe>> listRecipe();
}
