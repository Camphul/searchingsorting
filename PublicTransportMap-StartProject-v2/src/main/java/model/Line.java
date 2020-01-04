package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Line {

    private String name;
    private String type;
    private List<Station> stationsOnLine;

    public Line(String type, String name) {
        this.name = name;
        this.type = type;
        stationsOnLine = new ArrayList<>();
    }

    public void addStation(Station station) {
        stationsOnLine.add(station);
    }

    public List<Station> getStationsOnLine() {
        return stationsOnLine;
    }

    @Override
    public boolean equals(Object other) {
        return this.name.equals(((Line) other).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + " - " + type;
    }

}
