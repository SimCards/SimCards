package POJOs;

import java.util.Comparator;

/**
 * Created by Patrick on 2/26/2016.
 */
public class RankComparator implements CardComparator {
    /*
    Returns 1 if the first argument is greater than the second, -1 if the second is greater than
    the first and returns 0 if the 2 cards are equal
    This particular comparator has no suit preference and determines rank superiority as listed in
    Rank enum within this package.
    **/
    public int compare(Card lhs, Card rhs) {
        if (lhs.getRank().ordinal() > rhs.getRank().ordinal()) {
            return 1;
        } else if (lhs.getRank().ordinal() < rhs.getRank().ordinal()) {
            return -1;
        } else {
            return 0;
        }
    }


    public boolean equals(Card lhs, Card rhs) {
        if (lhs.getRank().ordinal() == rhs.getRank().ordinal()) {
            return true;
        }
        return false;
    }
}
