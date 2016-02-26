package POJOs;

import java.util.ArrayList;

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
            case BOTTOM_TO_BOTTOM :

        }
    }

    public void transferIndex(Deck dst, int index, TransferManner manner) {
    }
}

