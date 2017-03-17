package com.bas.map.service;

import com.bas.map.mapper.CoordinateMapper;
import com.bas.map.mapper.ShapeMapper;
import com.bas.map.model.Shape;
import com.bas.map.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ShapeService {

    /**
     * Returns shape of map by ID
     * @param id id shape
     * @return shape
     */
    public Shape getShapeById(Long id) {
        SqlSession session = MyBatisUtil.getSessionFactory().openSession();
        try {
            ShapeMapper shapeMapper = session.getMapper(ShapeMapper.class);
            return shapeMapper.getShapeById(id);
        }
        finally {
            session.close();
        }
    }

    /**
     * Returns all shapes of map
     * @return list of shapes
     */
    public List<Shape> getAllShapes() {
        SqlSession session = MyBatisUtil.getSessionFactory().openSession();
        try {
            ShapeMapper shapeMapper = session.getMapper(ShapeMapper.class);
            return shapeMapper.getAllShapes();
        }
        finally {
            session.close();
        }
    }

    /**
     * Create a shape of map with their coordinates and their type
     * @param shape a shape of map
     */
    public void createShape(Shape shape) {
        SqlSession session = MyBatisUtil.getSessionFactory().openSession(true);
        try {
            ShapeMapper shapeMapper = session.getMapper(ShapeMapper.class);
            CoordinateMapper coordinateMapper = session.getMapper(CoordinateMapper.class);
            shapeMapper.insertShape(shape);
            coordinateMapper.InsertListOfCoordinates(shape.getCoordinates());
        }
        finally {
            session.close();
        }
    }

    public void createAllShapes(List<Shape> shapes) {
        SqlSession session = MyBatisUtil.getSessionFactory().openSession(true);
        try {
            ShapeMapper shapeMapper = session.getMapper(ShapeMapper.class);
            CoordinateMapper coordinateMapper = session.getMapper(CoordinateMapper.class);
            coordinateMapper.deleteAllCoordinates();
            shapeMapper.deleteAllShapes();
            for (Shape shape: shapes) {
                shapeMapper.insertShape(shape);
                coordinateMapper.InsertListOfCoordinates(shape.getCoordinates());
            }
        }
        finally {
            session.close();
        }
    }

    /**
     * Test method
     * Clear tables "COORDINATES" and "SHAPES"
     */
    public void deleteAllShapes() {
        SqlSession session = MyBatisUtil.getSessionFactory().openSession(true);
        try {
            ShapeMapper shapeMapper = session.getMapper(ShapeMapper.class);
            CoordinateMapper coordinateMapper = session.getMapper(CoordinateMapper.class);
            coordinateMapper.deleteAllCoordinates();
            shapeMapper.deleteAllShapes();
        }
        finally {
            session.close();
        }
    }
}
