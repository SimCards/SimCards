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
        System.out.println(deckA.toString());
        deckA.reverse();
        System.out.println(deckA.toString());
        System.out.println(deckA.size());
        System.out.println(deckB.toString());
        System.out.println(deckB.size());

        //Deck deckC = Deck.merge(deckA, deckB, false);
        //System.out.println(deckC.size());

        //Deck deckC = Deck.initialize(false, DeckType.EMPTY);
        //deckB = Deck.initialize(false, DeckType.EMPTY);
        //Deck deckD = Deck.initialize(true, DeckType.EMPTY);
        Deck[] hands = deckA.distribute(52,100,RemainderStrategy.DISTRIBUTE_REMAINDERS,DealDirection.CLOCKWISE);
        //deckA.transfer(deckC, 3, TransferManner.TOP_TO_TOP_REVERSE);
        //deckA.transfer(deckC, 3, TransferManner.TOP_TO_TOP);
        System.out.println(hands[0].toString());
        System.out.println(hands[1].toString());
        System.out.println(hands[2].toString());
        System.out.println(deckA.toString());

        //deckC = Deck.initialize(false, DeckType.EMPTY);
        //deckA.transfer(deckC, 60, TransferManner.BOTTOM_TO_BOTTOM_REVERSE);
        //System.out.println(deckC.toString());
    }
}
