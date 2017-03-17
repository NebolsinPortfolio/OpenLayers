package com.bas.map.mapper;

import com.bas.map.model.Coordinate;

import java.util.List;

/**
 * Mapper for Coordinate
 */
public interface CoordinateMapper {

    /**
     * Returns coordinate by id from table "COORDINATES"
     * @param id  id coordinate
     * @return  coordinate
     */
    Coordinate getCoordinateById(Long id);

    /**
     * Returns coordinates of certain shape from table "COORDINATES"
     * @param shapeId id shape
     * @return list of coordinates of shape
     */
    List<Coordinate> getCoordinateByShapeId(Long shapeId);

    /**
     * Adds list of coordinates as multi-row insert to table "COORDINATES"
     * @param coordinates
     */
    void InsertListOfCoordinates(List<Coordinate> coordinates);

    /**
     * Clears table "COORDINATES"
     */
    void deleteAllCoordinates();
}
