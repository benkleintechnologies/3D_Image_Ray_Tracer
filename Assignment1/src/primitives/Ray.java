package primitives;

import java.util.Objects;

public class Ray {
    final Point point;
    final Vector direction;

    public Ray(Point point, Vector direction) {
        this.point = point;
        this.direction = direction.normalize();
    }

    public Point getPoint() {
        return point;
    }

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
