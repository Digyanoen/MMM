package mmm.jlnf.fetedelascience.pojos;

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

        JsonElement desc = jsonObject.get("description_fr");
        JsonElement titre = jsonObject.get("titre_fr");
        JsonElement ville = jsonObject.get("ville");
        JsonElement mots = jsonObject.get("mots_cle_fr");
        JsonElement dep = jsonObject.get("departement");
        return new EventPojo(
                desc == null? null:desc.getAsString(),
                titre == null? null:titre.getAsString(),
                ville == null? null:ville.getAsString(),
                mots == null? null:mots.getAsString(),
                dep == null? null:dep.getAsString(),
                lat,
                lng
        );
    }
}
