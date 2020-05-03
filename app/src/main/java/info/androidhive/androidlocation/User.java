package info.androidhive.androidlocation;

public class User {
    public int rssi;
    public String name;
    public String ip;
    public double latitude;
    public double longitude;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(int RSSI,String spname, String IP, double lat, double lon) {
        this.rssi=RSSI;
        this.name = spname;
        this.ip=IP;
        this.latitude = lat;
        this.longitude=lon;
    }

}
