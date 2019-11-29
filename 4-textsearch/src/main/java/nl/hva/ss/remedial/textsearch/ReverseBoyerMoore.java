package nl.hva.ss.remedial.textsearch;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 11/29/19
 */
public class ReverseBoyerMoore {

    private final String pattern;

    private ReverseBoyerMoore(String pattern) {
        this.pattern = pattern;
    }

    public static ReverseBoyerMoore pattern(String pattern) {
        return new ReverseBoyerMoore(pattern);
    }
}
