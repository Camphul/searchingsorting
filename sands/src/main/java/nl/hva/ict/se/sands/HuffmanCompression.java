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

    /**
     * Calculate tree and build encoding map.
     */
    private void initialize() {
        Node[] nodes = findCharacterWeights();
        this.compressionTree = buildTree(nodes)[0];
        //printTree();
        this.encodingMap = new HashMap<>();
        getCodes(this.encodingMap, this.compressionTree, "");
        // System.out.println("Encoding map:");
        //System.out.println(this.encodingMap);
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
     * Debug method to print out entire tree.
     * @param node root node.
     */
    private void printTree(Node node) {
        System.out.println("[Weight: " + node.getWeight() + "]");
        if(!node.isLeaf()) {
            System.out.println("[LEFT]");
            printTree(node.getLeft());
            System.out.println("[RIGHT]");
            printTree(node.getRight());
        } else {
            System.out.println("[Character: " + String.valueOf(node.getCharacter())+ "]");
        }
    }

    /**
     * Builds an sorts tree using recursion.
     * @param nodes array of nodes to build into a tree.
     * @return array of length one when completed.
     */
    private Node[] buildTree(Node[] nodes) {
        //Recursive exit function
        if(nodes.length == 1) {
            return nodes;
        }
        //Sort on weights/alphabet/etc...
        Arrays.sort(nodes);
        // We do not have to check which one goes left since it's being sorted in the compareTo of the node.
        Node left = nodes[0];
        Node right = nodes[1];

        Node parent = new Node(left, right);
        //Place new parent node in new array and copy over un-processed nodes.
        Node[] newNodes = new Node[nodes.length - 1];
        newNodes[0] = parent;
        for (int i = 2; i < nodes.length; i++) {
            newNodes[i-1] = nodes[i];
        }
        return buildTree(newNodes);
    }

    /**
     * Builds an array of character nodes with their weights. Unsorted.
     * @return character nodes with weights array.
     */
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
                //Creates nodes.
                nodes[nodeCount] = new Node(frequency, ch);
                nodeCount++;
            }
        }
        return nodes;
    }

    /**
     * Returns the compression ratio assuming that every characters in the text uses 8 bits.
     * @return the compression ratio.
     */
    public double getCompressionRatio() {
        int uncompressedSize = this.text.length() * 8;
        //Since the result of compress is the entire encoded string we can simply grab the length.
        int compressedSize = this.compress().length();
        System.out.println("Uncompressed size: " + uncompressedSize);
        System.out.println("Compressed size: " + compressedSize);
        return ((double)compressedSize/(double)uncompressedSize);
    }

    /**
     * Compresses the text that was provided to the constructor.
     * @return
     */
    public String compress() {
        char[] chars = this.text.toCharArray();
        String compressed = "";
        //Loop through chars
        for (char aChar : chars) {
            //Get specified encoding and append compressed string.
            compressed += this.encodingMap.get(aChar);
        }
        return compressed;
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

}
