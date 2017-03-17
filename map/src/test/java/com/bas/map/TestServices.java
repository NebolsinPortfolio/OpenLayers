package com.bas.map;


import com.bas.map.model.Coordinate;
import com.bas.map.model.Shape;
import com.bas.map.model.ShapeType;
import com.bas.map.service.ShapeService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;


public class TestServices {

    private static ShapeService shapeService;
    private static Shape shapePoint;
    private static Shape shapeLine;
    private static Shape shapePolygon;
    private static List<Shape> shapes;

    @BeforeClass
    public static void setup() {
        shapeService = new ShapeService();
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
    }

    @AfterClass
    public static void tearDown() {
        shapeService = null;
    }

    @Test
    public void testGetAllShapes() {
        List<Shape> shapes = shapeService.getAllShapes();
        Assert.assertNotNull(shapes);
        for (Shape shape : shapes) {
            System.out.println(shape);
        }
    }

    @Test
    public void testCreateShape() {
        shapeService.createShape(shapePoint);
        shapeService.createShape(shapeLine);
        shapeService.createShape(shapePolygon);
        Assert.assertTrue(shapePoint.getId() != 0);
        Assert.assertTrue(shapeLine.getId() != 0);
        Assert.assertTrue(shapePolygon.getId() != 0);
        Shape createdShapePoint = shapeService.getShapeById(shapePoint.getId());
        Shape createdShapeLine = shapeService.getShapeById(shapeLine.getId());
        Shape createdShapePolygon = shapeService.getShapeById(shapePolygon.getId());
        Assert.assertEquals(shapePoint, createdShapePoint);
        Assert.assertEquals(shapeLine, createdShapeLine);
        Assert.assertEquals(shapePolygon, createdShapePolygon);
    }

    @Test
    public void testCreateShapes() {
        shapeService.createAllShapes(shapes);
        List<Shape> createdShapes = shapeService.getAllShapes();
        Assert.assertNotNull(createdShapes);
    }

    @Test
    public void testDeleteAllRecords() {
        shapeService.deleteAllShapes();
        List<Shape> shapes = shapeService.getAllShapes();
        Assert.assertTrue(shapes.isEmpty());
    }

}
