package mmm.jlnf.fetedelascience;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mmm.jlnf.fetedelascience.Pojos.NotationPojo;

/**
 * Created by nicolas on 12/02/18.
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
        Log.e("tag", "coucou");
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @OnClick(R.id.evaluate)
    public void onTouch(){
        String i = getIntent().getStringExtra("identifiant");
        firebaseDatabase.child(i).child(name.getText() +" "+firstname.getText()).child("comment").setValue(comment.getText().toString());
        firebaseDatabase.child(i).child(name.getText() +" "+firstname.getText()).child("stars").setValue(ratingBar.getRating());
        firebaseDatabase.push();

    }
}
