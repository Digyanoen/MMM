package mmm.jlnf.fetedelascience;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nicolas on 21/01/18.
 */

public class EventView extends Activity{

    @BindView(R.id.event) RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<EventPojo> eventsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);

        Intent i = getIntent();
        /*Log.e("size", String.valueOf(recyclerViewAdapter.getItemCount()));
        Log.e("taille", String.valueOf(eventsList.size()));
        init();*/
        recyclerViewAdapter = new RecyclerViewAdapter(eventsList);
        recyclerView.setAdapter(recyclerViewAdapter);
        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.getCityDescription(i.getStringExtra("ville"), eventsList, recyclerViewAdapter);



    }

    private void init() {
        EventPojo eventPojo = new EventPojo();
        eventPojo.setDescription("desc");
        eventPojo.setTitle("title");
        for (int i=0; i<5; i++){
            eventsList.add(eventPojo);
        }
    }
}