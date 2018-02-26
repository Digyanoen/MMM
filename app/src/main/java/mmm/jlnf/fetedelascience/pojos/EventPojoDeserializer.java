package mmm.jlnf.fetedelascience.pojos;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by julien on 18/02/18.
 */

public class EventPojoDeserializer implements JsonDeserializer<EventPojo> {
    @Override
    public EventPojo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement geo = jsonObject.get("geolocalisation");

        Double lat = null;
        Double lng = null;

        if(geo != null) {
            lat = geo.getAsJsonArray().get(0).getAsDouble();
            lng = geo.getAsJsonArray().get(1).getAsDouble();
        }

        JsonElement id = jsonObject.get("id");
        JsonElement desc = jsonObject.get("description_fr");
        JsonElement titre = jsonObject.get("titre_fr");
        JsonElement ville = jsonObject.get("ville");
        JsonElement mots = jsonObject.get("mots_cles_fr");
        JsonElement dep = jsonObject.get("departement");
        JsonElement identifiant = jsonObject.get("identifiant");
        JsonElement dates = jsonObject.get("dates");
        JsonElement image = jsonObject.get("image");
        if(image != null)Log.e("image", image.getAsString());
        return new EventPojo(
                id.getAsInt(),
                desc == null? null:desc.getAsString(),
                titre == null? null:titre.getAsString(),
                ville == null? null:ville.getAsString(),
                mots == null? null:mots.getAsString(),
                dep == null? null:dep.getAsString(),
                identifiant == null? null:identifiant.getAsString(),
                image == null? null:image.getAsString(),
                dates == null? null:dates.getAsString(),
                lat,
                lng
        );
    }
}
