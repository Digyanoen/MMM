package mmm.jlnf.fetedelascience;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import mmm.jlnf.fetedelascience.Database.DBManager;
import mmm.jlnf.fetedelascience.Pojos.EventPojo;

/**
 * Created by nicolas on 30/01/18.
 */

public class SearchAsyncHandler extends AsyncTask<String, Integer, List<EventPojo>> {

    private RecyclerViewAdapter recycler;
    private ProgressBar progressBar;
    private DBManager dbManager = DBManager.getInstance();
    private int k =0;
    private Listener listener;

    public interface Listener{
        void onFinished(List list);
    }

    public SearchAsyncHandler(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected List<EventPojo> doInBackground(String... strings) {
        return dbManager.getPojosByCity(strings[0]);

    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<EventPojo> result){
        super.onPostExecute(result);
        progressBar.setVisibility(View.GONE);
        listener.onFinished(result);



    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
}

