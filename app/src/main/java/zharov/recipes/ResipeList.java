package zharov.recipes;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResipeList extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {


    private List<String> recipesListArray = new ArrayList<>();
    private RecipesList recipesList;
    MyRecyclerViewAdapter adapter;
    private String url;
    private int Page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resipe_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        url = getIntent().getExtras().getString("url");
        Page = getIntent().getExtras().getInt("page");

        final RecyclerView ricipesView = findViewById(R.id.recipes_list);
        final ProgressBar ring = findViewById(R.id.loading_ring);

        if(recipesList == null) {
            recipesListArray.add("");
        }

        // set up the RecyclerView
        ricipesView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, recipesListArray);
        adapter.setClickListener(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ricipesView.getContext(),
                DividerItemDecoration.VERTICAL);
        ricipesView.addItemDecoration(dividerItemDecoration);
        ricipesView.addItemDecoration(dividerItemDecoration);
        ricipesView.setAdapter(adapter);

        AsyncCall();

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }


    protected void AsyncCall() {


        findViewById(R.id.loading_ring).setVisibility(View.VISIBLE);
        findViewById(R.id.recipes_list).setVisibility(View.GONE);

        findViewById(R.id.next_btn).setVisibility(View.GONE);
        findViewById(R.id.previous_btn).setVisibility(View.GONE);

        final MyTask mt;
        mt = new MyTask();
        mt.setPage(Page);
        mt.setUrl(url);
        mt.setAdapter(adapter);
        mt.execute();

        final RecyclerView ricipesView = findViewById(R.id.recipes_list);
        final ProgressBar ring = findViewById(R.id.loading_ring);


        mt.OnListener(new MyTask.OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        ring.setVisibility(View.GONE);

                        Log.v("RECIPE",  "ZA WARUDO");
                        recipesList = mt.rList;
                        recipesListArray.clear();
                        recipesListArray.addAll(Arrays.asList(recipesList.getRecipe()));
                        //recipesListArray.add(0, "Haha");
                        Log.v("RECIPE",  Integer.toString(recipesListArray.size()));
                        adapter.notifyItemRangeChanged(0, recipesListArray.size());
                        ricipesView.setVisibility(View.VISIBLE);
                        if(recipesList != null) {
                            findViewById(R.id.next_btn).setVisibility(View.VISIBLE);
                        }

                        if(Page!=1) {
                            findViewById(R.id.previous_btn).setVisibility(View.VISIBLE);
                        }
                    }
                }, 1000);

            }
        });

    }
    public void onNextClick(View view) {
        Page++;
        AsyncCall();
    }

    public void onPreviousClick(View view) {
        Page--;
        AsyncCall();
    }

}
