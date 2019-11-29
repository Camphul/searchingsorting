package nl.hva.ict.se.sands;

/**
 * NOTE: You are NOT allowed to implement the Serializable interface!!
 */
public class Node implements Comparable<Node> {
    private Node left;
    private Node right;
    private int weight;
    private Character character;

    public Node(int weight, Character c) {
        this.weight = weight;
        this.character = c;
    }

    public Node(Node left, Node right) {
        this.weight = left.weight + right.weight;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Node o) {
        int weightCompare = this.weight - o.getWeight();
        //When the comparison comes out as equal
        if(weightCompare == 0) {
            //compare alphabetically when both leafes
            if(isLeaf() && o.isLeaf()) {
                return o.getCharacter().compareTo(getCharacter());
            } else if(isLeaf() && !o.isLeaf()) {
                //Sort to left as leafes go first.
                return 1;
            } else if(!isLeaf() && o.isLeaf()) {
                //Sort to right as nodes go second
                return -1;
            }
        }
        //
        return weightCompare;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public int getWeight() {
        return weight;
    }

    public Character getCharacter() {
        return character;
    }

    /**
     * Custom method to determine if char node.
     * @return
     */
    public boolean isLeaf() {
        return this.getCharacter() != null;
    }
}
