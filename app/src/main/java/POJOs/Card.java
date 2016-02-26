package POJOs;

/**
 * Created by Patrick on 2/25/2016.
 */
public class Card {
    private final Suit suit;
    private final Value value;

    public Card(Suit su, Value val) {
        this.suit = su;
        this.value = val;
    }

    public Suit getSuit() {
        return suit;
    }

    public Value getValue() {
        return value;
    }
}
