package POJOs;

/**
 * Created by Patrick on 2/25/2016.
 */
public class Card {
    private final Suit suit;
    private final Rank value;

    public Card(Suit su, Rank val) {
        this.suit = su;
        this.value = val;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getValue() {
        return value;
    }
}
