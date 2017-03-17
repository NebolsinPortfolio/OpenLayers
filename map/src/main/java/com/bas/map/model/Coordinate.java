package com.bas.map.model;

/**
 * Coordinates of shape
 */
public class Coordinate {

    private Long id;
    private Double longitude;
    private Double latitude;
    private Shape shape;
    private Integer markerNumber;

    public Coordinate() {
    }

    public Coordinate(Double longitude, Double latitude, Shape shape, Integer markerNumber) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.shape = shape;
        this.markerNumber = markerNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Integer getMarkerNumber() {
        return markerNumber;
    }

    public void setMarkerNumber(Integer markerNumber) {
        this.markerNumber = markerNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", markerNumber=" + markerNumber +
                '}';
    }
}
