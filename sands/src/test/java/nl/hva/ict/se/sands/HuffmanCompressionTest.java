package nl.hva.ict.se.sands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HuffmanCompressionTest {
    HuffmanCompression compressor;

    @BeforeEach
    public void setup() {
        compressor = new HuffmanCompression(getClass().getResourceAsStream("/edu/princeton/cs/algs4/Huffman.java"));
    }

    @Test
    public void checkWeightSimple() {
        compressor = new HuffmanCompression("aba");

        Node compressionTree = compressor.getCompressionTree();

        assertEquals(3, compressionTree.getWeight());

        Node left = compressionTree.getLeft();
        Node right = compressionTree.getRight();
        assertEquals(1, left.getWeight());
        assertEquals(2, right.getWeight());
    }

    @Test
    public void checkUniqueCharacter() {
        // Handle Linux/Mac and Windows end-of-line characters, 86 and 87 are both ok.
        int numberOfChars = compressor.getCodes().size();
        assertTrue(numberOfChars == 86 || numberOfChars == 87, "You appear to have some very strange end-of-line configuration on your machine!");
    }

    @Test
    public void checkSimpleCompressionRatio() {
        compressor = new HuffmanCompression("aba");

        assertEquals(0.125,compressor.getCompressionRatio(), 0.0001);
    }

    /**
     * Custom written test to check if the encoding map is correct.
     * This also validates the tree since it can't give a correct encoding if the tree is made incorrect.
     */
    @Test
    public void checkEncoding() {
        compressor = new HuffmanCompression("ABRACADABRA!");
        Map<Character, String> expectedEncoding = new HashMap<>();
        //Switch up D and ! due to character comparisons
        expectedEncoding.put('A', "0");
        expectedEncoding.put('B', "111");
        expectedEncoding.put('C', "1011");
        expectedEncoding.put('D', "1010");
        expectedEncoding.put('R', "110");
        expectedEncoding.put('!', "100");
        System.out.println("Real: " + compressor.getCodes());
        System.out.println("Expected: " + expectedEncoding);
        assertTrue(expectedEncoding.equals(compressor.getCodes()));
    }

    /**
     * Custom test to validate compression algorithm.
     */
    @Test
    public void checkCompression() {
        compressor = new HuffmanCompression("ABRACADABRA!");
        String compressed = compressor.compress();
        String expected = "0111110010110101001111100100";
        assertEquals(expected, compressed);
    }

}
