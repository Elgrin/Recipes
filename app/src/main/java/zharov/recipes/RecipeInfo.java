package zharov.recipes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class RecipeInfo extends AppCompatActivity {


    protected String Title;
    private String Link;
    private String Inds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Title = getIntent().getExtras().getString("recipe");
        Link = getIntent().getExtras().getString("link");
        Inds = getIntent().getExtras().getString("inds");

        TextView txt = (TextView)findViewById(R.id.title_recipe);
        txt.setText("Recipe: " +  Title);
        TextView link = (TextView)findViewById(R.id.link_recipe);
        link.setText("Link to recipe: " + Link);
        TextView inds = (TextView)findViewById(R.id.inds_recipe);
        inds.setText("Ingredients: " + Inds);

        link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(Link));
                    startActivity(i);
                }
                catch (Exception e) {
                    Intent intent = new Intent(RecipeInfo.this, Web.class);
                    intent.putExtra("url", Link);
                    startActivity(intent);
                }


            }
        });
    }

}
