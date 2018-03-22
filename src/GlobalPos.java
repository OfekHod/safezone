public class GlobalPos {
    double latitude;
    double longitude;

    final static double rEarth = 6371008.8;

    private GlobalPos(GlobalPos newPos) {
        this.latitude = newPos.latitude;
        this.longitude = newPos.longitude;
    }

    public GlobalPos addMeters(double dx, double dy) {
        GlobalPos newPos = new GlobalPos(this);
        newPos.latitude = latitude + (dy / rEarth) * (180 / Math.PI);
        newPos.longitude = longitude + (dx / rEarth) * (180 / Math.PI) / Math.cos(this.latitude * Math.PI / 180);

        return newPos;
    }
}
