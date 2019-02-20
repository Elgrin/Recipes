package zharov.recipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Web extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String link = getIntent().getExtras().getString("url");

        WebView webView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(link);

    }

}
