package com.coutocode.bakingapp.step;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.coutocode.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.rvStep)
    RecyclerView rvStep;

    private boolean mTwoPane;
    private ArrayList<RecipeStep> mValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvStep.setLayoutManager(layoutManager);

        if (savedInstanceState != null) {
            mValues = savedInstanceState.getParcelableArrayList(getString(R.string.extra_recipe));
        }else{
            mValues = getIntent().getParcelableArrayListExtra(getString(R.string.extra_recipe));
        }

        rvStep.setAdapter(new StepAdapter(this, mValues, mTwoPane));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.extra_recipe), mValues);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mValues = savedInstanceState.getParcelableArrayList(getString(R.string.extra_recipe));
        super.onRestoreInstanceState(savedInstanceState);
    }
}
