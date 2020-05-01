package com.example.nokiafinal1;

public class Nokia {
    String nokiaId;
    String nokiaLat;
    String nokiaLong;
    String nokiaTag;

    public Nokia(){

    }

    public Nokia(String nokiaId,String nokiaLat, String nokiaLong, String nokiaTag) {
        this.nokiaId = nokiaId;
        this.nokiaLat = nokiaLat;
        this.nokiaLong = nokiaLong;
        this.nokiaTag = nokiaTag;
    }

    public String getNokiaId() {
        return nokiaId;
    }
    public String getNokiaLat() {
        return nokiaLat;
    }

    public String getNokiaLong() {
        return nokiaLong;
    }

    public String getNokiaTag() {
        return nokiaTag;
    }
}
