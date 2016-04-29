package io.github.simcards.libcards.network;


public enum MessageType {
    /* received by GameClient */
    DECK_ADD,
    DECK_UPDATE,
    DECK_REMOVE,
    GAME_OVER,

    /* received by GameServer */
    EVENT,

    /* sent/received by MatchmakingClient*/
    CONNECTED,
    CONNECT_UPDATE,
    GAME_READY,
}
