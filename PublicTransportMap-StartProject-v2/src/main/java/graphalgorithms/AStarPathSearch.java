package graphalgorithms;

import model.Connection;
import model.IndexMinPQ;
import model.Station;
import model.TransportGraph;

/**
 * A* search algorithm. Based from the Dijkstra implementation but added the location heuristic.
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
        Station startStation = this.graph.getStation(this.startIndex);
        Station endStation = this.graph.getStation(this.endIndex);
        this.distTo[this.startIndex] = startStation.getLocation().travelTime(endStation.getLocation());
        this.pq.insert(startIndex, distTo[this.startIndex]);
        while (!this.pq.isEmpty()) {
            int vertex = this.pq.delMin();
            this.nodesVisited.add(this.graph.getStation(vertex));
            for (Integer adjacentVertex : this.graph.getAdjacentVertices(vertex)) {
                Connection connection = this.graph.getConnection(vertex, adjacentVertex);
                //Now relax the edge/connection
                double weight = connection.getWeight();
                //TODO ADD HEURISTICS
                this.nodesVisited.add(this.graph.getStation(adjacentVertex));
                if (this.distTo[adjacentVertex] > distTo[vertex] + weight) {
                    this.distTo[adjacentVertex] = distTo[vertex] + weight;
                    this.edgeTo[adjacentVertex] = vertex;
                    if (this.pq.contains(adjacentVertex)) {
                        this.pq.decreaseKey(adjacentVertex, this.distTo[adjacentVertex]);
                    } else {
                        this.pq.insert(adjacentVertex, this.distTo[adjacentVertex]);
                    }
                }
            }
        }
        pathTo(this.endIndex);
    }

    /**
     * Get the heuristic value for a certain vertex.
     * The travel time for the vertex to reach the end vertex.
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
