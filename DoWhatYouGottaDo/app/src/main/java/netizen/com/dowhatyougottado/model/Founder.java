package netizen.com.dowhatyougottado.model;

public class Founder {

    private Long id;
    private String accountName;
    private String listKey;
    private double longtitude, latitude;

    public Founder(){}

    public Founder(String accountName){
        this.accountName = accountName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getListKey() {
        return listKey;
    }

    public void setListKey(String listKey) {
        this.listKey = listKey;
    }

    public void setLongtitude(double longtitude){
        this.longtitude = longtitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLongtitude(){
        return this.longtitude;
    }

    public double getLatitude(){
        return this.latitude;
    }
}
