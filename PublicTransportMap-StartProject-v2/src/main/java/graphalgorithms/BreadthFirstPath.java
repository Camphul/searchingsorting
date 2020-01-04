package graphalgorithms;

import model.Station;
import model.TransportGraph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 1/4/20
 */
public class BreadthFirstPath extends AbstractPathSearch {

    public BreadthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
        Queue<Integer> q = new LinkedList<>();
        this.marked[this.startIndex] = true;
        q.add(this.startIndex);
        this.nodesVisited.add(this.graph.getStation(this.startIndex));
        while (q.size() != 0) {
            int vertex = q.poll();
            for (Integer adjacentVertex : this.graph.getAdjacentVertices(vertex)) {
                if (!marked[adjacentVertex]) {
                    marked[adjacentVertex] = true;
                    edgeTo[adjacentVertex] = vertex;
                    Station station = this.graph.getStation(adjacentVertex);
                    this.nodesVisited.add(station);
                    q.add(adjacentVertex);
                }

            }
        }
        pathTo(this.endIndex);
    }
}
