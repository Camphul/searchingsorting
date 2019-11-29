package nl.hva.ss.remedial.textsearch;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 11/29/19
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("TextSearch Applicatie");
        System.out.println("Luca Camphuisen 500756672");
        System.out.println("Jeroen de Jong");
        String pattern = "hay";
        String search = "needleinahaystack";
        BoyerMoore boyerMoore = new BoyerMoore(pattern);
        int needle = boyerMoore.search(search);
        int expected = 9;
        System.out.println("Needle: " + needle);
        if(needle != expected) {
            System.err.println("needle did not meet expectation.");
        }
    }
}
