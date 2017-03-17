package com.bas.map.util;

import com.bas.map.enumeration.TypeOfShape;
import com.bas.map.model.Coordinate;
import com.bas.map.model.Shape;
import com.bas.map.model.ShapeType;
import org.geojson.geometry.Geometry;
import org.geojson.geometry.LineString;
import org.geojson.geometry.Point;
import org.geojson.geometry.Polygon;
import org.geojson.object.Feature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mapper between type "Shape" and GeoJSON type "feature"(from package org.geojson)
 */
public class ShapeFeatureMapper {

    private static final String FEATURE_NAME = "name";
    private static final String FEATURE_DESCRIPTION = "description";
    private static TypeOfShape typeOfShape;

    /**
     * Mappers class Shape to class Feature
     * @param shape object of Shape
     * @return object of Feature
     */
    public static Feature shapeToFeature(Shape shape) {
        Feature feature = new Feature();
        Geometry geometry = null;
        Map<String, Serializable> propFeature = new HashMap<>();
        propFeature.put(FEATURE_NAME, shape.getName());
        propFeature.put(FEATURE_DESCRIPTION, shape.getDescription());
        feature.setProperties(propFeature);
        List<Coordinate> coordinates = shape.getCoordinates();
        typeOfShape = TypeOfShape.valueOf(shape.getType().getName().toUpperCase());
        switch (typeOfShape) {
            case POINT:
                geometry  = getPointsOfFuture(coordinates).get(0);
                break;
            case LINESTRING:
                geometry = new LineString(getPointsOfFuture(coordinates));
                break;
            case POLYGON:
                LineString lineString = new LineString(getPointsOfFuture(coordinates));
                List<LineString> lineStrings = new ArrayList<>();
                lineStrings.add(lineString);
                geometry = new Polygon(lineStrings);
                break;
            }
        feature.setGeometry(geometry);
        return feature;
    }

    /**
     * Makes list of Point from list of Coordinate
     * @param coordinates  list of coordinates
     * @return  list of points
     */
    private static List<Point> getPointsOfFuture(List<Coordinate> coordinates) {
        List<Point> points = new ArrayList<>();
        for (Coordinate coordinate: coordinates) {
            points.add(new Point(coordinate.getLongitude(), coordinate.getLatitude()));
        }
        return points;
    }

    /**
     * Mappers list of class Shape to list of class Feature
     * @param shapes  list of shapes
     * @return list of features
     */
    public static List<Feature> shapesToFeatures(List<Shape> shapes) {
        List<Feature> features = new ArrayList<>();
        for (Shape shape: shapes) {
            features.add(shapeToFeature(shape));
        }
        return features;
    }

    /**
     * Mappers class Feature to class Shape
     * @param feature object of Future
     * @return object of Shape
     */
    public static Shape featureToShape(Feature feature) {
        Shape shape = new Shape();
        ShapeType shapeType = new ShapeType();
        List<Coordinate> coordinates = null;
        shape.setName((String)feature.getProperties().get(FEATURE_NAME));
        shape.setDescription((String)feature.getProperties().get(FEATURE_DESCRIPTION));
        Geometry geometry = feature.getGeometry();
        typeOfShape = TypeOfShape.valueOf(geometry.getType().toUpperCase());
        shapeType.setName(geometry.getType());
        shapeType.setId(typeOfShape.getId());
        shape.setType(shapeType);
        switch (typeOfShape) {
            case POINT:
                Point point = (Point) geometry;
                List<double[]> pointList = new ArrayList<>();
                pointList.add(point.getCoordinates());
                coordinates = getCoordinatesOfShape(pointList, shape);
                break;
            case LINESTRING:
                LineString lineString = (LineString) geometry;
                List<double[]> lineStringList = lineString.getCoordinates();
                coordinates = getCoordinatesOfShape(lineStringList, shape);
                break;
            case POLYGON:
                Polygon polygon = (Polygon) geometry;
                List<List<double[]>> polygonList = polygon.getCoordinates();
                coordinates = getCoordinatesOfShape(polygonList.get(0), shape);
                break;
        }
        shape.setCoordinates(coordinates);
        return shape;
    }

    /**
     * Makes list of Coordinate from list of array coordinates of Point
     * @param list list of coordinates
     * @param shape current shape
     * @return list of Coordinate
     */
    private static List<Coordinate> getCoordinatesOfShape(List<double[]> list, Shape shape) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Coordinate coordinate = new Coordinate();
            coordinate.setLongitude(list.get(i)[0]);
            coordinate.setLatitude(list.get(i)[1]);
            coordinate.setMarkerNumber(i+1);
            coordinate.setShape(shape);
            coordinates.add(coordinate);
        }
        return coordinates;
    }

    /**
     * Mappers list of class Feature to list of class Shape
     * @param features list of features
     * @return list of shapes
     */
    public static List<Shape> featuresToShapes(List<Feature> features) {
        List<Shape> shapes = new ArrayList<>();
        for (Feature feature: features) {
            shapes.add(featureToShape(feature));
        }
        return shapes;
    }

}
