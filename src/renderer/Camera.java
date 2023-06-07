package renderer;

import primitives.*;

import java.util.MissingResourceException;
import java.util.stream.IntStream;

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
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    //Multithreading
    private boolean threading = true;

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
     * Setter for the ImageWriter
     * @param imageWriter object
     * @return this camera object
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Setter for the RayTracer
     * @param rayTracer object
     * @return this camera object
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
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
        double Ry = (double) height / nY;
        double Rx = (double) width / nX;
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
     * Prints a grid with the given interval and color on top of the image
     * @param interval the size of the boxes
     * @param color the color of the grid
     */
    public void printGrid(int interval, Color color){
        if (imageWriter == null) {
            throw new MissingResourceException("ImageWriter not set", "Camera", "printGrid");
        }

        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Checks to make sure ImageWriter is set and calls writeToImage
     * Otherwise throws MissingResourceException
     */
    public void writeToImage(){
        if (imageWriter == null) {
            throw new MissingResourceException("ImageWriter not set", "Camera", "writeToImage");
        }

        imageWriter.writeToImage();
    }

    /**
     * Setter for threading
     * @param threading boolean for whether to use multithreading
     * @return this camera object
     */
    public Camera setThreading(boolean threading){
        this.threading = threading;
        return this;
    }

    /**
     * Render the image with the camera object
     */
    public ImageWriter renderImage(){
        if (imageWriter == null || rayTracer == null){
            throw new MissingResourceException("Error: Null value", "Camera", null);
        }

        if (!threading) {
            //no threading
            for (int i = 0; i < imageWriter.getNy(); i++) {
                for (int j = 0; j < imageWriter.getNx(); j++) {
                    imageWriter.writePixel(j, i, rayTracer.traceRay(constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i)));
                }
            }
        }else {
            //Run on threads
            Pixel.initialize(imageWriter.getNy(), imageWriter.getNx(), 100l);
            IntStream.range(0, imageWriter.getNy()).parallel().forEach(i -> {
                IntStream.range(0, imageWriter.getNx()).parallel().forEach(j -> {
                    imageWriter.writePixel(j, i, rayTracer.traceRay(constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i)));
                    Pixel.pixelDone();
                    Pixel.printPixel();
                });
            });
        }
        return imageWriter;
    }

}
