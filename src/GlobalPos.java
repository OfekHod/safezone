public class GlobalPos {
    double latitude;
    double longitude;

    final static double rEarth = 6371008.8;

    private GlobalPos(GlobalPos newPos) {
        this.latitude = newPos.latitude;
        this.longitude = newPos.longitude;
    }

    public GlobalPos(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static GlobalPos addMeters(double latitude, double longitude, double dx, double dy) {
        GlobalPos newPos = new GlobalPos(latitude, longitude);
        newPos.latitude = latitude + (dy / rEarth) * (180 / Math.PI);
        newPos.longitude = longitude + (dx / rEarth) * (180 / Math.PI) / Math.cos(latitude * Math.PI / 180);

        return newPos;
    }

    public double getLongitude() {
        return longitude;
    }
}
