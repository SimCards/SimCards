package POJOs;

import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
/**
 * Created by Patrick on 3/1/2016.
 */
public class CardComparator implements Comparator<Card> {
	private List<Card> cardOrder; //0
	private List<Rank> rankOrder; //1
	private List<Suit> suitOrder; //2
	private ArrayList<Integer> order;
	public CardComparator() {
		order = new ArrayList<Integer>();
		order.add(1);
		rankOrder = new ArrayList<Rank>();
		for (Rank r : Rank.values()) {
			rankOrder.add(r);
		}
		setPriority(Priority.RANK);
	}	
	
	public void setOrdering(List cards) {
		if (cards.get(0) instanceof Rank) {
			rankOrder = cards;
		} else if (cards.get(0) instanceof Card) {
			cardOrder = cards;
		} else if (cards.get(0) instanceof Suit) {
			suitOrder = cards;
		}
	}
	public void setPriority(Priority pri) {
		Integer[] ord = order.toArray(new Integer[order.size()]);
		switch(pri) {
			case ABSOLUTE:
				ord = new Integer[]{0};
				break;
			case RANK_THEN_SUIT:
				ord = new Integer[]{1, 2};
				break;
			case SUIT_THEN_RANK:
				ord = new Integer[]{2, 1};
				break;
			case SUIT:
				ord = new Integer[]{2};
				break;
			case RANK:
				ord = new Integer[]{1};
				break;
			case NONE:
				ord = new Integer[]{};
				break;
			default:
				break;
		}
		order = new ArrayList<Integer>(Arrays.asList(ord));
	}
	
	private int getIndex(Card c) {
		for (int x = 0; x < cardOrder.size(); x++) {
			if (c.equals(cardOrder.get(x))) {
				return x;
			}
		}
		return -1;
	}
	
	private int getIndex(Rank c) {
		for (int x = 0; x < rankOrder.size(); x++) {
			if (c==rankOrder.get(x)) {
				return x;
			}
		}
		return -1;
	}
	
	private int getIndex(Suit c) {
		for (int x = 0; x < suitOrder.size(); x++) {
			if (c==suitOrder.get(x)) {
				return x;
			}
		}
		return -1;
	}
	
    @Override
    public int compare(Card lhs, Card rhs) {	
		for (Integer val : order) {
			int a=0,b=0;
			if (val == 0) {
				b = getIndex(lhs);
				b = getIndex(rhs);
			} else if (val == 1) {
				a = getIndex(lhs.getRank());
				b = getIndex(rhs.getRank());
			} else if (val == 2) {
				a = getIndex(lhs.getSuit());
				b = getIndex(rhs.getSuit());
			}
			if (a != b) {
				return a-b;
			}
		}
		return 0;
	}
}
