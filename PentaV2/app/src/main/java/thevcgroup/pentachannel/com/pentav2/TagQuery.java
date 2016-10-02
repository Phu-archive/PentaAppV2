package thevcgroup.pentachannel.com.pentav2;

public class TagQuery {
    public String id;
    public String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagQuery(String id, String name){
        this.id = id;
        this.name = name;
    }
}
