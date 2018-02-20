package mmm.jlnf.fetedelascience.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mmm.jlnf.fetedelascience.R;

/**
 * Created by nicolas on 12/02/18.
 * Activité permettant la notation d'un événement
 */

public class NotationActivity extends Activity {

    @BindView(R.id.ratingBar)RatingBar ratingBar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.firstname) TextView firstname;
    @BindView(R.id.comment) EditText comment;
    DatabaseReference firebaseDatabase;

    @Override
    public void onCreate(Bundle onSavedInBundle) {
        super.onCreate(onSavedInBundle);
        setContentView(R.layout.notation_layout);
        ButterKnife.bind(this);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @OnClick(R.id.evaluate)
    public void onTouch(){
        String i = getIntent().getStringExtra("identifiant");
        firebaseDatabase.child(i).child(name.getText() +" "+firstname.getText()).child("comment").setValue(comment.getText().toString());
        firebaseDatabase.child(i).child(name.getText() +" "+firstname.getText()).child("stars").setValue(String.valueOf(ratingBar.getRating()));
        firebaseDatabase.push();
        Toast.makeText(getApplicationContext(), "Événement noté", Toast.LENGTH_SHORT).show();
        finish();

    }
}
