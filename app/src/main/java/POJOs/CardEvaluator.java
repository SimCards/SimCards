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
	private Basis evaluationBasis;
	enum Basis {CARDS, RANKS, SUITS};

	/**
	 * The default constructor creates a Rank map that correlates the ranks of the cards
	 * to their ordinal values and establishes State.RANKS as the default evaluator
	 */
	public CardEvaluator() {
		rankMap = new HashMap<Rank, Integer>();
		for (Rank r : Rank.values()) {
			rankMap.put(r, r.ordinal());
		}
		evaluationBasis = Basis.RANKS;
	}

	/**
	 * Dependency injection occurs on the basis of the map. When you set a new map that correlates
	 * ranks, suits, or full cards, the Card evaluator will use that most recent map as the basis for comparison
	 * until either a new map is added or the user changes the state with setBasis
	 * @param map
	 */
	public void setMap(Map map) {
		for (Object a : map.keySet()) {
			if (a instanceof Card) {
				cardMap = map;
				evaluationBasis = Basis.CARDS;
			} else if (a instanceof Rank) {
				rankMap = map;
				evaluationBasis = Basis.RANKS;
			} else if (a instanceof Suit) {
				suitMap = map;
				evaluationBasis = Basis.SUITS;
			}
			break;
		}
	}

	/**
	 * Returns the value associated with a given card depending on the currently set evaluation basis
	 * @param c
	 * @return
	 */
	public int value(Card c) {
		if (evaluationBasis == Basis.CARDS) {
			return cardMap.get(c);
		} else if (evaluationBasis == Basis.RANKS) {
			return rankMap.get(c.getRank());
		} else if (evaluationBasis == Basis.SUITS) {
			return suitMap.get(c.getSuit());
		}
		return -1;
	}

	public void setMode(Basis st) {
		evaluationBasis = st;
	}

	/**
	 * Allows for setting the evaluation mode
	 * Takes in a string. The mode can be set to Cards, Suits or Ranks.
	 * As an example, "Card","card","Cards, "cards", "c", "C" "CARDS", etc would all evaluste to
	 * the card state
	 * @param st
	 */
	public void setMode(String st) {
		char temp = st.toUpperCase().charAt(0);
		switch (temp) {
			case 'C':
				evaluationBasis = Basis.CARDS;
				break;
			case 'R':
				evaluationBasis = Basis.RANKS;
				break;
			case 'S':
				evaluationBasis = Basis.SUITS;
				break;
			default:
				evaluationBasis = Basis.RANKS;
				break;
		}
	}


	public Basis getBasis() {
		return evaluationBasis;
	}

	public String getBasisString() {
		return evaluationBasis.name();
	}
}
