package mmm.jlnf.fetedelascience;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import mmm.jlnf.fetedelascience.database.DBManager;
import mmm.jlnf.fetedelascience.pojos.EventPojo;

import static android.content.ContentValues.TAG;

/**
 * Created by nicolas on 30/01/18.
 * Recherche asynchrone dans la base de données
 */

public class SearchAsyncHandler extends AsyncTask<String, Integer, List<EventPojo>> {

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
        List<EventPojo> pojosByCriteria = dbManager.getPojosByCriteria(strings[0], strings[1]);
        return pojosByCriteria;

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

