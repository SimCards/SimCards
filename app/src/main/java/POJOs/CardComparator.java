package POJOs;

import java.util.Comparator;

/**
 * Created by Patrick on 3/1/2016.
 */
public interface CardComparator extends Comparator<Card> {
    @Override
    int compare(Card lhs, Card rhs);

    @Override
    boolean equals(Object object);
}
