package zharov.recipes;

import android.util.Log;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RecipesList {

    private String[] Recipe;
    private String[] Link;
    private String[] Indigrients;
    private int Page;


    RecipesList(String urlString, int Page) throws java.net.MalformedURLException,
            javax.xml.parsers.ParserConfigurationException,
            java.io.IOException,
            org.xml.sax.SAXException{



            int Iterator = 0;

            String url_s = "http://" + urlString + Page;

            //url_s = UrlEscapers.urlFragmentEscaper().escape(url_s);

            URL url = new URL(url_s);

            Log.v("URL_url", url.toString());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("recipe");

            //Log.v("URL", Integer.toString(nodeList.getLength()));

            //Node node = Properties.item(0);
            //Element el = (Element) node;

            Indigrients = new String[nodeList.getLength()];
            Recipe = new String[nodeList.getLength()];
            Link = new String[nodeList.getLength()];

            for(int i = 0; i < nodeList.getLength(); i++) {
                //Log.v("URLI", Integer.toString(i));
                Node nodeRecipe = nodeList.item(i);

                if (nodeRecipe.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) nodeRecipe;

                    NodeList nameList = element.getElementsByTagName("title");
                    Element nameElement = (Element) nameList.item(0);
                    nameList = nameElement.getChildNodes();
                    Recipe[Iterator] = nameList.item(0).getNodeValue().trim();
                    Log.v("URL", "Recipe " + Recipe[Iterator]);

                    NodeList nameList2 = element.getElementsByTagName("href");
                    Element nameElement2 = (Element) nameList2.item(0);
                    nameList2 = nameElement2.getChildNodes();
                    Link[Iterator] = nameList2.item(0).getNodeValue().trim();
                    Log.v("URL", "Link " + Link[Iterator]);


                    NodeList nameList3 = element.getElementsByTagName("ingredients");
                    Element nameElement3 = (Element) nameList3.item(0);
                    nameList3 = nameElement3.getChildNodes();
                    Indigrients[Iterator] = nameList3.item(0).getNodeValue().trim();
                    Log.v("URL", "Recipe " + Indigrients[Iterator]);


                    /*
                    NodeList nodeElements = nodeRecipe.getChildNodes();

                    //Log.v("URL", Integer.toString(nodeElements.getLength()));

                    for(int j = 0; j < nodeElements.getLength(); j++) {

                        //Log.v("URLout", Integer.toString(j));

                        Node nodeElement = nodeElements.item(j);

                        if(nodeElement.getNodeType() == Node.ELEMENT_NODE) {
                            //Log.v("URL2", Integer.toString(j));
                            String name = nodeElement.getNodeName();

                            //Log.v("URL2", name);

                            if(name.equals("title")) {
                                Recipe[Iterator] = nodeElement.getTextContent();
                                Log.v("URL", "Recipe " + Recipe[Iterator]);
                            } else if(name.equals("href")) {
                                Link[Iterator] = nodeElement.getTextContent();
                                Log.v("URL", "Link " + Link[Iterator]);

                            } else if(name.equals("ingredients")) {
                                Log.v("URLIndig", "YES");
                                Indigrients[Iterator] = nodeElement.getTextContent();
                                Log.v("URL", "Indigrients " + Indigrients[Iterator]);
                            }

                        }

                    }
                    //Iterator++;
                    */

                }
                Iterator++;
            }



    }


    public void setRecipe(String[] Recipe) {this.Recipe = Recipe;}
    public void setLink(String[] Link) {this.Link = Link;}
    public void setIndigrients(String[] Indigrients) {this.Indigrients = Indigrients;}

    public String[] getRecipe() {return Recipe;}
    public String[] getLink() {return Link;}
    public String[] getIndigrients() {return Indigrients;}
}
