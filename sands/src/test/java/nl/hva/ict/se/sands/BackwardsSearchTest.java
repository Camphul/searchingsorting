package nl.hva.ict.se.sands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 100000;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();


    }
}