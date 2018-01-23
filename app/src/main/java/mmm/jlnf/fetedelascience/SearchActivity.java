package mmm.jlnf.fetedelascience;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nicolas on 21/01/18.
 */

public class SearchActivity extends Activity{

    @BindView(R.id.Ville)
    TextView textView;

    @Override
    public void onCreate(Bundle onSavedInBundle) {
        super.onCreate(onSavedInBundle);
        setContentView(R.layout.searchacitivity_layout);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.search)
    public void onClick(){
        Intent intent = new Intent(getApplicationContext(), EventView.class);
        String txt = textView.getText().toString();
        intent.putExtra("ville", txt);
        startActivity(intent);
    }
}
