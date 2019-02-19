package zharov.recipes;


import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

class MyTaskLoad extends AsyncTask<Void, Void, Void> {

    protected MyRecyclerViewAdapter adapter;
    private Context context;
    XmlSavedReader xmlSavedReader;

    public interface OnTaskCompleted{
        void onTaskCompleted();
        //void onTaskExecute();
    }

    private OnTaskCompleted listener;

    public void OnListener(OnTaskCompleted listener){
        this.listener=listener;
    }


    public void setAdapter(MyRecyclerViewAdapter adapter) {this.adapter = adapter;}
    public void setContext(Context context) {this.context = context;}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            xmlSavedReader = new XmlSavedReader(context);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        listener.onTaskCompleted();
        //adapter.notifyItemRangeChanged(0, rList.getRecipe().length);
    }

    @Override
    protected void onProgressUpdate(Void... results) {
        super.onProgressUpdate(results);
        //listener.onTaskExecute();
    }
}
