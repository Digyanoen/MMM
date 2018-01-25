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
}
