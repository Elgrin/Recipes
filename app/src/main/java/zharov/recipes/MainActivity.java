package zharov.recipes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings: {

                Intent intent = new Intent(MainActivity.this, FavsList.class);
                //startActivity(intent);
                startActivityForResult(intent, 0);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onClearEditBoxes();
    }
    private void onClearEditBoxes() {
        ((TextView)findViewById(R.id.dish_name_edit)).setText("");
        ((TextView)findViewById(R.id.ingredients_edit)).setText("");
    }
    private boolean isOnline() {
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

                //String s = textViewInd.getText().toString().trim();

                //URLEncoder.encode(urlString, "utf-8")
                //textViewInd.getText().toString().trim()
                //textViewDish.getText().toString().trim()
                if (textViewInd != null) {
                    url += URLEncoder.encode(textViewInd.getText().toString().trim(), "utf-8") + "&q=";

                    TextView textViewDish = findViewById(R.id.dish_name_edit);

                    if (textViewDish != null) {
                        url += URLEncoder.encode(textViewDish.getText().toString().trim(), "utf-8") + "&"
                                + getResources().getString(R.string.link_type) + "&p=";

                        Log.v("URL", url + "1");

                        Intent intent = new Intent(MainActivity.this, ResipeList.class);
                        intent.putExtra("url", url);
                        intent.putExtra("page", 1);
                        //startActivity(intent);

                        startActivityForResult(intent, 0);
                    }
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "There is no Internet connection!", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
