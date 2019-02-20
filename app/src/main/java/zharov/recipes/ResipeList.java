package zharov.recipes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResipeList extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {


    private List<String> recipesListArray = new ArrayList<>();
    private RecipesList recipesList;
    private MyRecyclerViewAdapter adapter;
    private String url;
    private int Page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resipe_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState!=null) {
            url = savedInstanceState.getString("url");
            Page = savedInstanceState.getInt("page");
        }
        else {
            url = getIntent().getExtras().getString("url");
            Page = getIntent().getExtras().getInt("page");
        }

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
        ricipesView.setAdapter(adapter);

        AsyncCall();

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(ResipeList.this, RecipeInfo.class);
        intent.putExtra("recipe", recipesList.getRecipe()[position]);
        intent.putExtra("link", recipesList.getLink()[position]);
        intent.putExtra("inds", recipesList.getIndigrients()[position]);
        intent.putExtra("visibility", true);
        startActivity(intent);
    }


    private void AsyncCall() {


        findViewById(R.id.loading_ring).setVisibility(View.VISIBLE);
        findViewById(R.id.recipes_list).setVisibility(View.GONE);
        findViewById(R.id.text_error).setVisibility(View.GONE);

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
                        recipesList = mt.rList;

                        if(recipesList == null) {
                            Toast.makeText(getApplicationContext(), "Nothing found!", Toast.LENGTH_SHORT).show();


                            findViewById(R.id.next_btn).setVisibility(View.GONE);
                            if(Page!=1) {
                                findViewById(R.id.previous_btn).setVisibility(View.VISIBLE);
                                findViewById(R.id.recipes_list).setVisibility(View.VISIBLE);
                            }
                            else {
                                findViewById(R.id.recipes_list).setVisibility(View.GONE);
                            }
                            Page--;
                            return;
                        }

                        recipesListArray.clear();
                        recipesListArray.addAll(Arrays.asList(recipesList.getRecipe()));
                        //recipesListArray.add(0, "Haha");
                        Log.v("RECIPE",  Integer.toString(recipesListArray.size()));
                        adapter.notifyItemRangeChanged(0, recipesListArray.size());

                        ricipesView.setAdapter(adapter);

                        ricipesView.setVisibility(View.VISIBLE);

                        if(recipesList.getIndigrients().length != 0) {
                            findViewById(R.id.next_btn).setVisibility(View.VISIBLE);
                        }
                        else {
                            findViewById(R.id.next_btn).setVisibility(View.GONE);
                            findViewById(R.id.text_error).setVisibility(View.VISIBLE);
                        }

                        if(Page!=1) {
                            findViewById(R.id.previous_btn).setVisibility(View.VISIBLE);
                        }
                        Log.v("RECIPE",  Integer.toString(recipesList.getIndigrients().length));
                    }
                }, 100);

            }
        });

    }
    public void onNextClick(View view) {
        onStep(++Page);
    }

    public void onPreviousClick(View view) {
        onStep(--Page);
    }


    private void onStep(int page) {
        if(isOnline()) {
            Page  = page;
            AsyncCall();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "There is no internet connection! Please check it or try later.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("url", url);
        savedInstanceState.putInt("page", Page);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
