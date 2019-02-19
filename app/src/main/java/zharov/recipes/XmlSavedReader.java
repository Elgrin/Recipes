package zharov.recipes;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

class XmlSavedReader {


    private String[] Recipe;
    private String[] Link;
    private String[] Inds;


    void setRecipe(String[] Recipe) {this.Recipe = Recipe;}
    void setLink(String[] Link) {this.Link = Link;}
    void setInds(String[] Inds) {this.Inds = Inds;}

    String[] getRecipe() {return Recipe;}
    String[] getLink() {return Link;}
    String[] getInds() {return Inds;}

    private static final String TAG = "XmlSavedReader";

    XmlSavedReader(Context context) {
        final String filePath = "Save_massive.xml";
        try {

            InputStream is = context.openFileInput(filePath);
            int Iterator = 0;

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            NodeList nList = doc.getElementsByTagName("unit");

            int allInt = nList.getLength();

            //bundle = new SavedBundle[allInt];

            Inds = new String[allInt];
            Recipe = new String[allInt];
            Link = new String[allInt];


            Log.v(TAG, "Third" + " " + Integer.toString(nList.getLength()));
            for(int i = 0; i < nList.getLength(); i++) {


                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) nNode;

                        NodeList nameList = element.getElementsByTagName("title");
                        Element nameElement = (Element) nameList.item(0);
                        nameList = nameElement.getChildNodes();
                        Recipe[Iterator] = nameList.item(0).getNodeValue().trim();
                        Log.v("URL", "Recipe " + Recipe[Iterator]);

                        NodeList nameList2 = element.getElementsByTagName("link");
                        Element nameElement2 = (Element) nameList2.item(0);
                        nameList2 = nameElement2.getChildNodes();
                        Link[Iterator] = nameList2.item(0).getNodeValue().trim();
                        Log.v("URL", "Link " + Link[Iterator]);


                        NodeList nameList3 = element.getElementsByTagName("inds");
                        Element nameElement3 = (Element) nameList3.item(0);
                        nameList3 = nameElement3.getChildNodes();
                        Inds[Iterator] = nameList3.item(0).getNodeValue().trim();
                        Log.v("URL", "Recipe " + Inds[Iterator]);

                    }

                        /*if(nNode_Second.getNodeType() == Node.ELEMENT_NODE) {

                            if (j == 0) {
                                Recipe[Iterator] = (nNode_Second.getTextContent());
                                Log.v(TAG, "Path " + nNode_Second.getTextContent());
                            }
                            if (j == 1) {
                                Link[Iterator] = (nNode_Second.getTextContent());
                                Log.v(TAG, "Number " + nNode_Second.getTextContent());
                                //continue;
                            }
                            if (j == 2) {
                                Inds[Iterator] = (nNode_Second.getTextContent());
                                Log.v(TAG, "Show " + nNode_Second.getTextContent());
                                //continue;
                            }

                            }*/
                        }
                        Iterator++;
                    }

        }
        catch (Exception e) {
            Log.v(TAG, "Error loading" + " " + e.getMessage() + " " +
                    " " + e.getCause());
        }
    }

}
