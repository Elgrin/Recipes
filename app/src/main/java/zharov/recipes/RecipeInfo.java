package zharov.recipes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class RecipeInfo extends AppCompatActivity {


    private String Title;
    private String Link;
    private String Inds;
    private static final String TAG = "RecipeInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState != null) {
            Title = savedInstanceState.getString("title");
            Link = savedInstanceState.getString("link");
            Inds = savedInstanceState.getString("inds");
        }
        else {
            Title = getIntent().getExtras().getString("recipe");
            Link = getIntent().getExtras().getString("link");
            Inds = getIntent().getExtras().getString("inds");

            if(!getIntent().getExtras().getBoolean("visibility")) {
                findViewById(R.id.save_btn).setVisibility(View.GONE);
            }
        }

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

    public void onSave(View view) {

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

        if(!exist) {
            //.setText("Net");
            FileOutputStream fos;

            try {
                String newXmlFileName = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><save all=\"0\"></save>";
                fos = getApplicationContext().
                        openFileOutput(filePath, MODE_APPEND);
                fos.write(newXmlFileName.getBytes());
                fos.close();
            }
            catch (Exception e) {
                Log.v("Error while saving", "Error");
            }

        }

        try {
            InputStream is = getApplicationContext().openFileInput(filePath);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            Node nNode = doc.getFirstChild();
            String allString = ((Element) nNode).getAttribute("all");
            int allInt = Integer.parseInt(allString);
            //Save.setText(allString);
            ((Element) nNode).setAttribute("all", Integer.toString(allInt+1));

            Element NameElement=doc.createElement("unit");
            //NameElementTitle.appendChild(doc.createTextNode("true")); //Внутренний текст
            NameElement.setAttribute("id", Integer.toString(allInt));
            Log.v(TAG, "0");

            Element NameElementTitle = doc.createElement("title");

            NameElementTitle.appendChild(doc.createTextNode(Title));

            Log.v(TAG, "1");
            Element NameElementLink= doc.createElement("link");
            NameElementLink.appendChild(doc.createTextNode(Link));
            Log.v(TAG, "2");
            Element NameElementInds = doc.createElement("inds");
            NameElementInds.appendChild(doc.createTextNode(Inds));
            Log.v(TAG, "3");


            NameElement.appendChild(NameElementTitle);
            NameElement.appendChild(NameElementLink);
            NameElement.appendChild(NameElementInds);
            nNode.appendChild(NameElement);

            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(getApplicationContext().getFilesDir().getAbsolutePath()+"/" + filePath));
            transformer.transform(source, result);
            is.close();
            Log.v("Info" + "A", "Done");
        }
        catch (Exception e) {
            Log.v("Info", "Error writing");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("title", Title);
        savedInstanceState.putString("link", Link);
        savedInstanceState.putString("inds", Inds);
    }

}
