package model;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 1/5/20
 */
public class Location {

    private static final double TIME_PER_SQUARE = 1.5;
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Find travel time to another location.
     * @param location
     * @return
     */
    public double travelTime(Location location) {
        int deltaY = location.getY() - getY();
        int deltaX = location.getX() - getX();
        double distance = Math.sqrt((deltaY * deltaY) + (deltaX * deltaX));
        return distance * TIME_PER_SQUARE;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{x = " + this.x + ", y= " + this.y + " }";
    }

}
