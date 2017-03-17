package com.bas.map.model;

import java.util.List;

/**
 * Shapes for map objects
 */
public class Shape {

    private Long id;
    private ShapeType type;
    private String name;
    private String description;
    private List<Coordinate> coordinates;

    public Shape() {
    }

    public Shape(ShapeType type, String name, String description, List<Coordinate> coordinates) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.coordinates = coordinates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShapeType getType() {
        return type;
    }

    public void setType(ShapeType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return getId().equals(shape.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Shape{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
