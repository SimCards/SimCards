package io.github.simcards.libcards.network;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles messages received by the network interface
 */
public interface MessageHandler {
    void handleMessage(JSONObject msg) throws JSONException;
}
