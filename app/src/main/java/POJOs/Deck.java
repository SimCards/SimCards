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
    private CardEvaluator eval;

    private Deck() {
        this.cards = new ArrayList<Card>();
        this.size = 0;
    }

    /**
     * Takes in a card and adds it to the top of the deck (index size)
     * @param card
     */
    public void addTop(Card card) {
        this.cards.add(card);
        this.size++;
    }

    /**
     * Takes in a card and adds it to the bottom of the deck (index 0)
     * @param card
     */
    public void addBottom(Card card) {
        this.cards.add(0,card);
        this.size++;
    }

    public Card removeTop() {
        if (size == 0) {return null;}
        this.size--;
        return cards.remove(size);
    }

    public Card removeBottom() {
        if (size == 0) {return null;}
        this.size--;
        return cards.remove(0);
    }

    public Card get(int index) {
        if (index >= size) {return null;}
        return cards.get(index);
    }

    public Card remove(int index) {
        if (index >= size) {return null;}
        size--;
        return cards.remove(index);
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
        if (this.size() < num_cards) { num_cards = size; }
        switch(manner) {

            case TOP_TO_TOP:

                for(i=0;i<num_cards;i++) {
                    dst.addTop(cards.remove(cards.size() - (num_cards)));
                }

                break;

            case BOTTOM_TO_BOTTOM:

                for(i=0;i<num_cards;i++) {
                    dst.addBottom(cards.remove((num_cards - 1) - i));
                }

                break;

            case BOTTOM_TO_BOTTOM_REVERSE:

                for(i=0;i<num_cards;i++) {
                    dst.addBottom(cards.remove(0));
                }

                break;

            case BOTTOM_TO_TOP:

                for(i=0;i<num_cards;i++) {
                    dst.addTop(cards.remove((num_cards - 1) - i));
                }

                break;

            case TOP_TO_BOTTOM:

                for(i=0;i<num_cards;i++) {
                    dst.addBottom(cards.remove(cards.size()-num_cards));
                }

                break;

            case TOP_TO_TOP_REVERSE:

                for(i=0;i<num_cards;i++) {
                    dst.addTop(cards.remove(cards.size()-1));
                }

                break;

            default:
                break;
        }

        //Resize based on the number of cards that were moved
        if (size <= num_cards) {
            size = 0;
        } else {
            size-= num_cards;
        }
    }


    /**
     * Transfers a card from the given index in this deck to dst. If top is set to true
     * the card will be transfered to the top of the dst deck and otherwise to the bottom
     * @param dst
     * @param index
     * @param top
     */
    public void transferIndex(Deck dst, int index, boolean top) {
        if (index > size-1) {return;}
        if (top) {
            dst.addTop(this.remove(index));
        } else {
            dst.addBottom(this.remove(index));
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
                return newDeck;
            case WITH_JOKERS:
                newDeck.addTop(new Card(Rank.JOKER, Suit.JOKER));
                newDeck.addTop(new Card(Rank.JOKER, Suit.JOKER));
                break;
            default:
                break;
        }

        Rank[] rArray = Rank.values();
        Suit[] sArray = Suit.values();
        for(i=0;i<4;i++) {
            for(j=0;j<13;j++) {
                Card temp = new Card(rArray[j],sArray[i]);
                newDeck.addTop(temp);
            }
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

    public static Deck merge(Deck d1, Deck d2, boolean shuffle) {
        if (d1 == null || d2 == null) {return null;}
        Deck newDeck = new Deck();
        newDeck.cards.addAll((List<Card>)d1.getAll());
        newDeck.cards.addAll((List<Card>)d2.getAll());
        newDeck.size = newDeck.cards.size();

        if (shuffle) {
            newDeck.shuffle();
        }
        return newDeck;
    }

    public Deck[] distribute(int num_decks, int cards_per_deck, RemainderStrategy strat, DealDirection direction) {
        Deck[] decks = new Deck[num_decks];
        for (int n=0; n < num_decks; n++) {
            decks[n] = Deck.initialize(false, DeckType.EMPTY);
        }
        if (cards_per_deck*num_decks > size) {
            cards_per_deck = (size - (size%num_decks)) / num_decks;
        }
        if (direction == DealDirection.CLOCKWISE) {
            for (int j=0; j < cards_per_deck; j++) {
                for (int i = 0; i < num_decks; i++) {
                    decks[i].addTop(this.removeTop());
                }
            }
        } else {
            for (int j=0; j < cards_per_deck; j++) {
                for (int i = num_decks-1; i>=0; i--) {
                    decks[i].addTop(this.removeTop());
                }
            }
        }
        if (strat == RemainderStrategy.DISTRIBUTE_REMAINDERS) {
            if (direction == DealDirection.CLOCKWISE) {
                for (int i = 0; i < num_decks && size>0; i++) {
                    decks[i].addTop(this.removeTop());
                }
            } else {
                for (int i = num_decks-1; i>=0 && size>0; i--) {
                    decks[i].addTop(this.removeTop());
                }
            }
        }
        return decks;
    }

    public void setComparator(CardComparator comp) {
        this.comp = comp;
    }
    /*
    * Orders the deck using the currently set comparator. If no comparator has been set, no ordering
    * will be applied
    **/
    public void order() {
        Collections.sort(cards,comp);
    }

    public void order(CardComparator comp) {
        Collections.sort(cards,comp);
    }

    public void setEvaluator(CardEvaluator eval) {
        this.eval = eval;
    }

    public int pointValue() {
		int tot = 0;
		for (int x = 0; x < cards.size(); x++) {
			tot += eval.value(cards.get(x));
		}
		return tot;
    }

    public int pointValue(CardEvaluator eval) {
		int tot = 0;
		for (int x = 0; x < cards.size(); x++) {
			tot += eval.value(cards.get(x));
		}
		return tot;
    }

    public Card getMax() {
		int index = 0;
		for (int x = 1; x < cards.size(); x++) {
			if (comp.compare(cards.get(0), cards.get(x)) < 0) {
				index = x;
			}
		}
		return cards.get(index);
    }

    public Card getMin() {
		int index = 0;
		for (int x = 1; x < cards.size(); x++) {
			if (comp.compare(cards.get(0), cards.get(x)) > 0) {
				index = x;
			}
		}
		return cards.get(index);
    }

    /*
    Returns a copy of the decks card list
    **/
    public List<Card> getAll() {
        return cards.subList(0,size);
    }
    //TO-DO: Implement this method
    //public List<Card> removeBulk(int num_cards, boolean top) {
    //    List<Card> temp = new ArrayList<Card>();
    //    if(top) {
    //    }
    //}

    public void insert(int index, Card card) {
        if (index > size) { index = size; }
        cards.add(index, card);
        size++;
    }

    public void insert(int index, List<Card> cards) {
        if (index > size) { index = size; }
        cards.addAll(index, cards);
        size = cards.size();
    }

    public int size() {
        return this.size;
    }

    public int countRank(Rank rank) {
        int counter = 0;
        for (int i=0;i<size;i++) {
            if (cards.get(i).getRank().equals(rank)) {
                counter++;
            }
        }
        return counter;
    }

    public int countSuit(Suit suit) {
        int counter = 0;
        for (int i=0;i<size;i++) {
            if (cards.get(i).getSuit().equals(suit)) {
                counter++;
            }
        }
        return counter;
    }

    public int countRankSuit(Rank rank, Suit suit) {
        int counter = 0;
        for (int i=0;i<size;i++) {
            if (cards.get(i).getRank().equals(rank) && cards.get(i).getSuit().equals(suit)) {
                counter++;
            }
        }
        return counter;
    }

    /************************************
     * DEBUGGING METHODS
     *********************************/

    @Override
    public String toString() {
        String out = "";
        for (int i =0; i < size; i++) {
            Card temp = cards.get(i);
            out = out.concat("\n" + i + " : " + temp.getRank().toString() + " of " + temp.getSuit().toString());
        }
        return out;
    }

}
