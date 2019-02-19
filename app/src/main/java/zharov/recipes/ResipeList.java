package zharov.recipes;

import android.os.Bundle;
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
import android.widget.Toast;

import java.util.ArrayList;
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

        RecyclerView ricipesView = findViewById(R.id.recipes_list);

        // data to populate the RecyclerView with
        //ArrayList<String> recipesList = new ArrayList<>();

        /*
        recipesListArray.add("Horse");
        recipesListArray.add("Cow");
        recipesListArray.add("Camel");
        recipesListArray.add("Sheep");
        recipesListArray.add("Goat");*/

        final MyTask mt;
        mt = new MyTask();
        mt.setPage(Page);
        mt.setUrl(url);
        mt.execute();

        mt.OnListener(new MyTask.OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                Log.v("RECIPE",  "ZA WARUDO");
                recipesList = mt.rList;
                recipesListArray.clear();
                for(int i =0; i < recipesList.getRecipe().length; i++) {
                    recipesListArray.add(recipesList.getRecipe()[i]);
                    Log.v("RECIPE",  recipesList.getRecipe()[i]);
                    adapter.notifyDataSetChanged();
                }
            }
        });


        // set up the RecyclerView
        ricipesView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, recipesListArray);
        adapter.setClickListener(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ricipesView.getContext(),
                DividerItemDecoration.VERTICAL);
        ricipesView.addItemDecoration(dividerItemDecoration);

        ricipesView.addItemDecoration(dividerItemDecoration);

        ricipesView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

}
