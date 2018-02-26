package mmm.jlnf.fetedelascience.pojos;

import com.google.gson.annotations.JsonAdapter;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by nicolas on 21/01/18.
 */

@DatabaseTable(tableName = "events")
@JsonAdapter(EventPojoDeserializer.class)
public class EventPojo implements Serializable{

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(columnName = "description_fr")
    private String description_fr;

    @DatabaseField(columnName = "titre_fr")
    private String titre_fr;

    @DatabaseField(columnName = "ville")
    private String ville;

    @DatabaseField(columnName = "mots_cles_fr")
    private String mots_cles_fr;

    @DatabaseField(columnName = "departement")
    private String departement;
    @DatabaseField(columnName = "identifiant")
    private String identifiant;

    @DatabaseField(columnName = "dates")
    private String dates;

    @DatabaseField(columnName = "image")
    private String image;

    @DatabaseField
    private Double lat;

    @DatabaseField
    private Double lng;

    public EventPojo(int id ,String description_fr, String titre_fr, String ville, String mots_cles_fr, String departement, String identifiant, String image, String dates, Double lat, Double lng) {
        this.id = id;
        this.description_fr = description_fr;
        this.titre_fr = titre_fr;
        this.ville = ville;
        this.mots_cles_fr = mots_cles_fr;
        this.departement = departement;
        this.identifiant = identifiant;
        this.dates = dates;
        this.image = image;
        this.lat = lat;
        this.lng = lng;
    }

    public EventPojo(){}

    public String getDescription_fr() {
        return description_fr;
    }

    public void setDescription_fr(String description_fr) {
        this.description_fr = description_fr;
    }

    public String getTitre_fr() {
        return titre_fr;
    }

    public void setTitre_fr(String titre_fr) {
        this.titre_fr = titre_fr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMots_cles_fr() {
        return mots_cles_fr;
    }

    public void setMots_cles_fr(String mots_cles_fr) {
        this.mots_cles_fr = mots_cles_fr;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "EventPojo{" +
                "id=" + id +
                ", description_fr='" + description_fr + '\'' +
                ", titre_fr='" + titre_fr + '\'' +
                ", ville='" + ville + '\'' +
                ", mots_cles_fr='" + mots_cles_fr + '\'' +
                ", departement='" + departement + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
