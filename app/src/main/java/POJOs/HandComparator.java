package POJOs;

/**
 * Created by Patrick on 3/1/2016.
 */
public class HandComparator implements CardComparator {
    @Override
    /*
    Comparator made for user's hands. Orders by suit then rank
    **/
    public int compare(Card lhs, Card rhs) {
        if (lhs.getSuit().ordinal() > rhs.getSuit().ordinal()) {
            return 1;
        } else if (lhs.getSuit().ordinal() < rhs.getSuit().ordinal()) {
            return -1;
        } else {
            if (lhs.getRank().ordinal() > rhs.getRank().ordinal()) {
                return 1;
            } else if (lhs.getRank().ordinal() < rhs.getRank().ordinal()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
