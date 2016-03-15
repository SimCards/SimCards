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
        this.size--;
        if (size > -1) {
            return cards.remove(cards.size());
        } else {
            return null;
        }
    }

    public Card removeBottom() {
        this.size--;
        if (size > -1) {
            return cards.remove(0);
        } else {
            return null;
        }
    }

    public Card get(int index) {
        return cards.get(index);
    }

    public Card remove(int index) {
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
        switch(manner) {

            case TOP_TO_TOP:

                for(i=0;i<num_cards && i < size;i++) {
                    dst.addTop(cards.remove(cards.size() - (num_cards)));
                }

                break;

            case BOTTOM_TO_BOTTOM:

                for(i=0;i<num_cards && i < size;i++) {
                    dst.addBottom(cards.remove((num_cards - 1) - i));
                }

                break;

            case BOTTOM_TO_BOTTOM_REVERSE:

                for(i=0;i<num_cards && i < size;i++) {
                    dst.addBottom(cards.remove(0));
                }

                break;

            case BOTTOM_TO_TOP:

                for(i=0;i<num_cards && i < size;i++) {
                    dst.addTop(cards.remove((num_cards - 1) - i));
                }

                break;

            case TOP_TO_BOTTOM:

                for(i=0;i<num_cards && i < size;i++) {
                    dst.addBottom(cards.remove(cards.size()-num_cards));
                }

                break;

            case TOP_TO_TOP_REVERSE:

                for(i=0;i<num_cards && i < size;i++) {
                    dst.addTop(cards.remove(cards.size()-1));
                }

                break;

            default:
                break;
        }

        //Resize based on the number of cards that were moved
        if (size < num_cards) {
            size = 0;
        } else {
            size-= num_cards;
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
                return newDeck;
            case WITH_JOKERS:
                newDeck.cards.add(new Card(Rank.JOKER,Suit.JOKER));
                newDeck.cards.add(new Card(Rank.JOKER,Suit.JOKER));
                break;
            default:
                break;
        }

        Rank[] rArray = Rank.values();
        Suit[] sArray = Suit.values();
        for(i=0;i<4;i++) {
            for(j=0;j<13;j++) {
                Card temp = new Card(rArray[j],sArray[i]);
                newDeck.cards.add(temp);
            }
        }
        newDeck.size = newDeck.cards.size();
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
        return (List<Card>) cards.clone();
    }
    //TO-DO: Implement this method
    //public List<Card> removeBulk(int num_cards, boolean top) {
    //    List<Card> temp = new ArrayList<Card>();
    //    if(top) {
    //    }
    //}

    public void insert(int index, Card card) {
        cards.add(index, card);
    }

    public void insert(int index, List<Card> cards) {
        cards.addAll(index, cards);
    }

    public int size() {
        return size;
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
