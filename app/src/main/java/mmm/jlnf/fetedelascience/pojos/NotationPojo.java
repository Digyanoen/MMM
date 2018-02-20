package mmm.jlnf.fetedelascience.pojos;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nicolas on 12/02/18.
 */

public class NotationPojo {
    private String comment;
    private String user;
    private  int stars;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("commentaire", comment);
        map.put("stars", stars);
        return map;
    }
}
