package mmm.jlnf.fetedelascience.asyncTasks;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mmm.jlnf.fetedelascience.R;
import mmm.jlnf.fetedelascience.pojos.EventPojo;

/**
 * Created by julien on 20/02/18.
 */

public class ItineraireTask extends AsyncTask<Void, Integer, Boolean> {
    private static final String TOAST_MSG = "Calcul de l'itinéraire en cours";
    private static final String TOAST_ERR_MAJ = "Impossible de trouver un itinéraire";

    private Context context;
    private GoogleMap gMap;
    private EventPojo editDepart;
    private EventPojo editArrivee;
    private List<EventPojo> listPassage = new ArrayList<EventPojo>();
    private ArrayList<LatLng> lstLatLng = new ArrayList<LatLng>();

    /**
     * Constructeur.
     * @param context
     * @param gMap
     */
    public ItineraireTask( Context context,  GoogleMap gMap) {
        this.context = context;
        this.gMap= gMap;
    }

    public void setPassages(List<EventPojo> pass){
        listPassage.clear();
        editDepart = pass.get(0);
        for (int i=1; i<pass.size()-1; i++) {
            listPassage.add(pass.get(i));
        }
        editArrivee = pass.get(pass.size()-1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPreExecute() {
        Toast.makeText(context, TOAST_MSG, Toast.LENGTH_LONG).show();
    }

    /***
     * {@inheritDoc}
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Log.e("URL build", "YOOOOOOO1");
            lstLatLng.clear();
            //Construction de l'url à appeler
            StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/directions/xml?sensor=false&language=fr");
            url.append("&origin=");
           // url.append(editDepart.getLat()+","+editDepart.getLng());
            url.append("&destination=");
            //url.append(editArrivee.getLat()+","+editArrivee.getLng());
            Log.e("URL build", "YOOOOOOO2");
            Log.e("URL build", String.valueOf(listPassage.isEmpty()));
            if(!listPassage.isEmpty()){
                url.append("&waypoints=");
                 String tmp = "";
                for (EventPojo p : listPassage) {
                    Log.e("URL build", "4");
                   // tmp = tmp.concat(p.getLat()+","+p.getLng()+"|");
                }
                Log.e("URL build", "5");

                Log.e("URL build", tmp.substring(0, tmp.length()-1));
                url.append(tmp.substring(0, tmp.length()-1));
            }
            url.append("&key=AIzaSyDzR-2-ogM0rCpViV8jrUx3NexzP0cwN2s");

            Log.e("URL build", String.valueOf(url));

            //Appel du web service
            InputStream stream = new URL(url.toString()).openStream();

            //Traitement des données
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setIgnoringComments(true);

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(stream);
            document.getDocumentElement().normalize();

            //On récupère d'abord le status de la requête
            String status = document.getElementsByTagName("status").item(0).getTextContent();
            Log.e("STATUS", status);
            if(!"OK".equals(status)) {
                return false;
            }

            //On récupère les steps
            Element elementLeg = (Element) document.getElementsByTagName("leg").item(0);
            NodeList nodeListStep = elementLeg.getElementsByTagName("step");
            int length = nodeListStep.getLength();

            for(int i=0; i<length; i++) {
                Node nodeStep = nodeListStep.item(i);

                if(nodeStep.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementStep = (Element) nodeStep;

                    //On décode les points du XML
                    decodePolylines(elementStep.getElementsByTagName("points").item(0).getTextContent());
                }
            }

            return true;
        }
        catch( Exception e) {
            return false;
        }
    }

    /**
     * Méthode qui décode les points en latitude et longitudes
     * @param encodedPoints
     */
    private void decodePolylines( String encodedPoints) {
        int index = 0;
        int lat = 0, lng = 0;

        while (index < encodedPoints.length()) {
            int b, shift = 0, result = 0;

            do {
                b = encodedPoints.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;

            do {
                b = encodedPoints.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            lstLatLng.add(new LatLng((double)lat/1E5, (double)lng/1E5));
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPostExecute( Boolean result) {
        if(!result) {
            Toast.makeText(context, TOAST_ERR_MAJ, Toast.LENGTH_SHORT).show();
        }
        else {
            //On déclare le polyline, c'est-à-dire le trait (ici bleu) que l'on ajoute sur la carte pour tracer l'itinéraire
            PolylineOptions polylines = new PolylineOptions();
            polylines.color(Color.BLUE);

            //On construit le polyline
            for( LatLng latLng : lstLatLng) {
                polylines.add(latLng);
            }
/*
            //On déclare un marker vert que l'on placera sur le départ
            MarkerOptions markerA = new MarkerOptions();
            markerA.position(new LatLng(editDepart.getLat(),editDepart.getLng()));
            markerA.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            //On déclare un marker rouge que l'on mettra sur l'arrivée
            MarkerOptions markerB = new MarkerOptions();
            markerB.position(new LatLng(editArrivee.getLat(),editArrivee.getLng()));
            markerB.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            //On met à jour la carte
//            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lstLatLng.get(0), 10));
            gMap.addPolyline(polylines);
            gMap.addMarker(markerA);
            for (EventPojo p : listPassage) {
                gMap.addMarker(new MarkerOptions().position(new LatLng(p.getLat(),p.getLng())));*/
            }
           // gMap.addMarker(markerB);
       // }
    }

}

