/**
 * 
 */
package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   private Scene scene = new Scene("Test scene");

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheres() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(150, 150).setVPDistance(1000);

      scene.geometries.add( //
                           new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                           new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add( //
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                          .setKl(0.0004).setKq(0.0000006));

      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheresOnMirrors() {
      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(2500, 2500).setVPDistance(10000); //

      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

      scene.geometries.add( //
                           new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setKt(new Double3(0.5, 0, 0))),
                           new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(1.0)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
         .setKl(0.00001).setKq(0.000005));

      ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(200, 200).setVPDistance(1000);

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

      scene.geometries.add( //
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
         .setKl(4E-5).setKq(2E-7));

      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   @Test
   public void impressivePhoto() {
      Camera camera = new Camera(new Point(0, 0, 5000), new Vector(0, 0, -1), new Vector(0, 1, 0))
              .setVPSize(2500, 2500).setVPDistance(5000);

      // Set ambient light
      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));
      scene.setBackground(new Color(135, 206, 235));

      // Add geometries to the scene
      // Add geometries to the scene
      scene.geometries.add(
              // Main body of the house (Polygon)
              new Polygon(
                      new Point(-1000, -1000, -2000),
                      new Point(1000, -1000, -2000),
                      new Point(1000, 1000, -2000),
                      new Point(-1000, 1000, -2000)
              )
                      .setEmission(new Color(200, 200, 200))
                      .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(30)),

              // Roof (Polygon)
              new Triangle(
                      new Point(-1000, 1000, -2000),
                      new Point(0, 1500, -2000),
                      new Point(1000, 1000, -2000)
              )
                      .setEmission(new Color(100, 0, 0))
                      .setMaterial(new Material().setKd(0.1).setKs(0.5).setShininess(10)),

              // Door (Polygon)
              new Polygon(
                      new Point(-300, -1000, -1999),
                      new Point(-300, -500, -1999),
                      new Point(300, -500, -1999),
                      new Point(300, -1000, -1999)
              )
                      .setEmission(new Color(50, 25, 0))
                      .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(20)),

              // Window 1 (Polygon)
              new Polygon(
                      new Point(-800, -700, -1999),
                      new Point(-800, -400, -1999),
                      new Point(-500, -400, -1999),
                      new Point(-500, -700, -1999)
              )
                      .setEmission(new Color(0, 100, 100))
                      .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(20)),

              // Window 2 (Polygon)
              new Polygon(
                      new Point(500, -700, -1999),
                      new Point(500, -400, -1999),
                      new Point(800, -400, -1999),
                      new Point(800, -700, -1999)
              )
                      .setEmission(new Color(0, 100, 100))
                      .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(20)),

              // Window 3 (Polygon)
              new Polygon(
                      new Point(-800, 500, -1999),
                      new Point(-800, 200, -1999),
                      new Point(-500, 200, -1999),
                      new Point(-500, 500, -1999)
              )
                      .setEmission(new Color(0, 100, 100))
                      .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(20)),

              // Window 4 (Polygon)
              new Polygon(
                      new Point(500, 500, -1999),
                      new Point(500, 200, -1999),
                      new Point(800, 200, -1999),
                      new Point(800, 500, -1999)
              )
                      .setEmission(new Color(0, 100, 100))
                      .setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(20)),

              // Chimney (Cylinder)
              new Cylinder(new Ray(new Point(500, 1500, -2001), new Vector(0, 1, 0)), 100d, 500d)
                      .setEmission(new Color(100, 100, 100))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)),

              // Front lawn (Polygon)
              new Polygon(
                      new Point(-2500, -1000, -4000),
                      new Point(2500, -1000, -4000),
                      new Point(2500, -1000, 1000),
                      new Point(-2500, -1000, 1000)
              )
                      .setEmission(new Color(0, 150, 0))
                      .setMaterial(new Material().setKd(0.2).setKs(0.1).setShininess(10)),

              // Cloud 1 (Spheres)
              new Sphere(200d, new Point(-1500, 1000, -1500))
                      .setEmission(new Color(255, 255, 255))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10)),
              new Sphere(200d, new Point(-1200, 1200, -1500))
                      .setEmission(new Color(255, 255, 255))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10)),
              new Sphere(150d, new Point(-1300, 1100, -1500))
                      .setEmission(new Color(255, 255, 255))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10)),
              new Sphere(250d, new Point(-1300, 1000, -1500))
                      .setEmission(new Color(255, 255, 255))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10)),

              // Cloud 2 (Spheres)
              new Sphere(150d, new Point(1000, 1250, -1000))
                      .setEmission(new Color(255, 255, 255))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10)),
              new Sphere(200d, new Point(1300, 1100, -1000))
                      .setEmission(new Color(255, 255, 255))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10)),
              new Sphere(250d, new Point(1200, 1200, -1000))
                      .setEmission(new Color(255, 255, 255))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10))

      );

      // Add lights to the scene
      scene.lights.add(
              new SpotLight(new Color(1000, 1000, 1000), new Point(-2000, -2000, 2000), new Vector(1, 1, -1))
                      .setKl(0.0001).setKq(0.00005));
      scene.lights.add(new DirectionalLight(new Color(100, 100, 100), new Vector(0, -1, -1)));
      scene.lights.add(new PointLight(new Color(500, 0, 0), new Point(1000, 1000, 2000)).setKl(0.0001).setKq(0.00005));
      scene.lights.add(
              new SpotLight(new Color(1000, 1000, 0), new Point(2000, 2000, 0), new Vector(-.01, -.01, -1))
                      .setKl(0.00000001).setKq(0.00000005));

      ImageWriter imageWriter = new ImageWriter("impressivePhoto", 500, 500);
      camera.setImageWriter(imageWriter)
              .setRayTracer(new RayTracerBasic(scene))
              .renderImage()
              .writeToImage();
   }


}
