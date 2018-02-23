package mmm.jlnf.fetedelascience.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mmm.jlnf.fetedelascience.R;
import mmm.jlnf.fetedelascience.database.DatabaseHandler;
import mmm.jlnf.fetedelascience.database.IDatabaseHandler;

/**
 * Created by nicolas on 21/01/18.
 */

public class SearchActivity extends AppCompatActivity{

    @BindView(R.id.data) TextView textView;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.my_toolbar) Toolbar toolbar;
    private IDatabaseHandler databaseHandler;


    @Override
    public void onCreate(Bundle onSavedInBundle) {
        super.onCreate(onSavedInBundle);
        databaseHandler = new DatabaseHandler(this);
        setContentView(R.layout.search_activity_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

    }


    @OnClick(R.id.search)
    public void onClick(){
        Intent intent = new Intent(getApplicationContext(), EventView.class);
        String txt = textView.getText().toString();
        RadioButton selected = findViewById(radioGroup.getCheckedRadioButtonId());
        String type = selected.getText().toString();
        intent.putExtra("type", type);
        intent.putExtra("data", txt);
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = getIntent();
        switch (item.getItemId()) {
            case R.id.orga:
                MapsActivity.isOrganisteur = !MapsActivity.isOrganisteur;
                return true;
            case R.id.mapActivity:
                i.setClass(this, MapsActivity.class);
                startActivity(i);
                return true;
            case R.id.rechercheActivity:
                i.setClass(this, SearchActivity.class);
                startActivity(i);
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


}
