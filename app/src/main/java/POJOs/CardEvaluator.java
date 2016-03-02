package POJOs;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
/**
 * Created by Patrick on 2/26/2016.
 */
public class CardEvaluator {
	private Map<Card, Integer> cardMap;//0
	private Map<Rank, Integer> rankMap;//1
	private Map<Suit, Integer> suitMap;//2
	private int pick;
	public CardEvaluator() {
		rankMap = new HashMap<Rank, Integer>();
		for (Rank r : Rank.values()) {
			rankMap.put(r, r.ordinal());
		}
		pick = 1;
	}
	public void setMap(Map map) {
		for (Object a : map.keySet()) {
			if (a instanceof Card) {
				cardMap = map;
				pick = 0;
			} else if (a instanceof Rank) {
				rankMap = map;
				pick = 1;
			} else if (a instanceof Suit) {
				suitMap = map;
				pick = 2;
			}
			break;
		}
	}
	public int value(Card c) {
		if (pick == 0) {
			return cardMap.getOrDefault(c, -1);
		} else if (pick == 1) {
			return rankMap.getOrDefault(c.getRank(), -1);
		} else if (pick == 2) {
			return suitMap.getOrDefault(c.getSuit(), -1);
		}
		return -1;
	}
}
