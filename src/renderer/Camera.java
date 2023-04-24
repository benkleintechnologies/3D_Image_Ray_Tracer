package renderer;

import primitives.*;

import static java.lang.Math.floor;

/**
 * Class Camera represents a camera in a 3D space.
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
public class Camera {
    //Camera variables
    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    //View Plane variables
    private double width;
    private double height;
    private double distance;

    /**
     * Constructor for Camera. Throws IllegalArgumentException if vTo and vUp are not orthogonal
     * @param position the position of the camera
     * @param vTo the direction of the camera
     * @param vUp a vector perpendicular to vTo which points up
     */
    public Camera(Point position, Vector vTo, Vector vUp) {
        this.p0 = position;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        if (vTo.dotProduct(vUp) == 0) {
            this.vRight = vTo.crossProduct(vUp).normalize();
        }else {
            throw new IllegalArgumentException("vTo and vUp must be orthogonal");
        }
    }

    /**
     * Getter for the position of the camera
     * @return the position of the camera
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter for the direction of the camera
     * @return the direction of the camera
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * Getter for the up vector
     * @return the up vector
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * Getter for the right vector
     * @return the right vector
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * A set function for the View Plane size
     * @param width of the View Plane
     * @param height of the View Plane
     * @return this camera object
     */
    public Camera setVPSize(double width, double height) {
        this.width  = width;
        this.height = height;
        return this;
    }

    /**
     * A set function for the View Plane distance
     * @param distance of the View Plane from the Camera
     * @return this camera object
     */
    public Camera setVPDistance(double distance){
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a ray from the camera through a pixel
     * @param nX represents the number of columns of pixels
     * @param nY represents the number of rows of pixels
     * @param j represents the column of the pixel
     * @param i represents the row of the pixel
     * @return the ray
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        //Find point of middle pixel of the view plane
        Point middle = p0.add(vTo.scale(distance));

        //Find the top left corner of the view plane
        Point vTopLeft = middle.add(vRight.scale(floor(-nX/2)).add(vUp.scale(floor(nY/2))));

        //Find the desired pixel relative to the top left corner
        Point pixel;
        if (i == 0 && j == 0){
            pixel = vTopLeft;
        }else if (i == 0){
            pixel = vTopLeft.add(vRight.scale(j));
        }else if(j == 0){
            pixel = vTopLeft.add(vUp.scale(-i));
        }else{
            pixel = vTopLeft.add(vRight.scale(j).add(vUp.scale(-i)));
        }

        //Return ray from the camera to the center of the pixel found
        return new Ray(p0, pixel.subtract(p0));
    }

}
