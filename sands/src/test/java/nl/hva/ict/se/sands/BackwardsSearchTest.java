package nl.hva.ict.se.sands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.*;

public class BackwardsSearchTest {
    protected BackwardsSearch searchEngine;

    @BeforeEach
    public void setup() {
        searchEngine = new BackwardsSearch();
    }

    @Test
    public void findSingleOccurrence() {
        int index = searchEngine.findLocation("needle", "whereistheneedleinthishaystack");

        assertEquals("whereisthe".length(), index);
    }

    @Test
    public void cantFindOccurrence() {
        int index = searchEngine.findLocation("needle", "thereisnothinginthishaystack");

        assertEquals(-1, index);
    }

    @Test
    public void simpleCharacterCount() {
        searchEngine.findLocation("needle", "whereistheneedle");

        assertEquals(6, searchEngine.getComparisonsForLastSearch());
    }

    @Test
    public void compareWithLargeText() {
        System.out.println("\nLARGE TEXT TEST----------------------------------------------------------------------");
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10000;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        buffer.insert(6000, "needle");
        String generatedString = buffer.toString();


        System.out.println("pattern found at index " + searchEngine.findLocation("needle", generatedString));
        System.out.println("inverse algorithm did " + searchEngine.getComparisonsForLastSearch() + " comparisons");
        BoyerMoore bm = new BoyerMoore("needle");
        System.out.println("pattern found at index " + bm.search(generatedString));
        System.out.println("original algorithm did " + bm.getComparisonCount() + " comparisons");

        Assertions.assertTrue(searchEngine.getComparisonsForLastSearch() <  bm.getComparisonCount());

        System.out.println("END OF LARGE TEXT TEST---------------------------------------------------------------\n");


    }
}