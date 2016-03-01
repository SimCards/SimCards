package POJOs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Patrick on 2/25/2016.
 */
public class Deck {

    private ArrayList<Card> cards;
    private int size;
    private CardComparator comp;

    private Deck() {
        this.cards = new ArrayList<Card>();
        this.size = 0;
    }

    /**
     * Transfers num_cards cards from this deck to the one specified by dst.
     * Where within the deck the cards are taken from and inserted are determined
     * by the manner parameter. There is currently no default behavior.
     * Num_cards must be greater than 0.
     *
     * @param dst
     * @param num_cards
     * @param manner
     */
    public void transfer(Deck dst, int num_cards, TransferManner manner) {
        int i;
        if (num_cards < 1 || dst==null || manner==null){return;}
        switch(manner) {
            case TOP_TO_TOP:
                for(i=0;i<num_cards;i++) {
                    dst.cards.add(cards.remove(cards.size()-(num_cards)));
                }
                dst.size = dst.cards.size();
                this.size = cards.size();
                break;
            case BOTTOM_TO_BOTTOM:
                for(i=0;i<num_cards;i++) {
                    dst.cards.add(0,cards.remove((num_cards-1)-i));
                }
                dst.size = dst.cards.size();
                this.size = cards.size();
            case BOTTOM_TO_BOTTOM_REVERSE:
                for(i=0;i<num_cards;i++) {
                    dst.cards.add(0,cards.remove(0));
                }
                dst.size = dst.cards.size();
                this.size = cards.size();
                break;
            case BOTTOM_TO_TOP:
                for(i=0;i<num_cards;i++) {
                    dst.cards.add(cards.remove((num_cards-1)-i));
                }
                dst.size = dst.cards.size();
                this.size = cards.size();
                break;
            case TOP_TO_BOTTOM:
                for(i=0;i<num_cards;i++) {
                    dst.cards.add(0,cards.remove(cards.size()-num_cards));
                }
                dst.size = dst.cards.size();
                this.size = cards.size();
                break;
            case TOP_TO_TOP_REVERSE:
                for(i=0;i<num_cards;i++) {
                    dst.cards.add(cards.remove(cards.size()-1));
                }
                dst.size = dst.cards.size();
                this.size = cards.size();
                break;
            default:
                break;
        }
    }


    public void transferIndex(Deck dst, int index, boolean top) {
        if (index > cards.size()-1) {return;}
        if (top) {
            dst.cards.add(cards.remove(index));
            dst.size = dst.cards.size();
            this.size = cards.size();
        } else {
            dst.cards.add(0,cards.remove(index));
            dst.size = dst.cards.size();
            this.size = cards.size();
        }
    }

    /**
     *
     * @param shuffled
     * @param type
     * @return A new deck object
     */
    public static Deck initialize(boolean shuffled, DeckType type) {
        Deck newDeck = new Deck();
        int i,j;
        switch (type) {
            case EMPTY:
                break;
            case STANDARD_52:
                for(i=0;i<4;i++) {
                    for(j=0;j<13;i++) {
                        newDeck.cards.add(new Card(Rank.values()[j],Suit.values()[i]));
                    }
                }
                newDeck.size = newDeck.cards.size();
                break;
            case WITH_JOKERS:
                for(i=0;i<4;i++) {
                    for(j=0;j<13;i++) {
                        newDeck.cards.add(new Card(Rank.values()[j],Suit.values()[i]));
                    }
                }
                newDeck.cards.add(new Card(Rank.JOKER,Suit.JOKER));
                newDeck.cards.add(new Card(Rank.JOKER,Suit.JOKER));
                newDeck.size = newDeck.cards.size();
                break;
            default:
                break;
        }
        if (shuffled) {
            newDeck.shuffle();
        }
        return newDeck;
    }

    /**
     * Shuffles the Card elements within their containing array class
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Reverses the order of cards in the deck
     */
    public void reverse() {
        Collections.reverse(cards);
    }

    public static Deck merge(Deck d1, Deck d2, TransferManner manner) {
        if (d1 == null || d2 == null || manner == null) {return null;}
        Deck newDeck = new Deck();
        newDeck.cards.addAll(d1.cards);
        newDeck.cards.addAll(d2.cards);
        return newDeck;
    }

    public Deck[] distribute(int num_decks, int cards_per_deck, RemainderStrategy strat, DealDirection direction) {
        Deck[] decks = new Deck[num_decks];
        if (direction == DealDirection.CLOCKWISE) {
            while (cards.size() >= num_decks) {
                for (int i = 0; i < num_decks; i++) {
                    decks[i].cards.add(cards.remove(0));
                }
            }
        } else {
            while (cards.size() >= num_decks) {
                for (int i = num_decks-1; i>=0; i++) {
                    decks[i].cards.add(cards.remove(0));
                }
            }
        }
        if (strat == RemainderStrategy.DISTRIBUTE_REMAINDERS) {
            if (direction == DealDirection.CLOCKWISE) {
                for (int i = 0; i < num_decks && cards.size()>0; i++) {
                    decks[i].cards.add(cards.remove(0));
                }
            } else {
                for (int i = num_decks-1; i>=0 && cards.size()>0; i++) {
                    decks[i].cards.add(cards.remove(0));
                }
            }
        }
        return decks;
    }

    public void setComparator(CardComparator comp) {
        this.comp = comp;
    }

    public void order() {

    }

    public void order(CardComparator comp) {

    }

    public void setEvaluator(CardEvaluator eval) {

    }

    public int pointValue() {

    }

    public int pointValue(CardEvaluator eval) {

    }

    public Card getMax() {

    }

    public Card getMin() {

    }

    public Card getTop() {

    }

    public Card getBottom() {

    }

    public Card removeTop() {

    }

    public Card removeBottom() {

    }

    public Card get(int index) {

    }

    public Card remove(int index) {

    }

    public List<Card> getBulk(int num_cards) {

    }

    public List<Card> removeBulk(int num_cards) {

    }

    public void insert(int index, Card card) {

    }

    public void insert(int index, List<Card> cards) {

    }

    public int size() {

    }

    public int countRank(Rank rank) {

    }

    public int countSuit(Suit suit) {

    }

    public int countRankSuit(Rank rank, Suit suit) {

    }
}