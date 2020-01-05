package controller;

import graphalgorithms.BreadthFirstPath;
import graphalgorithms.DepthFirstPath;
import graphalgorithms.DijkstraShortestPath;
import model.TransportGraph;

public class TransportGraphLauncher {

    public static void main(String[] args) {
        assignmentB();
    }

    public static void assignmentB() {
        String[] redLine = {"Red", "metro", "Haven", "Marken", "Steigerplein", "Centrum", "Meridiaan", "Dukdalf", "Oostvaarders"};
        double[] redWeights = {4.5, 4.7, 6.1, 3.5, 5.4, 5.6};
        String[] blueLine = {"Blue", "metro", "Trojelaan", "Coltrane Cirkle", "Meridiaan", "Robijnpark", "Violetplantsoen"};
        double[] blueWeights = {6, 5.3, 5.1, 3.3};
        String[] purpleLine = {"Purple", "metro", "Grote Sluis", "Grootzeil", "Coltrane Cirkel", "Centrum", "Swingstraat"};
        double[] purpleWeights = {6.2, 5.2, 3.8, 3.6};
        String[] greenLine = {"Green", "metro", "Ymeerdijk", "Trojelaan", "Steigerplein", "Swingstraat", "Bachgracht", "Nobelplein"};
        double[] greenWeights = {5, 3.7, 6.9, 3.9, 3.4};
        String[] yellowLine = {"Yellow", "bus", "Grote Sluis", "Ymeerdijk", "Haven", "Nobelplein", "Violetplantsoen", "Oostvaarders", "Grote Sluis"};
        double[] yellowWeights = {26, 19, 37, 25, 22, 28};
        TransportGraph transportGraph = new TransportGraph.Builder()
                .addLine(redLine, redWeights).addLine(blueLine, blueWeights).addLine(purpleLine, purpleWeights).addLine(greenLine, greenWeights).addLine(yellowLine, yellowWeights)
                .buildStationSet().addLinesToStations().buildConnections().build();
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

        DijkstraShortestPath dsp = new DijkstraShortestPath(transportGraph, "Violetplantsoen", "Haven");
        dsp.search();
        System.out.println(dsp);
        dsp.printNodesInVisitedOrder();
        System.out.println();
        System.out.println("Total weight: " + dsp.getTotalWeight());
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
