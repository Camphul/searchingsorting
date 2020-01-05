package graphalgorithms;

import model.Station;
import model.TransportGraph;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 1/4/20
 */
public class DepthFirstPath extends AbstractPathSearch {

    public DepthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
        depthFirstSearch(this.startIndex);
        pathTo(this.endIndex);
    }

    public void depthFirstSearch(int vertex) {
        this.marked[vertex] = true;
        for (Integer adjacentVertex : this.graph.getAdjacentVertices(vertex)) {
            if (!this.marked[adjacentVertex]) {
                this.edgeTo[adjacentVertex] = vertex;
                Station station = this.graph.getStation(adjacentVertex);
                this.nodesVisited.add(station);
                depthFirstSearch(adjacentVertex);
            }
        }
    }
}
