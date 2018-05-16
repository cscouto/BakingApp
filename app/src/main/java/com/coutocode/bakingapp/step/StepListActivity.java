package com.coutocode.bakingapp.step;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.coutocode.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListActivity extends AppCompatActivity {

    @BindView(R.id.rvStep)
    RecyclerView rvStep;

    private boolean mTwoPane;
    private ArrayList<RecipeStep> mValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ButterKnife.bind(this);

        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvStep.setLayoutManager(layoutManager);

        mValues = getIntent().getParcelableArrayListExtra(getString(R.string.extra_recipe));

        rvStep.setAdapter(new StepAdapter(this, mValues, mTwoPane));
    }
}
