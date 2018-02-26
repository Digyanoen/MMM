package mmm.jlnf.fetedelascience.fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mmm.jlnf.fetedelascience.activity.MapsActivity;
import mmm.jlnf.fetedelascience.activity.NotationActivity;
import mmm.jlnf.fetedelascience.activity.SearchActivity;
import mmm.jlnf.fetedelascience.pojos.EventPojo;
import mmm.jlnf.fetedelascience.R;


/**
 * Created by nicolas on 06/02/18.
 * Fragment de description d'un evenement
 */

public class DescriptionFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback{

    @BindView(R.id.titleEvent)
    TextView title;
    @BindView(R.id.descriptionEvent)
    TextView desc;
    @BindView(R.id.villeEvent)
    TextView ville;
    @BindView(R.id.dateEvent)
    TextView date;
    @BindView(R.id.remplissage)
    FrameLayout frameLayout;

    private Spinner spinner;
    private TextView textView;

    private EventPojo eventPojo;
    private NotationRecyclerFragment notationRecyclerFragment;
    private DatabaseReference databaseReference;
    private List<Integer> remplissage;
    private ItineraireListener itineraireListener;

    public DescriptionFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.eventlayoutdesc, container, false);
        ButterKnife.bind(this, view);

        FragmentManager fragmentManager = getFragmentManager();
        notationRecyclerFragment = new NotationRecyclerFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.avis, notationRecyclerFragment);
        fragmentTransaction.commit();

        textView = new TextView(getActivity());
        spinner = new Spinner(getActivity());
        setHasOptionsMenu(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        setItem();

        // Si l'utilisateur est l'oganisateur, on rajoute un listener sur le spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int remplissage = (int) parent.getItemAtPosition(position);
                databaseReference.child(eventPojo.getIdentifiant()).child("Remplissage").setValue(remplissage);
                databaseReference.push();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


    public void update() {
        title.setText(eventPojo.getTitre_fr());
        desc.setText(eventPojo.getDescription_fr());
        ville.setText(eventPojo.getVille());
        date.setText(eventPojo.getDates().replace(';', ' '));
        notationRecyclerFragment.getNotationRecyclerAdapter().getCommentForEvent(eventPojo.getIdentifiant());

    }


    /**
     * Ajout au calendrier
     * Vérification si la permission d'ajout au calendrier est accordée
     */
    @OnClick(R.id.addToCalendar)
    public void addToCalendar(){
        if(ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.WRITE_CALENDAR)==PackageManager.PERMISSION_GRANTED)

        {
            ContentValues values = new ContentValues();
            Calendar beginTime = Calendar.getInstance();
            String[] dates = eventPojo.getDates().split(";");
            for( String date : dates) {
                String[] splitDate = date.split("-");
                beginTime.set(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1])-1, Integer.parseInt(splitDate[2]));
                Calendar endTime = Calendar.getInstance();
                long start = beginTime.getTimeInMillis();
                endTime.set(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1])-1, Integer.parseInt(splitDate[2])+1);
                long end = endTime.getTimeInMillis();
                Log.e("tag", "begin "+start+" end "+end);
                values.put(CalendarContract.Events.DTSTART, start);
                values.put(CalendarContract.Events.DTEND, end);
                values.put(CalendarContract.Events.TITLE, eventPojo.getTitre_fr());
                values.put(CalendarContract.Events.DESCRIPTION, eventPojo.getDescription_fr());
                values.put(CalendarContract.Events.ALL_DAY, 1);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getDisplayName());
                values.put(CalendarContract.Events.CALENDAR_ID, 3);
                Uri uri = this.getActivity().getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
            }
            Toast.makeText(this.getActivity(), "Evénement ajouté", Toast.LENGTH_SHORT).show();
        } else

        {
            requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR}, 0);
        }
    }

    @OnClick(R.id.notate)
    public void notate(){
        Intent i = new Intent(getActivity(), NotationActivity.class);
        i.putExtra("identifiant", eventPojo.getIdentifiant());
        startActivity(i);
    }

    /**
     * Résultat de la demande de permission
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("request code", String.valueOf(requestCode));
        if (requestCode == 0 && (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.WRITE_CALENDAR)==PackageManager.PERMISSION_GRANTED)) {
            Log.e("tata", "toto");
            addToCalendar();
        }else {
            Toast.makeText(this.getActivity(), "Vous avez refusé l'autorisation", Toast.LENGTH_SHORT).show();
        }
    }

    public void setEventPojo(EventPojo eventPojo) {
        this.eventPojo = eventPojo;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.orga:
                SearchActivity.isOrganisteur = !SearchActivity.isOrganisteur;
                if(SearchActivity.isOrganisteur){
                    frameLayout.removeView(textView);
                    Toast.makeText(getActivity(), "Vous êtes organisateur", Toast.LENGTH_LONG).show();

                }
                else{
                    frameLayout.removeView(spinner);
                    Toast.makeText(getActivity(), "Vous n'êtes plus l'organisateur", Toast.LENGTH_LONG).show();
                }
                setItem();
                return true;
            case R.id.mapActivity:
                Intent i = getActivity().getIntent();
                i.setClass(getActivity(), MapsActivity.class);
                i.putExtra("latitude", eventPojo.getLat());
                i.putExtra("longitude", eventPojo.getLng());
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void setItem(){
        databaseReference.child(eventPojo.getIdentifiant()).child("Remplissage").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = (Long) dataSnapshot.getValue();
                if(SearchActivity.isOrganisteur) {
                    frameLayout.addView(spinner);
                    remplissage = new ArrayList<Integer>();
                    for (int i = 0; i <= 10; i++) remplissage.add(i * 10);

                    // Creating adapter for spinner
                    ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, remplissage);

                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    spinner.setAdapter(dataAdapter);
                    if (value != null) {
                        spinner.setSelection(remplissage.indexOf(value.intValue()));

                    }
                }
                else {
                    frameLayout.addView(textView);
                    if(value != null){
                        textView.setText(value.toString());
                    }else {
                        textView.setText("0");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.addToList)
    public void addToList(){
        itineraireListener.onAddItineraire(eventPojo);
    }

    public void setItineraireListener(ItineraireListener i){
        Log.e("listener", i.toString());
        this.itineraireListener = i;

    }

    public interface ItineraireListener{
        void onAddItineraire(EventPojo e);
    }

}
