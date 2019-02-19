package zharov.recipes;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

class MyTask extends AsyncTask<Void, Void, Void> {

    private String url;
    private int Page;
    protected RecipesList rList;


    public interface OnTaskCompleted{
        void onTaskCompleted();
    }

    private OnTaskCompleted listener;

    public void OnListener(OnTaskCompleted listener){
        this.listener=listener;
    }


    public void setUrl(String url) {this.url = url;}
    public void setPage(int Page) {this.Page = Page;}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            rList = new RecipesList(url, Page);
            listener.onTaskCompleted();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

    }
}
