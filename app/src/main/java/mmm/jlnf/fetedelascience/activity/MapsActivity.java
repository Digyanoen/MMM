package mmm.jlnf.fetedelascience.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import mmm.jlnf.fetedelascience.R;
import mmm.jlnf.fetedelascience.asyncTasks.ItineraireTask;
import mmm.jlnf.fetedelascience.database.*;
import mmm.jlnf.fetedelascience.pojos.EventPojo;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static boolean isOrganisteur = false;

    private GoogleMap mMap;
    private List<Marker> markers;
    private DBManager dbManager;
    private List<EventPojo> itinéraire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        markers = new ArrayList<>();
        dbManager = DBManager.getInstance();

        setContentView(R.layout.activity_maps);
    }

    @Override
    protected void onStart() {
        super.onStart();
        itinéraire = (List<EventPojo>) getIntent().getSerializableExtra("itis");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMap.clear();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(itinéraire.size() >2){
            ItineraireTask it = new ItineraireTask(this, mMap);
            it.setPassages(itinéraire);
            it.execute();
        }
        mMap.setOnCameraIdleListener(() -> {
            LatLng topLeft = mMap.getProjection().fromScreenLocation(new Point(0,0));
            LatLng center = mMap.getCameraPosition().target;
            double rayon = Math.sqrt(Math.pow(topLeft.latitude - center.latitude,2) + Math.pow(topLeft.longitude - center.longitude,2));

            List<EventPojo> res = dbManager.getPojosByCoordinate(center, rayon);


            Log.e("MapEVENT", String.valueOf(res));

            List<Marker> mtosuppress= new ArrayList<>();

            for(Marker m: markers){
                LatLng pos = m.getPosition();
                double lat = pos.latitude;
                double lng = pos.longitude;
                double clat = center.latitude;
                double clng = center.longitude;
                if(lat >= clat + rayon
                        || lat <= clat - rayon
                        || lng >= clng + rayon
                        || lng <= clng - rayon){
                    mtosuppress.add(m);
                    m.remove();
                }
            }

            for (Marker m: mtosuppress){
                markers.remove(m);
            }

            for (EventPojo event : res) {
                boolean marked = false;
                for (Marker m : markers) {
                    if (m.getTag().equals(event.getId())) {
                        marked = true;
                        break;
                    }
                }
                if (!marked) {
                    LatLng evpos = new LatLng(event.getLat(), event.getLng());
                    Marker m = mMap.addMarker(new MarkerOptions().position(evpos).title(event.getTitre_fr()).snippet(event.getDescription_fr()));
                    m.setTag(event.getId());
                    markers.add(m);
                }
            }

        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.11,-1.68), 10f));
    }
}
