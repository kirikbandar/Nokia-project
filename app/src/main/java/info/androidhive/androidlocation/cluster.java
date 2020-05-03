package info.androidhive.androidlocation;

public class cluster {
    private String latitude;
    private String longitude;
    private String tag;

    public cluster(String latitude, String longitude, String tag)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.tag = tag;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
