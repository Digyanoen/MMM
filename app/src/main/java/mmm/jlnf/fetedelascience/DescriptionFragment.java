package mmm.jlnf.fetedelascience;

import android.*;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mmm.jlnf.fetedelascience.Pojos.EventPojo;

/**
 * Created by nicolas on 06/02/18.
 */

public class DescriptionFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    @BindView(R.id.titleEvent)
    TextView title;
    @BindView(R.id.descriptionEvent)
    TextView desc;
    @BindView(R.id.villeEvent)
    TextView ville;
    @BindView(R.id.dateEvent)
    TextView date;

    private EventPojo eventPojo;
    private NotationRecyclerFragment notationRecyclerFragment;

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

        return view;
    }


    public void update(EventPojo currentPojo) {
        title.setText(currentPojo.getTitre_fr());
        desc.setText(currentPojo.getDescription_fr());
        ville.setText(currentPojo.getVille());
        date.setText(currentPojo.getDates().replace(';', '\n'));
        this.eventPojo = currentPojo;
        notationRecyclerFragment.getNotationRecyclerAdapter().getCommentForEvent(currentPojo.getIdentifiant());

    }

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
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.WRITE_CALENDAR}, 0);
    }
}

    @OnClick(R.id.notate)
    public void notate(){
        Intent i = new Intent(getActivity(), NotationActivity.class);
        i.putExtra("identifiant", eventPojo.getIdentifiant());
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {

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

            }else {
                Toast.makeText(this.getActivity(), "Vous avez refusé l'autorisation", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
