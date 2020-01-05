package controller;

import graphalgorithms.AStarPathSearch;
import graphalgorithms.BreadthFirstPath;
import graphalgorithms.DepthFirstPath;
import graphalgorithms.DijkstraShortestPath;
import model.Station;
import model.TransportGraph;

public class TransportGraphLauncher {

    public static void main(String[] args) {
        assignmentBC();
    }

    /**
     * Assignment b and c
     */
    public static void assignmentBC() {
        String[] redLine = {"Red", "metro", "Haven", "Marken", "Steigerplein", "Centrum", "Meridiaan", "Dukdalf", "Oostvaarders"};
        double[] redWeights = {4.5, 4.7, 6.1, 3.5, 5.4, 5.6};
        String[] blueLine = {"Blue", "metro", "Trojelaan", "Coltrane Cirkel", "Meridiaan", "Robijnpark", "Violetplantsoen"};
        double[] blueWeights = {6, 5.3, 5.1, 3.3};
        String[] purpleLine = {"Purple", "metro", "Grote Sluis", "Grootzeil", "Coltrane Cirkel", "Centrum", "Swingstraat"};
        double[] purpleWeights = {6.2, 5.2, 3.8, 3.6};
        String[] greenLine = {"Green", "metro", "Ymeerdijk", "Trojelaan", "Steigerplein", "Swingstraat", "Bachgracht", "Nobelplein"};
        double[] greenWeights = {5, 3.7, 6.9, 3.9, 3.4};
        String[] yellowLine = {"Yellow", "bus", "Grote Sluis", "Ymeerdijk", "Haven", "Nobelplein", "Violetplantsoen", "Oostvaarders", "Grote Sluis"};
        double[] yellowWeights = {26, 19, 37, 25, 22, 28};
        TransportGraph transportGraph = new TransportGraph.Builder()
                .addLine(redLine, redWeights).addLine(blueLine, blueWeights).addLine(purpleLine, purpleWeights).addLine(greenLine, greenWeights).addLine(yellowLine, yellowWeights)
                .locate("Grote Sluis", 2, 3).locate("Oostvaarders", 0,11)
                .locate("Grootzeil", 4, 6).locate("Dukdalf", 3, 10)
                .locate("Ymeerdijk", 9,0).locate("Trojelaan", 9,3)
                .locate("Coltrane Cirkel", 7, 6).locate("Meridiaan", 6, 9)
                .locate("Robijnpark", 6, 12).locate("Violetplantsoen", 5, 14)
                .locate("Haven", 14, 1).locate("Marken", 12, 3)
                .locate("Steigerplein", 10, 5).locate("Centrum", 8, 8)
                .locate("Swingstraat", 10, 9).locate("Bachgracht", 11, 11)
                .locate("Nobelplein", 12, 13)
                .buildStationSet().addLinesToStations().buildConnections().build();

        System.out.println("Dijkstra:");
        DijkstraShortestPath dsp = new DijkstraShortestPath(transportGraph, "Trojelaan", "Meridiaan");
        dsp.search();
        System.out.println(dsp);
        dsp.printNodesInVisitedOrder();
        System.out.println();
        System.out.println("Total weight: " + dsp.getTotalWeight());

        System.out.println("A Star:");
        AStarPathSearch asp = new AStarPathSearch(transportGraph, "Trojelaan", "Meridiaan");
        asp.search();
        System.out.println(asp);
        asp.printNodesInVisitedOrder();
        System.out.println();
        System.out.println("Total weight: " + asp.getTotalWeight());
        measure(transportGraph);
        /*DepthFirstPath dfpTest = new DepthFirstPath(transportGraph, "Nobelplein", "Coltrane Cirkel");
        dfpTest.search();
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();*/

//        Uncommented to test the BreadthFirstPath algorithm
        /*BreadthFirstPath bfsTest = new BreadthFirstPath(transportGraph, "Violetplantsoen", "Haven");
        bfsTest.search();
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();*/

    }

    public static void measure(TransportGraph graph) {
        for (Station station : graph.getStationList()) {
            String from = station.getStationName();
            for (Station station1 : graph.getStationList()) {
                String to = station1.getStationName();
                if(!from.equals(to)) {
                    measure(graph, from, to);
                }
            }
        }
    }

    public static void measure(TransportGraph graph, String from, String to) {
        //System.out.println("From: " + from + " To: " + to);
        DijkstraShortestPath dsp = new DijkstraShortestPath(graph, from, to);
        dsp.search();
        AStarPathSearch asp = new AStarPathSearch(graph, from, to);
        asp.search();
        if(asp.getVisitedNodes() < dsp.getVisitedNodes()) {
            System.out.println("AStar Was more efficient: " + asp.getVisitedNodes() + " nodes visited instead of Dijkstra " + dsp.getVisitedNodes() + " node visits");
            System.out.println("From: " + from + " To: " + to);
            System.out.println("Dijkstra:");
            System.out.println(dsp);
            System.out.println("Weight: " + dsp.getTotalWeight());
            System.out.println("AStar:");
            System.out.println(asp);
            System.out.println("Weight: " + asp.getTotalWeight());
        }
    }

    public static void assignmentA() {
        String[] redLine = {"red", "metro", "A", "B", "C", "D"};
        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

        TransportGraph transportGraph = new TransportGraph.Builder()
                .addLine(redLine).addLine(blueLine).addLine(greenLine).addLine(yellowLine)
                .buildStationSet().addLinesToStations().buildConnections().build();
//        Uncomment to test the builder:
        System.out.println(transportGraph);

//        Uncommented to test the DepthFirstPath algorithm
        DepthFirstPath dfpTest = new DepthFirstPath(transportGraph, "E", "J");
        dfpTest.search();
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();

//        Uncommented to test the BreadthFirstPath algorithm
        BreadthFirstPath bfsTest = new BreadthFirstPath(transportGraph, "E", "J");
        bfsTest.search();
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();

        //TODO: Make an overview of all the path from all stations to all other stations with the least connections.
    }
}
