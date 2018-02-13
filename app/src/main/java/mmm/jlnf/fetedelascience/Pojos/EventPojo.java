package mmm.jlnf.fetedelascience.Pojos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by nicolas on 21/01/18.
 */

@DatabaseTable(tableName = "events")
public class EventPojo {

    @DatabaseField(generatedId = true)
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
}
