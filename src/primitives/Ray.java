package primitives;

/**
 * Class Ray is the basic class representing a ray starting at a point in Cartesian
 * 3-Dimensional coordinate system.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Ray {
    final Point point;
    final Vector direction;

    public Ray(Point point, Vector direction) {
        this.point = point;
        this.direction = direction.normalize();
    }

    /**
     * Getter for point
     * @return starting point of ray
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Getter for direction
     * @return direction of ray
     */
    public Vector getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Ray)) return false;
        Ray ray = (Ray) obj;
        return (ray.point.equals(this.point) && ray.direction.equals(this.direction));
    }

    @Override
    public String toString() {
        return "point = " + point + ", direction = " + direction;
    }
}
