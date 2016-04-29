package io.github.simcards.libcards.util;

import org.json.JSONObject;

/**
 * Created by Vishal on 4/17/16.
 */
public interface InputHandler {
    /**
     * Handles the input of the user defined by the game format
     * @param input a dictionary of decks/cards that were interacted with
     * @return false if input was invalid or inconsequential, true if input was valid and consequential
     */
    public boolean handleInput(JSONObject input);
}
