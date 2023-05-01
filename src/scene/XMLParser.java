package scene;

import elements.AmbientLight;
import geometries.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    private String FILENAME = "";
    Scene scene;
    Geometries geometries = new Geometries();

    public XMLParser(String fileName){
        this.FILENAME = fileName;
    }

    public Scene parse(){
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));

            // get scene background color
            Node sceneNode = doc.getElementsByTagName("scene").item(0);
            String backgroundString = ((Element) sceneNode).getAttribute("background-color");
            int red = Integer.parseInt(backgroundString.split(" ")[0]);
            int green = Integer.parseInt(backgroundString.split(" ")[1]);
            int blue = Integer.parseInt(backgroundString.split(" ")[2]);
            Color background = new Color(red, green, blue);

            //get ambient light
            Node ambientLightNode = doc.getElementsByTagName("ambient-light").item(0);
            String ambientLightString = ((Element) ambientLightNode).getAttribute("color");
            red = Integer.parseInt(ambientLightString.split(" ")[0]);
            green = Integer.parseInt(ambientLightString.split(" ")[1]);
            blue = Integer.parseInt(ambientLightString.split(" ")[2]);
            AmbientLight ambientLight = new AmbientLight(new Color(red, green, blue), new Double3(1,1,1));

            // get children of <geometries> (i.e. the shpaes)
            NodeList list = doc.getElementsByTagName("geometries").item(0).getChildNodes();

            for (int temp = 0; temp < list.getLength(); temp++){

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // get type of shape
                    String shapeType = element.getNodeName();

                    //If it's a triangle
                    if (shapeType.equals("triangle")){
                        String p0String = element.getAttribute("p0");
                        String p1String = element.getAttribute("p1");
                        String p2String = element.getAttribute("p2");
                        Point p0 = new Point(Integer.parseInt(p0String.split(" ")[0]), Integer.parseInt(p0String.split(" ")[1]), Integer.parseInt(p0String.split(" ")[2]));
                        Point p1 = new Point(Integer.parseInt(p1String.split(" ")[0]), Integer.parseInt(p1String.split(" ")[1]), Integer.parseInt(p1String.split(" ")[2]));
                        Point p2 = new Point(Integer.parseInt(p2String.split(" ")[0]), Integer.parseInt(p2String.split(" ")[1]), Integer.parseInt(p2String.split(" ")[2]));
                        //Create and add Triangle to geometries list
                        Triangle triangle = new Triangle(p0, p1, p2);
                        geometries.add(triangle);
                    } else if (shapeType.equals("sphere")) {
                        String centerString = element.getAttribute("center");
                        Point center = new Point(Integer.parseInt(centerString.split(" ")[0]), Integer.parseInt(centerString.split(" ")[1]), Integer.parseInt(centerString.split(" ")[2]));
                        double radius = Double.parseDouble(element.getAttribute("radius"));
                        //Create and add Sphere to geometries list
                        Sphere sphere = new Sphere(radius, center);
                        geometries.add(sphere);
                    } else if (shapeType.equals("Plane")) {
                        String p0String = element.getAttribute("p0");
                        String p1String = element.getAttribute("p1");
                        String p2String = element.getAttribute("p2");
                        Point p0 = new Point(Integer.parseInt(p0String.split(" ")[0]), Integer.parseInt(p0String.split(" ")[1]), Integer.parseInt(p0String.split(" ")[2]));
                        Point p1 = new Point(Integer.parseInt(p1String.split(" ")[0]), Integer.parseInt(p1String.split(" ")[1]), Integer.parseInt(p1String.split(" ")[2]));
                        Point p2 = new Point(Integer.parseInt(p2String.split(" ")[0]), Integer.parseInt(p2String.split(" ")[1]), Integer.parseInt(p2String.split(" ")[2]));
                        //Create and add Plane to geometries list
                        Plane plane = new Plane(p0, p1, p2);
                        geometries.add(plane);
                    } else if (shapeType.equals("Polygon")) {
                        NamedNodeMap attributes = element.getAttributes();
                        //Get all points
                        List<Point> points = new ArrayList<>();
                        for (int i = 0; i < attributes.getLength(); i++){
                            Node attribute = attributes.item(i);
                            String pointString =  attribute.getNodeValue();
                            Point point = new Point(Integer.parseInt(pointString.split(" ")[0]), Integer.parseInt(pointString.split(" ")[1]), Integer.parseInt(pointString.split(" ")[2]));
                            points.add(point);
                        }
                        //Create and add Polygon to geometries list
                        //TODO:Figure out how to make Polygon from list of points
                        //geometries.add(polygon);
                    } else if (shapeType.equals("Cylinder")) {
                        //TODO: Implement Cylinder parser
                    } else if (shapeType.equals("Tube")) {
                        //TODO: Implement Tube parser
                    }


                }
            }

            scene = new Scene("XML Parsing").setGeometries(geometries).setBackground(background).setAmbientLight(ambientLight);
            return scene;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}