package mmm.jlnf.fetedelascience.Database;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import mmm.jlnf.fetedelascience.Pojos.EventPojo;
import mmm.jlnf.fetedelascience.R;
import mmm.jlnf.fetedelascience.RecyclerViewAdapter;

/**
 * Created by nicolas on 20/01/18.
 */

public class DatabaseHandler implements IDatabaseHandler {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    private SQLDatabaseHelper helper;


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
                       eventPojo.setTitre_fr((String) issue.child("fields").child("titre_fr").getValue());
                       eventPojo.setDescription_fr((String) issue.child("fields").child("description_fr").getValue());
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

    public void initialize(Context context){
        try {

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            InputStream resource = context.getResources().openRawResource(R.raw.data);
            JsonReader jsonReader = new JsonReader(new InputStreamReader(resource));
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(jsonReader).getAsJsonArray();
            DBManager.Init(context);
            DBManager SQLdatabase = DBManager.getInstance();
            for (JsonElement element : jsonArray){

                JsonObject object = element.getAsJsonObject().get("fields").getAsJsonObject();


                EventPojo eventPojo = gson.fromJson(object, EventPojo.class);
                SQLdatabase.createEventPojo(eventPojo);
                Log.e("Tag", "title : "+eventPojo.getTitre_fr());
                Log.e("TAG", "desc : "+eventPojo.getDescription_fr());
            }
            Log.e("tag", "finish");

            resource.close();
            jsonReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
