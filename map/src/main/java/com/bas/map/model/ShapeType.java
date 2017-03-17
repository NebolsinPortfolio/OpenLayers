package com.bas.map.model;

/**
 * Types of shape
 */
public class ShapeType {

    private Integer id;
    private String name;

    public ShapeType() {
    }

    public ShapeType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapeType shapeType = (ShapeType) o;
        return getId().equals(shapeType.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "ShapeType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
