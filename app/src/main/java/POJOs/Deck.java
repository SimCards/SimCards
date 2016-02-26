package POJOs;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.simcards.game.Rank;

/**
 * Created by Patrick on 2/25/2016.
 */
public class Deck {

    ArrayList<Card> cards;
    int size;

    public Deck() {
        this.cards = new ArrayList<Card>();
        this.size = 0;
    }

    public void transfer(Deck dst, int num_cards, TransferManner manner) {
        int i;
        switch(manner) {
            case BOTTOM_TO_BOTTOM:

        }
    }

    public void transferIndex(Deck dst, int index, TransferManner manner) {
    }

    public static Deck initialize(boolean shuffled,DeckType type) {
        switch(type) {
            case STANDARD_52:
        }
    }

    public void shuffle() {

    }

    public void reverse() {

    }

    public static void merge(Deck d1, Deck d2, TransferManner manner) {

    }

    public Deck[] distribute(int num_decks, int cards_per_deck, RemainderStrategy strat, DealDirection direction) {

    }

    public void setComparator(CardComparator comp) {

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

