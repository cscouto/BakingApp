package com.coutocode.bakingapp.step;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.coutocode.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    private RecipeStep step;
    private StepDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        step = getIntent().getParcelableExtra(getString(R.string.extra_step));

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            fragment = new StepDetailFragment();
            fragment.step = step;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }else {
            fragment = (StepDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState,
                    getString(R.string.extra_fragment));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (fragment != null) {
                fragment.pressedBack();
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, getString(R.string.extra_fragment), fragment);
        super.onSaveInstanceState(outState);
    }
}
