package POJOs;

/**
 * Created by Patrick on 3/15/2016.
 */
public class DeckTest {



    public static void main(String[] arg) {
        Deck deckA;
        Deck deckB;
        deckA = Deck.initialize(true, DeckType.STANDARD_52);
        deckB = Deck.initialize(false, DeckType.WITH_JOKERS);
        System.out.print(deckA.toString());
        System.out.print(deckB.toString());
    }
}
