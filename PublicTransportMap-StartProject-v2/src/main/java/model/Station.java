package model;

import java.util.HashSet;
import java.util.Set;

public class Station {

    private String stationName;
    private Set<Line> lines;

    public Station(String nodeName) {
        this.stationName = nodeName;
        lines = new HashSet<>();
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public String getStationName() {
        return stationName;
    }

    public Set<Line> getLines() {
        return lines;
    }

    /**
     * Method to find the lines two stations have in common, the intersection of their sets of lines.
     * @param station Station to find common lines with
     * @return The set of lines the stations have in common
     */
    public Set<Line> getCommonLines(Station station) {
        Set<Line> commons = new HashSet<>(lines);
        commons.retainAll(station.getLines());
        return commons;
    }


    /**
     * Method to get one line that two stations have in common
     * @param station
     * @return
     */
    public Line getCommonLine(Station station) {
        Line firstResult = null;
        Set<Line> commons = getCommonLines(station);
        for (Line line : commons) {
            firstResult = line;
            break;
        }
        return firstResult;
    }


    public boolean hasCommonLine(Station station) {
        return !getCommonLines(station).isEmpty();
    }

    public boolean hasLine(Line line) {
        return lines.contains(line);
    }

    @Override
    public boolean equals(Object other) {
        return this.stationName.equals(((Station) other).stationName);
    }

    @Override
    public int hashCode() {
        return stationName.hashCode();
    }

    @Override
    public String toString() {
        return stationName;
    }
}
