package xml;


/**
 * A simple example used in many tutorials.
 * 
 * Replicates the functionality of Map.Entry, but is perhaps simpler to use.
 *
 * @author Robert C. Duvall
 */
public class Pair<A, B> {
    // immutable instance variables
    // NOTE: these can be any two types, same or different
    private final A first;
    private final B second;


    /**
     * Create a pair directly from the given values
     */
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    // NOTE: provides getters, but not setters
    /**
     * Returns first value in pair
     */
    public A getFirst () {
        return first;
    }

    /**
     * Returns second value in pair
     */
    public B getSecond () {
        return second;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString () {
        return "(" + first + ", " + second + ")";
    }

    /**
     * Create pair with single, repeated value.
     */
    // NOTE: basic example to show generic method that could appear in any class whether or not it is generic
    public static <T> Pair<T, T> of (T value) {
        return new Pair<>(value, value);
    }


    // examples to show how to use this class
    public static void main (String[] args) {
        Pair<String, Integer> words = new Pair<>("Hello", 1);
        System.out.println(words);
        Pair<Integer, String> counts = new Pair<>(2, "Goodbye");
        System.out.println(counts);
        Pair<String, String> values = Pair.of("What");
        System.out.println(values);
    }
}
