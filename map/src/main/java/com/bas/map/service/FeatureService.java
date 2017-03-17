package com.bas.map.service;

import com.bas.map.model.Shape;
import com.bas.map.util.ShapeFeatureMapper;
import org.geojson.object.Feature;

import java.util.List;

/**
 * Service for objects of Feature class
 */
public class FeatureService {

    private static ShapeService shapeService;

    /**
     * Get all shapes from database and convert it to objects GeoJSON Feature
     * with mapper
     * @return - list of objects Feature
     */
    public List<Feature> getAllFeaturesFromDB() {
        shapeService = new ShapeService();
        List<Feature> features = ShapeFeatureMapper.shapesToFeatures(shapeService.getAllShapes());
        return features;
    }

    /**
     * Create shapes to database from GeoJSON objects Feature
     * @param features list of object Features
     */
    public void createAllFeaturesToDB(List<Feature> features) {
        shapeService = new ShapeService();
        List<Shape> shapes = ShapeFeatureMapper.featuresToShapes(features);
        shapeService.createAllShapes(shapes);
    }
}
