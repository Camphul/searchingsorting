package nl.hva.ict.se.sands;

public class BackwardsSearch {

    private int comparisonCount = 0;
    private int R;     // the radix
    private int[] left;     // the bad-character skip array

    private String needle;  // store the pattern as a character array
    private String haystack;
    /**
     * Returns index of the right most location where <code>needle</code> occurs within <code>haystack</code>. Searching
     * starts at the right end side of the text (<code>haystack</code>) and proceeds to the first character (right side).
     * @param needle the text to search for.
     * @param haystack the text which might contain the <code>needle</code>.
     * @return -1 if the <code>needle</code> is not found and otherwise the right most index of the first
     * character of the <code>needle</code>.
     */
    int findLocation(String needle, String haystack) {
        comparisonCount = 0;
        this.needle = needle;
        this.haystack = haystack;

        this.R = 256;

        // preprocess pattern ----------------------------------------
        // position of rightmost occurrence of c in the pattern
        left = new int[R];
        for (int c = R-1; c > 0; c--)
            left[c] = needle.length() -1 ;
        for (int j = needle.length() - 1; j > 0; j--)
            left[needle.charAt(j)] = j;

        // search ----------------------------------------------------
        int m = needle.length();
        int n = haystack.length();
        int skip = 0;
        for (int i = n - m; i >= m; i -= skip) {
            System.out.println("skip was " + skip + ", now looking at text index " + i);
            skip = 0;
            for (int j = 0; j < m; j++) {
                this.comparisonCount++;
                if (needle.charAt(j) != haystack.charAt(i+j)) {
                    skip = Math.max(1, j + left[haystack.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) return i;    // found
        }
        return -1; // not found
    }

    public BackwardsSearch() {
    }

    /**
     * Returns the number of character compared during the last search.
     * @return the number of character comparisons during the last search.
     */
    int getComparisonsForLastSearch() {
        return this.comparisonCount;
    }

}
