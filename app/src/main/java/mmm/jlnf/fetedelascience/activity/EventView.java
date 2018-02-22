package mmm.jlnf.fetedelascience.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.R;
import mmm.jlnf.fetedelascience.SearchAsyncHandler;
import mmm.jlnf.fetedelascience.database.DBManager;
import mmm.jlnf.fetedelascience.fragments.NotationRecyclerFragment;
import mmm.jlnf.fetedelascience.fragments.RecyclerFragment;

/**
 * Created by nicolas on 21/01/18.
 */

public class EventView extends AppCompatActivity {

    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);
        ButterKnife.bind(this);
        DBManager dbmanager = DBManager.getInstance();
        Intent i = getIntent();
        setSupportActionBar(toolbar);

        NotationRecyclerFragment notationRecyclerFragment = new NotationRecyclerFragment();
        RecyclerFragment recycler = new RecyclerFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.eventlarge, recycler );
        fragmentTransaction.commit();

        SearchAsyncHandler asyncHandler = new SearchAsyncHandler(progressBar);

        asyncHandler.setListener(list -> {
            recycler.updateEventsList(list);
            recycler.getAdapter().notifyDataSetChanged();
        });

        asyncHandler.execute(i.getStringExtra("type"), i.getStringExtra("data"));

    }




}
