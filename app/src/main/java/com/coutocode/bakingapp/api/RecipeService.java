package com.coutocode.bakingapp.api;

import com.coutocode.bakingapp.recipe.Recipe;
import com.coutocode.bakingapp.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RecipeService {
    RecipeAPI api;

    public RecipeService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .build();
        api = retrofit.create(RecipeAPI.class);
    }

    public Call<List<Recipe>> listRecipes() {
        return api.listRecipe();
    }
}
