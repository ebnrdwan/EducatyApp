package Models;

/**
 * Created by ASI on 2/26/2017.
 */

public class schoolmodels {
    String id ;
    String name;

    public schoolmodels(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
