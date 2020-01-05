package graphalgorithms;

import model.Connection;
import model.Line;
import model.Station;
import model.TransportGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract class that contains methods and attributes shared by the DepthFirstPath en BreadthFirstPath classes
 */
public abstract class AbstractPathSearch {

    protected boolean[] marked;
    protected int[] edgeTo;
    protected int transfers = 0;
    protected List<Station> nodesVisited;
    protected List<Station> nodesInPath;
    protected LinkedList<Integer> verticesInPath;
    protected TransportGraph graph;
    protected final int startIndex;
    protected final int endIndex;
    protected int visitedNodes = 0;


    public AbstractPathSearch(TransportGraph graph, String start, String end) {
        startIndex = graph.getIndexOfStationByName(start);
        endIndex = graph.getIndexOfStationByName(end);
        this.graph = graph;
        nodesVisited = new ArrayList<>();
        marked = new boolean[graph.getNumberOfStations()];
        edgeTo = new int[graph.getNumberOfStations()];
        nodesInPath = new ArrayList<>();
        verticesInPath = new LinkedList<>();
    }

    public abstract void search();

    /**
     * @param vertex Determines whether a path exists to the station as an index from the start station
     * @return
     */
    public boolean hasPathTo(int vertex) {
        return marked[vertex];
    }


    /**
     * Method to build the path to the vertex, index of a Station.
     * First the LinkedList verticesInPath, containing the indexes of the stations, should be build, used as a stack
     * Then the list nodesInPath containing the actual stations is build.
     * Also the number of transfers is counted.
     *
     * @param vertex The station (vertex) as an index
     */
    public void pathTo(int vertex) {
        //Traverse path by traversing edgeTo until we're at startIndex
        if (!hasPathTo(vertex)) {
            return;
        }
        int v = vertex;
        while (v != this.startIndex) {
            //Add as first. Else it would be in reverse order.
            this.verticesInPath.addFirst(v);
            v = edgeTo[v];
        }
        //Finally ad the first start vertex
        this.verticesInPath.addFirst(this.startIndex);
        //Convert to nodes
        for (Integer vertexNode : verticesInPath) {
            this.nodesInPath.add(this.graph.getStation(vertexNode));
        }
        countTransfers();
        // DONE
    }

    /**
     * Method to count the number of transfers in a path of vertices.
     * Uses the line information of the connections between stations.
     * If to consecutive connections are on different lines there was a transfer.
     */
    public void countTransfers() {
        Line line = null;
        for (int i = 0; i < verticesInPath.size() - 1; i++) {
            //For every vertice next to each other get the connection
            int fromIndex = verticesInPath.get(i);
            int to = verticesInPath.get(i + 1);
            Connection connection = this.graph.getConnection(fromIndex, to);
            //If line is null it means we're at index 0 probably.
            if (line == null) {
                line = connection.getLine();
            } else {
                //If line is not equal to last connection then increment transfers.
                if (!line.equals(connection.getLine())) {
                    this.transfers += 1;
                }
                //Set new line
                line = connection.getLine();
            }
        }
        // DONE
    }


    protected void markVisited(int vertex) {
        this.visitedNodes++;
        this.nodesVisited.add(this.graph.getStation(vertex));
    }

    /**
     * Method to print all the nodes that are visited by the search algorithm implemented in one of the subclasses.
     */
    public void printNodesInVisitedOrder() {
        this.visitedNodes = 0;
        System.out.print("Nodes in visited order: ");
        for (Station vertex : nodesVisited) {
            this.visitedNodes++;
            System.out.print(vertex.getStationName() + " ");
        }
        System.out.println();
    }

    public int getVisitedNodes() {
        return visitedNodes;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder(String.format("Path from %s to %s: ", graph.getStation(startIndex), graph.getStation(endIndex)));
        resultString.append(nodesInPath).append(" with " + transfers).append(" transfers");
        return resultString.toString();
    }

}
