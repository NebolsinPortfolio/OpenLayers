package com.bas.map.mapper;

import com.bas.map.model.Shape;


import java.util.List;

/**
 * Mapper for Shape
 */
public interface ShapeMapper {

    Shape getShapeById(Long id);

    /**
     * Returns all shapes from table "SHAPES"
     * @return list of shapes
     */
    List<Shape> getAllShapes();

    /**
     * Adds shape to table "SHAPES"
     * @param shape shape
     */
    void insertShape(Shape shape);

    /**
     * Clears table "SHAPES"
     */
    void deleteAllShapes();
}
