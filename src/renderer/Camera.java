package renderer;

import primitives.*;

import java.util.MissingResourceException;

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
    private int width;
    private int height;
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
    public Camera setVPSize(int width, int height) {
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
        //Find middle of View Plane
        Point Pc = p0.add(vTo.scale(distance));

        //Calculate size of pixel
        double Ry = height / nY;
        double Rx = width / nX;
        // Calculate the distance from the center to the (i,j) coordinate
        double Yi = -(i-(nY-1)/2.0)*Ry;
        double Xj = (j-(nX-1)/2.0)*Rx;
        Point Pij = Pc;
        if(Xj != 0){
            Pij = Pij.add(vRight.scale(Xj));
        }
        if(Yi != 0){
            Pij = Pij.add(vUp.scale(Yi));
        }
        //Return ray from the camera to the center of the pixel found
        return new Ray(p0, Pij.subtract(p0));
    }

    /**
     * Render the image with the camera object
     */
    public void renderImage(){
        if (p0 == null || vTo == null || vUp == null || vRight == null) {// || width == null || height == null || distance == null){
            throw new MissingResourceException("Error: Null value", "Camera", null);
        }
        Ray ray;
        Point p;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
               imageWriter.writePixel(j, i, rayTracer.traceRay(constructRay(width, height, j, i)));
            }
        }

    }



}
