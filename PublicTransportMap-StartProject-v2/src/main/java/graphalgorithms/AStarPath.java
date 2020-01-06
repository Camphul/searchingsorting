package graphalgorithms;

import model.Connection;
import model.IndexMinPQ;
import model.Station;
import model.TransportGraph;

/**
 * A* search algorithm. Based from the Dijkstra implementation but added the location heuristic.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 1/5/20
 */
public class AStarPath extends AbstractPathSearch {
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    private double[] distTo;
    private IndexMinPQ<Double> priorityQueue;

    public AStarPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
        int numVertices = graph.getNumberOfStations();
        this.distTo = new double[numVertices];
        this.priorityQueue = new IndexMinPQ(numVertices);
        for (int i = 0; i < numVertices; i++) {
            distTo[i] = INFINITY;
        }
    }

    @Override
    public void search() {
        this.distTo[this.startIndex] = 0;
        //Start at station
        this.priorityQueue.insert(startIndex, getHeuristic(this.startIndex));
        while (!this.priorityQueue.isEmpty()) {
            int vertex = this.priorityQueue.delMin();
            //Check if end is reached.
            if (vertex == this.endIndex) {
                pathTo(vertex);
                return;
            }
            //Mark as visited.
            markVisited(vertex);
            for (Integer adjacentVertex : this.graph.getAdjacentVertices(vertex)) {
                Connection connection = this.graph.getConnection(vertex, adjacentVertex);
                //Now relax the edge/connection
                markVisited(vertex);
                double heuristic = getHeuristic(adjacentVertex);
                double tentative = this.distTo[vertex] + connection.getWeight();
                //Check if adjacent station is closer than any previous one.
                if (tentative + heuristic < this.distTo[adjacentVertex] + heuristic) {
                    this.edgeTo[adjacentVertex] = vertex;
                    this.distTo[adjacentVertex] = tentative;
                    if (this.priorityQueue.contains(adjacentVertex)) {
                        this.priorityQueue.decreaseKey(adjacentVertex, tentative + heuristic);
                    } else {
                        this.priorityQueue.insert(adjacentVertex, tentative + heuristic);
                    }
                }
            }
        }
        pathTo(this.endIndex);
    }

    /**
     * Get the heuristic value for a certain vertex.
     * The travel time for the vertex to reach the end vertex.
     *
     * @param vertex
     * @return
     */
    private double getHeuristic(int vertex) {
        Station station = this.graph.getStation(vertex);
        Station endStation = this.graph.getStation(this.endIndex);
        return station.getLocation().travelTime(endStation.getLocation());
    }

    @Override
    public boolean hasPathTo(int vertex) {
        return this.distTo[vertex] < INFINITY;
    }

    public double getTotalWeight() {
        //Get all connections in the vertices path and add all weights together
        double totalWeight = 0;
        for (int i = 0; i < this.verticesInPath.size() - 1; i++) {
            int from = this.verticesInPath.get(i);
            int to = this.verticesInPath.get(i + 1);
            Connection connection = this.graph.getConnection(from, to);
            totalWeight += connection.getWeight();
        }
        return totalWeight;
    }
}
