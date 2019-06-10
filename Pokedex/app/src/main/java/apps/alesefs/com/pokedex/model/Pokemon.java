package apps.alesefs.com.pokedex.model;

public class Pokemon {

    private int number;

    public int getNumber() {
        String[] urlSplit = getUrl().split("/");
        return Integer.parseInt(urlSplit[urlSplit.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String url;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
