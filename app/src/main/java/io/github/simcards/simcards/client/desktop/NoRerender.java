package io.github.simcards.simcards.client.desktop;

import io.github.simcards.libcards.util.IRerender;

/**
 * Empty rerenderer used to ignore rerender calls.
 */
public class NoRerender implements IRerender {
    /**
     * Does nothing; ignores rerender calls.
     */
    public void rerender() {}
}
