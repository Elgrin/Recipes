package zharov.recipes;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavsList extends AppCompatActivity  implements MyRecyclerViewAdapter.ItemClickListener {



    private List<String> recipesListArray = new ArrayList<>();
    private XmlSavedReader recipesList;
    MyRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final RecyclerView ricipesView = findViewById(R.id.recipes_list);
        final ProgressBar ring = findViewById(R.id.loading_ring);

        if(recipesList == null) {
            recipesListArray.add("Loading...");
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
        Intent intent = new Intent(FavsList.this, RecipeInfo.class);
        intent.putExtra("recipe", recipesList.getRecipe()[position]);
        intent.putExtra("link", recipesList.getLink()[position]);
        intent.putExtra("inds", recipesList.getInds()[position]);
        intent.putExtra("visibility", false);
        startActivity(intent);
    }

    protected void AsyncCall() {


        final String filePath = "Save_massive.xml";
        boolean exist = false;

        //FileInputStream fin;
        //getActivity().deleteFile(filePath);
        //Button Save = (Button) view.findViewById(R.id.button_save);

        String massive[] = getApplicationContext().fileList();
        for (String s:massive) {
            if(s.equals(filePath)) {
                exist = true;
                break;
            }
        }

        if(exist) {
            findViewById(R.id.loading_ring).setVisibility(View.VISIBLE);
            findViewById(R.id.recipes_list).setVisibility(View.GONE);
            findViewById(R.id.text_error).setVisibility(View.GONE);

            final MyTaskLoad mt;
            mt = new MyTaskLoad();

            mt.setAdapter(adapter);
            mt.setContext(getApplicationContext());
            mt.execute();

            final RecyclerView ricipesView = findViewById(R.id.recipes_list);
            final ProgressBar ring = findViewById(R.id.loading_ring);

            mt.OnListener(new MyTaskLoad.OnTaskCompleted() {
                @Override
                public void onTaskCompleted() {


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            ring.setVisibility(View.GONE);

                            Log.v("RECIPE", "ZA WARUDO");
                            recipesList = mt.xmlSavedReader;
                            recipesListArray.clear();
                            recipesListArray.addAll(Arrays.asList(recipesList.getRecipe()));
                            //recipesListArray.add(0, "Haha");
                            Log.v("RECIPE", Integer.toString(recipesListArray.size()));
                            adapter.notifyItemRangeChanged(0, recipesListArray.size());
                            ricipesView.setVisibility(View.VISIBLE);
                            Log.v("RECIPE", Integer.toString(recipesList.getInds().length));

                        }
                    }, 1000);

                }
            });
        }
        else  {
            findViewById(R.id.loading_ring).setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "There is favourite recipes! You can add it on recipe info page", Toast.LENGTH_SHORT).show();
        }

    }

}
