package io.github.simcards.simcards.client.util;

import io.github.simcards.libcards.util.IMiddleman;
import io.github.simcards.simcards.R;
import io.github.simcards.libcards.game.Card;

/**
 * Created by Vishal on 2/26/16.
 */
public class AndroidMiddleman implements IMiddleman {

    public AndroidMiddleman() {

    }

    @Override
    public int getImageLocation(Card c) {
        if (!c.getFaceUp()) {
            return R.drawable.face_down;
        }
        switch(c.suit) {
            case BLACKJOKER:
                return R.drawable.black_joker;
            case REDJOKER:
                return R.drawable.red_joker;
            case SPADE:
                switch(c.rank) {
                    case ACE: return R.drawable.ace_of_spades;
                    case TWO: return R.drawable.two_of_spades;
                    case THREE: return R.drawable.three_of_spades;
                    case FOUR: return R.drawable.four_of_spades;
                    case FIVE: return R.drawable.five_of_spades;
                    case SIX: return R.drawable.six_of_spades;
                    case SEVEN: return R.drawable.seven_of_spades;
                    case EIGHT: return R.drawable.eight_of_spades;
                    case NINE: return R.drawable.nine_of_spades;
                    case TEN: return R.drawable.ten_of_spades;
                    case JACK: return R.drawable.jack_of_spades;
                    case QUEEN: return R.drawable.queen_of_spades;
                    case KING: return R.drawable.king_of_spades;
                }
            case HEART:
                switch(c.rank) {
                    case ACE: return R.drawable.ace_of_hearts;
                    case TWO: return R.drawable.two_of_hearts;
                    case THREE: return R.drawable.three_of_hearts;
                    case FOUR: return R.drawable.four_of_hearts;
                    case FIVE: return R.drawable.five_of_hearts;
                    case SIX: return R.drawable.six_of_hearts;
                    case SEVEN: return R.drawable.seven_of_hearts;
                    case EIGHT: return R.drawable.eight_of_hearts;
                    case NINE: return R.drawable.nine_of_hearts;
                    case TEN: return R.drawable.ten_of_hearts;
                    case JACK: return R.drawable.jack_of_hearts;
                    case QUEEN: return R.drawable.queen_of_hearts;
                    case KING: return R.drawable.king_of_hearts;
                }
            case CLUB:
                switch(c.rank) {
                    case ACE: return R.drawable.ace_of_clubs;
                    case TWO: return R.drawable.two_of_clubs;
                    case THREE: return R.drawable.three_of_clubs;
                    case FOUR: return R.drawable.four_of_clubs;
                    case FIVE: return R.drawable.five_of_clubs;
                    case SIX: return R.drawable.six_of_clubs;
                    case SEVEN: return R.drawable.seven_of_clubs;
                    case EIGHT: return R.drawable.eight_of_clubs;
                    case NINE: return R.drawable.nine_of_clubs;
                    case TEN: return R.drawable.ten_of_clubs;
                    case JACK: return R.drawable.jack_of_clubs;
                    case QUEEN: return R.drawable.queen_of_clubs;
                    case KING: return R.drawable.king_of_clubs;
                }
            case DIAMOND:
                switch(c.rank) {
                    case ACE: return R.drawable.ace_of_diamonds;
                    case TWO: return R.drawable.two_of_diamonds;
                    case THREE: return R.drawable.three_of_diamonds;
                    case FOUR: return R.drawable.four_of_diamonds;
                    case FIVE: return R.drawable.five_of_diamonds;
                    case SIX: return R.drawable.six_of_diamonds;
                    case SEVEN: return R.drawable.seven_of_diamonds;
                    case EIGHT: return R.drawable.eight_of_diamonds;
                    case NINE: return R.drawable.nine_of_diamonds;
                    case TEN: return R.drawable.ten_of_diamonds;
                    case JACK: return R.drawable.jack_of_diamonds;
                    case QUEEN: return R.drawable.queen_of_diamonds;
                    case KING: return R.drawable.king_of_diamonds;
                }
        }
        return -1;
    }
}
