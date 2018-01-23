package mmm.jlnf.fetedelascience.Database;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import mmm.jlnf.fetedelascience.Pojos.EventPojo;
import mmm.jlnf.fetedelascience.RecyclerViewAdapter;

/**
 * Created by nicolas on 20/01/18.
 */

public class DatabaseHandler implements IDatabaseHandler {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();


    @Override
    public void getCityDescription(String city, List<EventPojo> eventPojos, RecyclerViewAdapter adapter){
    Log.e("test", "coucou");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("test2", "coucou2");
                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                   // Log.e("test3", issue.get)
                   if(issue.child("fields").child("ville").exists()
                           && issue.child("fields").child("ville").getValue().equals(city)) {
                        Log.e("Value", (String) issue.child("fields").child("ville").getValue());
                       Log.e("titre", (String) issue.child("fields").child("titre_fr").getValue());
                       Log.e("desc", (String) issue.child("fields").child("description_fr").getValue());

                       EventPojo eventPojo = new EventPojo();
                       eventPojo.setTitle((String) issue.child("fields").child("titre_fr").getValue());
                       eventPojo.setDescription((String) issue.child("fields").child("description_fr").getValue());
                       eventPojos.add(eventPojo);
                       adapter.notifyItemInserted(eventPojos.size() -1);

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void getEventByCoordinates(LatLng coordinatesMin, LatLng coordinatesMax) {

    }

}
