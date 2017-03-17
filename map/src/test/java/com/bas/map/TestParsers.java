package com.bas.map;


import com.bas.map.model.Shape;
import com.bas.map.service.ShapeService;
import com.bas.map.util.ShapeFeatureMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.geometry.Geometry;
import org.geojson.object.Feature;
import org.geojson.object.FeatureCollection;
import org.geojson.util.GeometryMixin;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class TestParsers {

    private static ShapeService shapeService;
    private static ObjectMapper serializer;
    private static ObjectMapper deserializer;

    @BeforeClass
    public static void setup() {
        shapeService = new ShapeService();
        serializer = new ObjectMapper();
        deserializer = new ObjectMapper();
        deserializer.addMixInAnnotations( Geometry.class, GeometryMixin.class);
    }


    @AfterClass
    public static void tearDown() {
        shapeService = null;
    }

    @Test
    public void testParseShapesToGeoJSON() throws Exception {
        List<Shape> allShapes = shapeService.getAllShapes();
        Assert.assertNotNull(allShapes);
        FeatureCollection featureCollection = new FeatureCollection();
        List<Feature> features = ShapeFeatureMapper.shapesToFeatures(allShapes);
        featureCollection.setFeatures(features);
        String json = serializer.writeValueAsString(featureCollection);
        System.out.println(json);
    }

    @Test
    public void testParseShapeToGeoJSON() throws Exception {
        Shape testShape = shapeService.getShapeById(30l);
        Assert.assertNotNull(testShape);
        Feature testFeature = ShapeFeatureMapper.shapeToFeature(testShape);
        String json = serializer.writeValueAsString(testFeature);
        System.out.println(json);

    }

    @Test
    public void testParseGeoJSONToShape() throws Exception {
        Shape shapeFromDB = shapeService.getShapeById(30l);
        shapeFromDB.setName("GeoJSON polygon");
        Assert.assertNotNull(shapeFromDB);
        Feature featureFromDB = ShapeFeatureMapper.shapeToFeature(shapeFromDB);
        String json = serializer.writeValueAsString(featureFromDB);
        System.out.println(json);
        Feature featureFromJSON = deserializer.readValue(json, Feature.class);
        Shape shapeFromJSON = ShapeFeatureMapper.featureToShape(featureFromJSON);
        shapeService.createShape(shapeFromJSON);
    }

    @Test
    public void testParseGeoJSONToShapes() throws Exception {
        List<Shape> shapesFromDB = shapeService.getAllShapes();
        for (Shape shape: shapesFromDB) {
            shape.setName(shape.getName() + " JSON");
        }
        List<Feature> featuresFromDB = ShapeFeatureMapper.shapesToFeatures(shapesFromDB);
        FeatureCollection featureColFromDB = new FeatureCollection(featuresFromDB);
        String json = serializer.writeValueAsString(featureColFromDB);
        FeatureCollection featureColFromJSON = deserializer.readValue(json, FeatureCollection.class);
        List<Feature> featuresFromJSON = featureColFromJSON.getFeatures();
        List<Shape> shapesFromJSON = ShapeFeatureMapper.featuresToShapes(featuresFromJSON);
        Assert.assertFalse(shapesFromJSON.isEmpty());
        shapeService.createAllShapes(shapesFromJSON);
    }
}
