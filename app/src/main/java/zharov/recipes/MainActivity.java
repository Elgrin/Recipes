package zharov.recipes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            Intent intent = new Intent(MainActivity.this, ResipeList.class);
            startActivity(intent);
        } else if (id == R.id.nav_favs) {
            Intent intent = new Intent(MainActivity.this, FavsList.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void SearchRecipes(View view) throws java.net.MalformedURLException,
            javax.xml.parsers.ParserConfigurationException,
            java.io.IOException,
            org.xml.sax.SAXException{


        if(view != null) {

            if(isOnline()) {
                //http://www.recipepuppy.com/api/?i=&q=&p=1&format=xml
                String url = getResources().getString(R.string.link) + "i=";

                TextView textViewInd = findViewById(R.id.ingredients_edit);

                if (textViewInd != null) {
                    url += textViewInd.getText() + "&q=";

                    TextView textViewDish = findViewById(R.id.dish_name_edit);

                    if (textViewDish != null) {
                        url += textViewDish.getText() + "&"
                                + getResources().getString(R.string.link_type) + "&p=";

                        Log.v("URL", url + "1");

                        Intent intent = new Intent(MainActivity.this, ResipeList.class);
                        intent.putExtra("url", url);
                        intent.putExtra("page", 1);
                        startActivity(intent);
                    }
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "There is no Internet connection!", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
