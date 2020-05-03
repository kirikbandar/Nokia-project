package info.androidhive.androidlocation;

public class op {

    public double latitude;
    public double longitude;
    public int tag;
    public double dist;

    public op() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public op(double lat, double lon, int tag, double dist) {

        this.latitude = lat;
        this.longitude=lon;
        this.tag=tag;
        this.dist=dist;
    }

}
