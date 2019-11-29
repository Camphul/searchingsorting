package nl.hva.ict.se.sands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}