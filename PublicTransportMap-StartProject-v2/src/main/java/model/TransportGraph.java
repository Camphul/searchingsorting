package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransportGraph {

    private int numberOfStations;
    private int numberOfConnections;
    private List<Station> stationList;
    private Map<String, Integer> stationIndices;
    private List<Integer>[] adjacencyLists;
    private Connection[][] connections;

    public TransportGraph(int size) {
        this.numberOfStations = size;
        stationList = new ArrayList<>(size);
        stationIndices = new HashMap<>();
        connections = new Connection[size][size];
        adjacencyLists = (List<Integer>[]) new List[size];
        for (int vertex = 0; vertex < size; vertex++) {
            adjacencyLists[vertex] = new LinkedList<>();
        }
    }

    /**
     * @param vertex Station to be added to the stationList
     *               The method also adds the station with it's index to the map stationIndices
     */
    public void addVertex(Station vertex) {
        int index = this.stationList.size();
        this.stationList.add(vertex);
        this.stationIndices.put(vertex.getStationName(), index);
        // DONE
    }


    /**
     * Method to add an edge to a adjancencyList. The indexes of the vertices are used to define the edge.
     * Method also increments the number of edges, that is number of Connections.
     * The grap is bidirected, so edge(to, from) should also be added.
     *
     * @param from
     * @param to
     */
    private void addEdge(int from, int to) {
        this.adjacencyLists[from].add(to);
        this.adjacencyLists[to].add(from);
        //Count the bidirectional connection as one connection as specified in the assignment
        this.numberOfConnections += 1;
        // DONE
    }


    /**
     * Method to add an edge in the form of a connection between stations.
     * The method also adds the edge as an edge of indices by calling addEdge(int from, int to).
     * The method adds the connecion to the connections 2D-array.
     * The method also builds the reverse connection, Connection(To, From) and adds this to the connections 2D-array.
     *
     * @param connection The edge as a connection between stations
     */
    public void addEdge(Connection connection) {
        int fromIndex = getIndexOfStationByName(connection.getFrom().getStationName());
        int toIndex = getIndexOfStationByName(connection.getTo().getStationName());
        addEdge(fromIndex, toIndex);
        this.connections[fromIndex][toIndex] = connection;
        this.connections[toIndex][fromIndex] = connection;
        // DONE
    }

    public List<Integer> getAdjacentVertices(int index) {
        return adjacencyLists[index];
    }

    public Connection getConnection(int from, int to) {
        return connections[from][to];
    }

    public int getIndexOfStationByName(String stationName) {
        return stationIndices.get(stationName);
    }

    public Station getStation(int index) {
        return stationList.get(index);
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public int getNumberEdges() {
        return numberOfConnections;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append(String.format("Graph with %d vertices and %d edges: \n", numberOfStations, numberOfConnections));
        for (int indexVertex = 0; indexVertex < numberOfStations; indexVertex++) {
            resultString.append(stationList.get(indexVertex) + ": ");
            int loopsize = adjacencyLists[indexVertex].size() - 1;
            for (int indexAdjacent = 0; indexAdjacent < loopsize; indexAdjacent++) {
                resultString.append(stationList.get(adjacencyLists[indexVertex].get(indexAdjacent)).getStationName() + "-");
            }
            resultString.append(stationList.get(adjacencyLists[indexVertex].get(loopsize)).getStationName() + "\n");
        }
        return resultString.toString();
    }


    /**
     * A Builder helper class to build a TransportGraph by adding lines and building sets of stations and connections from these lines.
     * Then build the graph from these sets.
     */
    public static class Builder {

        private Set<Station> stationSet;
        private List<Line> lineList;
        private Set<Connection> connectionSet;
        private Map<Line, double[]> weights;
        private Map<String, Location> locations;

        public Builder() {
            lineList = new ArrayList<>();
            stationSet = new HashSet<>();
            connectionSet = new HashSet<>();
            weights = new HashMap<>();
            locations = new HashMap<>();
        }

        /**
         * Method to add a line to the list of lines and add stations to the line.
         *
         * @param lineDefinition String array that defines the line. The array should start with the name of the line,
         *                       followed by the type of the line and the stations on the line in order.
         * @return
         */
        public Builder addLine(String[] lineDefinition) {
            return addLine(lineDefinition, new double[0]);
        }

        /**
         * Custom builder method adding weights to the connections by configuring them in a map.
         *
         * @param lineDefinition the line definition.
         * @param weights        weights array.
         * @return buiilder.
         */
        public Builder addLine(String[] lineDefinition, double[] weights) {
            if (lineDefinition.length < 2) {
                throw new IllegalArgumentException("Expected minimum length of 2");
            }
            String lineName = lineDefinition[0];
            String lineType = lineDefinition[1];
            Line line = new Line(lineType, lineName);
            for (int i = 2; i < lineDefinition.length; i++) {
                String stationName = lineDefinition[i];
                Station station = new Station(stationName);
                line.addStation(station);
            }
            this.lineList.add(line);
            if (weights != null && weights.length > 0) {
                this.weights.put(line, weights);
            }
            // DONE
            return this;
        }

        /**
         * Sets the location for a station inside the builder.
         *
         * @param stationName
         * @param x
         * @param y
         * @return
         */
        public Builder locate(String stationName, int x, int y) {
            this.locations.put(stationName, new Location(x, y));
            return this;
        }

        /**
         * Method that reads all the lines and their stations to build a set of stations.
         * Stations that are on more than one line will only appear once in the set.
         *
         * @return
         */
        public Builder buildStationSet() {
            for (Line line : lineList) {
                for (Station station : line.getStationsOnLine()) {
                    String name = station.getStationName();
                    //Adds location to station when present.
                    if (this.locations.containsKey(name)) {
                        station.setLocation(this.locations.get(name));
                    }
                    this.stationSet.add(station);
                }
            }
            // DONE
            return this;
        }

        /**
         * For every station on the set of station add the lines of that station to the lineList in the station
         *
         * @return
         */
        public Builder addLinesToStations() {
            Iterator<Station> stationIterator = this.stationSet.iterator();
            while (stationIterator.hasNext()) {
                Station station = stationIterator.next();
                for (Line line : this.lineList) {
                    for (Station lineStation : line.getStationsOnLine()) {
                        if (lineStation.equals(station)) {
                            station.addLine(line);
                        }
                    }
                }
            }
            // DONE
            return this;
        }

        /**
         * Method that uses the list of Lines to build connections from the consecutive stations in the stationList of a line.
         *
         * @return
         */
        public Builder buildConnections() {
            for (Line line : this.lineList) {
                for (int i = 0; i < line.getStationsOnLine().size() - 1; i++) {
                    Station from = line.getStationsOnLine().get(i);
                    Station to = line.getStationsOnLine().get(i + 1);
                    double weight = 0;
                    //Adds weight to connection when present.
                    if (this.weights.containsKey(line)) {
                        weight = this.weights.get(line)[i];
                    }
                    Connection connection = new Connection(from, to, weight, line);
                    this.connectionSet.add(connection);
                }
            }
            // DONE
            return this;
        }

        /**
         * Method that builds the graph.
         * All stations of the stationSet are addes as vertices to the graph.
         * All connections of the connectionSet are addes as edges to the graph.
         *
         * @return
         */
        public TransportGraph build() {
            TransportGraph graph = new TransportGraph(stationSet.size());
            Iterator<Station> stationIterator = this.stationSet.iterator();
            while (stationIterator.hasNext()) {
                Station station = stationIterator.next();
                graph.addVertex(station);
            }
            Iterator<Connection> connectionIterator = this.connectionSet.iterator();
            while (connectionIterator.hasNext()) {
                Connection connection = connectionIterator.next();
                graph.addEdge(connection);
            }
            // DONE
            return graph;
        }

    }
}
