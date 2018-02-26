package mmm.jlnf.fetedelascience.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.R;
import mmm.jlnf.fetedelascience.SearchAsyncHandler;
import mmm.jlnf.fetedelascience.database.DBManager;
import mmm.jlnf.fetedelascience.fragments.DescriptionFragment;
import mmm.jlnf.fetedelascience.fragments.ItineraireFragment;
import mmm.jlnf.fetedelascience.fragments.RecyclerFragment;
import mmm.jlnf.fetedelascience.pojos.EventPojo;

/**
 * Created by nicolas on 21/01/18.
 */

public class EventView extends AppCompatActivity implements DescriptionFragment.ItineraireListener, RecyclerFragment.EventRecyclerListener {

    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.my_toolbar)
    Toolbar toolbar;
    private RecyclerFragment recycler;
    private DescriptionFragment descriptionFragment;

    private ItineraireFragment itineraireFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        itineraireFragment = new ItineraireFragment();
        recycler = new RecyclerFragment();
        recycler.setOnEventRecyclerListener(this);
        descriptionFragment = new DescriptionFragment();
        descriptionFragment.setItineraireListener(this);
    }

    @Override
    protected void onStart() {
        DBManager dbmanager = DBManager.getInstance();

        Intent i = getIntent();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int eventID = i.getIntExtra("EventID", -1);

        if(eventID != -1){
            EventPojo pojo = dbmanager.getPojoByID(eventID);
            if(pojo != null){
                descriptionFragment.setEventPojo(pojo);
                fragmentTransaction.add(R.id.eventlarge,descriptionFragment).commit();
                fragmentManager.executePendingTransactions();
                descriptionFragment.update();
            }else{
                Toast.makeText(getApplicationContext(),"Cet événement n'existe pas !",Toast.LENGTH_LONG).show();
                eventID = -1;
            }
        }

        if(eventID == -1){
            fragmentTransaction.add(R.id.eventlarge, recycler);

            SearchAsyncHandler asyncHandler = new SearchAsyncHandler(progressBar);

            asyncHandler.setListener(list -> {
                recycler.updateEventsList(list);
                recycler.getAdapter().notifyDataSetChanged();
            });
            fragmentTransaction.commit();

            asyncHandler.execute(i.getStringExtra("type"), i.getStringExtra("data"));
        }
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = getIntent();
        switch (item.getItemId()) {
            case R.id.rechercheActivity:
                i.setClass(this, SearchActivity.class);
                startActivity(i);
                return true;
            case R.id.itineraireFragment:
                FragmentManager fragmentManager= getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.eventlarge, itineraireFragment)
                        .addToBackStack(null)
                        .commit();
                fragmentManager.executePendingTransactions();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }


    @Override
    public void onAddItineraire(EventPojo e) {
        itineraireFragment.addToList(e);
        Toast.makeText(this, "Evénement ajouté à l'itinéraire", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEventRecyclerListener(EventPojo pojo) {
        FragmentManager fragmentManager = getFragmentManager();
        descriptionFragment.setEventPojo(pojo);
        fragmentManager.beginTransaction()
                .replace(R.id.eventlarge, descriptionFragment)
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();
        descriptionFragment.update();
    }

}
