package com.bas.map.mapper;

import com.bas.map.model.ShapeType;

/**
 * Mapper for ShapeType
 */
public interface ShapeTypeMapper {

    /**
     * Returns type of shape by id from table "SHAPE_TYPES"
     * @param id  id type of shape
     * @return type of shape
     */
    ShapeType getShapeTypeById(Integer id);
}
