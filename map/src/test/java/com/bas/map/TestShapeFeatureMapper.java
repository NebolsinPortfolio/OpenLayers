package com.bas.map;

import com.bas.map.model.Coordinate;
import com.bas.map.model.Shape;
import com.bas.map.model.ShapeType;
import com.bas.map.util.ShapeFeatureMapper;
import org.geojson.geometry.LineString;
import org.geojson.geometry.Point;
import org.geojson.geometry.Polygon;
import org.geojson.object.Feature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

public class TestShapeFeatureMapper {

    private static Shape shapePoint;
    private static Shape shapeLine;
    private static Shape shapePolygon;
    private static List<Shape> shapes;
    private static Feature featurePoint;
    private static Feature featureLine;
    private static Feature featurePolygon;
    private static List<Feature> features;

    @BeforeClass
    public static void setup() {
        ShapeType shapeTypePoint = new ShapeType(1, "Point");
        shapePoint = new Shape(shapeTypePoint, "point", "point desc", null);
        Coordinate coordinatePoint = new Coordinate(11.11, -11.11, shapePoint, 1);
        List<Coordinate> coordinatesPoint = new ArrayList<>();
        coordinatesPoint.add(coordinatePoint);
        shapePoint.setCoordinates(coordinatesPoint);
        ShapeType shapeTypeLine = new ShapeType(2, "LineString");
        shapeLine = new Shape(shapeTypeLine, "line", "line desc", null);
        Coordinate coordinate1Line = new Coordinate(11.11, -11.11, shapeLine, 1);
        Coordinate coordinate2Line = new Coordinate(22.22, -22.22, shapeLine, 2);
        List<Coordinate> coordinatesLine = new ArrayList<>();
        coordinatesLine.add(coordinate1Line);
        coordinatesLine.add(coordinate2Line);
        shapeLine.setCoordinates(coordinatesLine);
        ShapeType shapeTypePolygon = new ShapeType(3, "Polygon");
        shapePolygon = new Shape(shapeTypePolygon, "polygon", "polygon desc", null);
        Coordinate coordinate1Polygon = new Coordinate(11.11, -11.11, shapePolygon, 1);
        Coordinate coordinate2Polygon = new Coordinate(22.22, -22.22, shapePolygon, 2);
        Coordinate coordinate3Polygon = new Coordinate(33.33, -33.33, shapePolygon, 3);
        Coordinate coordinate4Polygon = new Coordinate(11.11, -11.11, shapePolygon, 4);
        List<Coordinate> coordinatesPolygon = new ArrayList<>();
        coordinatesPolygon.add(coordinate1Polygon);
        coordinatesPolygon.add(coordinate2Polygon);
        coordinatesPolygon.add(coordinate3Polygon);
        coordinatesPolygon.add(coordinate4Polygon);
        shapePolygon.setCoordinates(coordinatesPolygon);
        shapes = new ArrayList<>();
        shapes.add(shapePoint);
        shapes.add(shapeLine);
        shapes.add(shapePolygon);

        featurePoint = new Feature(new Point(11.11, -11.11));
        Map<String,Serializable> pointProp = new HashMap<>();
        pointProp.put("name", "point");
        pointProp.put("description", "point desc");
        featurePoint.setProperties(pointProp);
        List<Point> linePoints = new ArrayList<>();
        linePoints.add(new Point(11.11, -11.11));
        linePoints.add(new Point(22.22, -22.22));
        featureLine = new Feature(new LineString(linePoints));
        Map<String,Serializable> lineProp = new HashMap<>();
        lineProp.put("name", "line");
        lineProp.put("description", "line desc");
        featureLine.setProperties(lineProp);
        List<Point> pointsPolygon = new ArrayList<>();
        pointsPolygon.add(new Point(11.11, -11.11));
        pointsPolygon.add(new Point(22.22, -22.22));
        pointsPolygon.add(new Point(33.33, -33.33));
        pointsPolygon.add(new Point(44.44, -44.44));
        LineString linePolygon = new LineString(pointsPolygon);
        List<LineString> linesPolygon = new ArrayList<>();
        linesPolygon.add(linePolygon);
        featurePolygon = new Feature(new Polygon(linesPolygon));
        Map<String,Serializable> polygonProp = new HashMap<>();
        polygonProp.put("name", "polygon");
        polygonProp.put("description", "polygon desc");
        featurePolygon.setProperties(polygonProp);
        features = new ArrayList<>();
        features.add(featurePoint);
        features.add(featureLine);
        features.add(featurePolygon);
    }
    
    @Test
    public void testShapeToFeature() {
        Feature testFeaturePoint = ShapeFeatureMapper.shapeToFeature(shapePoint);
        Feature testFeatureLine = ShapeFeatureMapper.shapeToFeature(shapeLine);
        Feature testFeaturePolygon = ShapeFeatureMapper.shapeToFeature(shapePolygon);
        Assert.assertNotNull(testFeaturePoint);
        Assert.assertEquals(featurePoint.getProperties().get("name"), testFeaturePoint.getProperties().get("name"));
        Assert.assertNotNull(testFeatureLine);
        Assert.assertEquals(featureLine.getProperties().get("name"), testFeatureLine.getProperties().get("name"));
        Assert.assertNotNull(testFeaturePolygon);
        Assert.assertEquals(featurePolygon.getProperties().get("name"), testFeaturePolygon.getProperties().get("name"));
        List<Feature> testFeatures = ShapeFeatureMapper.shapesToFeatures(shapes);
        Assert.assertFalse(testFeatures.isEmpty());
    }

    @Test
    public  void testFutureToShape() {
        Shape testShapePoint = ShapeFeatureMapper.featureToShape(featurePoint);
        Shape testShapeLine = ShapeFeatureMapper.featureToShape(featureLine);
        Shape testShapePolygon = ShapeFeatureMapper.featureToShape(featurePolygon);
        Assert.assertNotNull(testShapePoint);
        Assert.assertEquals(shapePoint.getType(), testShapePoint.getType());
        Assert.assertEquals(shapePoint.getCoordinates().get(0).getMarkerNumber(), testShapePoint.getCoordinates().get(0).getMarkerNumber());
        Assert.assertNotNull(testShapeLine);
        Assert.assertEquals(shapeLine.getDescription(), testShapeLine.getDescription());
        Assert.assertNotNull(testShapePolygon);
        Assert.assertEquals(shapePolygon.getName(), testShapePolygon.getName());
        List<Shape> testShapes = ShapeFeatureMapper.featuresToShapes(features);
        Assert.assertFalse(testShapes.isEmpty());
    }
}
