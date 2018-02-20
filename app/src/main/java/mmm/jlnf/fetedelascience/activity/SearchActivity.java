package mmm.jlnf.fetedelascience.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class SearchActivity extends Activity{

    @BindView(R.id.data) TextView textView;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    private IDatabaseHandler databaseHandler;


    @Override
    public void onCreate(Bundle onSavedInBundle) {
        super.onCreate(onSavedInBundle);
        databaseHandler = new DatabaseHandler(this);
        setContentView(R.layout.searchacitivity_layout);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.search)
    public void onClick(){
        Intent intent = new Intent(getApplicationContext(), EventView.class);
        String txt = textView.getText().toString();
        RadioButton selected =  (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        String type = selected.getText().toString();
        intent.putExtra("type", type);
        intent.putExtra("data", txt);
        startActivity(intent);
    }
}
