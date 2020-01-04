package model;

import java.util.HashSet;
import java.util.Set;

public class Connection {

    private Station from;
    private Station to;
    private double weight;
    private Line line;

    public Connection(Station from, Station to) {
        this.from = from;
        this.to = to;
    }

    public Connection(Station from, Station to, double weight, Line line) {
        this(from, to);
        this.weight = weight;
        this.line = line;
    }


    public Station getFrom() {
        return from;
    }

    public Station getTo() {
        return to;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }



    @Override
    public boolean equals(Object other) {
        return this.from.equals(((Connection) other).from) && this.to.equals(((Connection) other).to);
    }

    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode();
    }

    @Override
    public String toString() {
        return String.format("from %s to %s on line %s", getFrom().getStationName(), getTo().getStationName(), line);
    }
}
