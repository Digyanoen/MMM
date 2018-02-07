package mmm.jlnf.fetedelascience;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.Database.DBManager;
import mmm.jlnf.fetedelascience.Database.DatabaseHandler;
import mmm.jlnf.fetedelascience.Pojos.EventPojo;

/**
 * Created by nicolas on 21/01/18.
 */

public class EventView extends Activity{

    @BindView(R.id.event) RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<EventPojo> eventsList = new ArrayList<>();
    @BindView(R.id.progress) ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        DBManager dbmanager = DBManager.getInstance();
        Intent i = getIntent();


        if(findViewById(R.id.eventlarge) != null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.eventlarge, new DescriptionFragment());
            fragmentTransaction.commit();
        }



        recyclerViewAdapter = new RecyclerViewAdapter(eventsList, this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);
        SearchAsyncHandler asyncHandler = new SearchAsyncHandler(progressBar);
        asyncHandler.setListener(new SearchAsyncHandler.Listener() {
            @Override
            public void onFinished(List list) {
                eventsList.addAll(list);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        asyncHandler.execute(i.getStringExtra("type"), i.getStringExtra("data"));





    }

}
