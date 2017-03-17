package com.bas.map.enumeration;

/**
 * Enum for mapper shape<->feature
 */
public enum TypeOfShape {
    POINT(1),
    LINESTRING(2),
    POLYGON(3);

    private int id;

    TypeOfShape(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
