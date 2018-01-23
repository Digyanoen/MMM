package mmm.jlnf.fetedelascience;

import android.os.Debug;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 20/01/18.
 */

public class DatabaseHandler {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    public void readValue(){
        Log.d("TAG", databaseReference.getKey());
    }

    public void getEventDescription(){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = (String) dataSnapshot.child("5553").child("fields").child("ville").getValue();
                Log.e("Taaaaaaaag", s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public List<EventPojo> getCityDescription(String city, List<EventPojo> eventPojos, RecyclerViewAdapter adapter){
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
//                       eventPojo.setTitle("test1");
//                       eventPojo.setDescription("test2");
                       eventPojos.add(eventPojo);
                       adapter.notifyItemInserted(eventPojos.size() -1);

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return eventPojos;
    }

}
