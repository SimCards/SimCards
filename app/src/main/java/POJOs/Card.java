package POJOs;

/**
 * Created by Patrick on 2/25/2016.
 */
public class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Rank rank, Suit suit) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getValue() {
        return rank;
    }
}
