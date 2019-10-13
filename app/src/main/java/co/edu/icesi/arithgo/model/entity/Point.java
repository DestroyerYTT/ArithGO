package co.edu.icesi.arithgo.model.entity;

import java.io.Serializable;

public class Point implements Serializable {

    private String id;
    private String name;
    private Integer points;

    public Point(String id, String name, Integer points) {
        this.id = id;
        this.name = name;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
