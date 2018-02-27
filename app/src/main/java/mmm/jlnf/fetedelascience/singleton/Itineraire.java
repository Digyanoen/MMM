package mmm.jlnf.fetedelascience.singleton;

import java.util.ArrayList;

import mmm.jlnf.fetedelascience.pojos.EventPojo;

/**
 * Created by julien on 26/02/18.
 */

public class Itineraire {

    private ArrayList<EventPojo> pojoList;

    private static volatile Itineraire instance = null;

    /**
     * Constructeur de l'objet.
     */
    private Itineraire() {
        super();
    }

    /**
     * Méthode permettant de renvoyer une instance de la classe Singleton
     * @return Retourne l'instance du singleton.
     */
    public final static Itineraire getInstance() {
        if (Itineraire.instance == null) {
            synchronized(Itineraire.class) {
                if (Itineraire.instance == null) {
                    Itineraire.instance = new Itineraire();
                }
            }
        }
        return Itineraire.instance;
    }

    public ArrayList<EventPojo> getPojoList() {
        return getInstance().pojoList;
    }

    public void setPojoList(ArrayList<EventPojo> pojoList) {
        getInstance().pojoList = pojoList;
    }
}
