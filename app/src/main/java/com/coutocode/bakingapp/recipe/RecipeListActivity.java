package com.coutocode.bakingapp.recipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.coutocode.bakingapp.R;
import com.coutocode.bakingapp.api.RecipeService;
import com.coutocode.bakingapp.util.SimpleIdlingResource;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends AppCompatActivity {


    @BindView(R.id.rvRecipe)
    RecyclerView rvRecipe;

    private RecipeService service;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this, this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        rvRecipe.setLayoutManager(layoutManager);

        service = new RecipeService();

        requestData(service.listRecipes());

        getIdlingResource();
    }


    private void requestData(Call<List<Recipe>> call){
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()){
                    setUpList(response.body());
                }else{
                    Toast.makeText(RecipeListActivity.this,
                            response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(RecipeListActivity.this,
                        R.string.request_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpList(List<Recipe> recipes){
        rvRecipe.setAdapter(new RecipeAdapter(recipes, this));
    }

}
