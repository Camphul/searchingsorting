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
public class AStarPathSearch extends AbstractPathSearch {
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public AStarPathSearch(TransportGraph graph, String start, String end) {
        super(graph, start, end);
        int numVertices = graph.getNumberOfStations();
        this.distTo = new double[numVertices];
        this.pq = new IndexMinPQ(numVertices);
        for (int i = 0; i < numVertices; i++) {
            distTo[i] = INFINITY;
        }
    }

    @Override
    public void search() {
        this.distTo[this.startIndex] = 0;
        this.pq.insert(startIndex, getHeuristic(startIndex));
        while (!this.pq.isEmpty()) {
            int vertex = this.pq.delMin();
            if (vertex == endIndex) {
                pathTo(vertex);
                return;
            }
            markVisited(vertex);
            for (Integer adjacentVertex : this.graph.getAdjacentVertices(vertex)) {
                Connection connection = this.graph.getConnection(vertex, adjacentVertex);
                //Now relax the edge/connection
                markVisited(vertex);
                double heuristic = getHeuristic(adjacentVertex);
                double tentative = distTo[vertex] + connection.getWeight();
                if (tentative + heuristic < distTo[adjacentVertex] + heuristic) {
                    edgeTo[adjacentVertex] = vertex;
                    distTo[adjacentVertex] = tentative;
                    if (pq.contains(adjacentVertex)) {
                        pq.decreaseKey(adjacentVertex, tentative + heuristic);
                    } else {
                        pq.insert(adjacentVertex, tentative + heuristic);
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
