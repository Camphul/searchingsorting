package nl.hva.ict.se.sands;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HuffmanCompression {
    private final String text;
    private final int MAX_CHARACTERS = 256;
    private Node compressionTree;
    private Map<Character, String> encodingMap;

    public static void main(String[] args) {
        HuffmanCompression huffmanCompression = new HuffmanCompression("ABRACADABRA!");

    }

    public HuffmanCompression(String text) {
        this.text = text;
        initialize();
    }

    public HuffmanCompression(InputStream input) {
        Scanner sc = new Scanner(input);
        sc.useDelimiter("\\Z"); // EOF marker
        text = sc.next();
        initialize();
    }

    private void initialize() {
        Node[] nodes = findCharacterWeights();
        this.compressionTree = buildTree(nodes)[0];
        printTree();
        this.encodingMap = new HashMap<>();
        getCodes(this.encodingMap, this.compressionTree, "");
        System.out.println("Encoding map:");
        System.out.println(this.encodingMap);
    }

    /**
     * Debug method to print out tree.
     */
    private void printTree() {
        System.out.println("Tree:");
        printTree(this.compressionTree);
        System.out.println();
    }

    /**
     * Debug method to print out tree.
     * @param node
     */
    private void printTree(Node node) {
        System.out.println("[Weight: " + node.getWeight() + "]");
        if(node.getLeft() != null && node.getRight() != null) {
            System.out.println("[LEFT]");
            printTree(node.getLeft());
            System.out.println("[RIGHT]");
            printTree(node.getRight());
        }
        if(node.getCharacter() != null) {
            System.out.println("[Character: " + String.valueOf(node.getCharacter())+ "]");
        }
    }

    private Node[] buildTree(Node[] nodes) {
        //Recursive exit function
        if(nodes.length == 1) {
            System.out.println("Exit condition reached.");
            return nodes;
        }
        Arrays.sort(nodes);
        Node left = nodes[0];
        Node right = nodes[1];
        Node parent = new Node(left, right);
        Node[] newNodes = new Node[nodes.length - 1];
        newNodes[0] = parent;
        for (int i = 2; i < nodes.length; i++) {
            newNodes[i-1] = nodes[i];
        }
        return buildTree(newNodes);
    }

    private Node[] findCharacterWeights() {
        char[] chars = this.text.toCharArray();
        int[] freq = new int[MAX_CHARACTERS];
        int alphabetSize = 0;
        for (int i = 0; i < chars.length; i++) {
            //Used to calculate alphabet size.
            if(freq[chars[i]] == 0) {
                alphabetSize++;
            }
            freq[chars[i]]++;
        }
        Node[] nodes = new Node[alphabetSize];
        int nodeCount = 0;
        for (int i = 0; i < freq.length; i++) {
            int frequency = freq[i];
            if(frequency > 0) {
                char ch = (char) i;
                nodes[nodeCount] = new Node(frequency, ch);
                nodeCount++;
            }
        }
        for (Node node : nodes) {
            System.out.println("Char: " + String.valueOf(node.getCharacter()) + " Weight: " + node.getWeight());
        }
        return nodes;
    }

    /**
     * Returns the compression ratio assuming that every characters in the text uses 8 bits.
     * @return the compression ratio.
     */
    public double getCompressionRatio() {
        int uncompressedSize = this.text.length() * 8;
        int compressedSize = 0;
        Node[] characterWeights = findCharacterWeights();
        for (Node characterNode : characterWeights) {
            Character ch = characterNode.getCharacter();
            String encoding = this.encodingMap.get(ch);
            int encodingLength = encoding.length();
            compressedSize += encodingLength * characterNode.getWeight();
        }
        System.out.println("Uncompressed size: " + uncompressedSize);
        System.out.println("Compressed size: " + compressedSize);
        return ((double)compressedSize/(double)uncompressedSize);
    }

    /**
     * Compresses the text that was provided to the constructor.
     * @return
     */
    public String compress() {
        return "";
    }

    /**
     * Returns the root of the compression tree.
     * @return the root of the compression tree.
     */
    Node getCompressionTree() {
        return this.compressionTree;
    }

    /**
     * Returns a Map<Character, String> with the character and the code that is used to encode it.
     * For "aba" this would result in: ['b' -> "0", 'a' -> "1"]
     * And for "cacbcac" this would result in: ['b' -> "00", 'a' -> "01", 'c' -> "1"]
     * @return the Huffman codes
     */
    Map<Character, String> getCodes() {
        return this.encodingMap;
    }

    /**
     * Recursively get codes by traversing the tree.
     * @param encodings encoding map.
     * @param root encoding node to check.
     * @param currentPath current encoding path.
     */
    private void getCodes(Map<Character,String> encodings, Node root, String currentPath) {
        if(root.isLeaf()) {
            encodings.put(root.getCharacter(), currentPath);
        } else {
            getCodes(encodings, root.getLeft(), currentPath + "0");
            getCodes(encodings, root.getRight(), currentPath + "1");
        }
    }


    private boolean isCharNode(Node node) {
        return node.getCharacter() != null;
    }

}
