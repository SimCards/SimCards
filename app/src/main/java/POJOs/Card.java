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

    public Rank getRank() {
        return rank;
    }
	
	public int hashCode() {
		return (suit.ordinal()+","+rank.ordinal()).hashCode();
	}
	
	public boolean equals(Object c) {
		return equals((Card)c);
	}
	
	public boolean equals(Card c) {
		return suit==c.getSuit() && rank==c.getRank();
	}
}
