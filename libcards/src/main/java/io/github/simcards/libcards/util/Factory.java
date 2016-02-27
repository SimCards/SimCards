package io.github.simcards.libcards.util;

import io.github.simcards.libcards.graphics.IGLWrapper;

/**
 * Stores platform-specific interfaces for the application.
 */
public class Factory {

    /** The logger used by the application. */
    private static ILog log = null;
    /** The renderer used by the application. */
    private static IGLWrapper glWrapper = null;
    /** The rerenderer used by the application. */
    private static IRerender rerender = null;

    /**
     * Stores platform-specific interfaces for the application.
     * @param log The logger used by the application.
     * @param glWrapper The renderer used by the application.
     * @param rerenderer The rerenderer used by the application.
     * @param middleman The image resource retriever used by the application.
     */
    public static void init(ILog log, IGLWrapper glWrapper, IRerender rerenderer, IMiddleman middleman) {
        System.out.println("Initializing factory.");
        Middleman.init(middleman);
        Factory.log = log;
        Factory.glWrapper = glWrapper;
        Factory.rerender = rerenderer;
    }

    /**
     * Gets the logger used by the application.
     * @return The logger used by the application.
     */
    public static ILog log() {
        if (log == null) {
            throw new IllegalStateException("Log must be initialized before use.");
        }
        return log;
    }

    /**
     * Gets the renderer used by the application.
     * @return The renderer used by the application.
     */
    public static IGLWrapper gl() {
        if (glWrapper == null) {
            throw new IllegalStateException("GL must be initialized before use.");
        }
        return glWrapper;
    }

    /**
     * Sets the rerenderer used by the application.
     * @param r The rerenderer used by the application.
     */
    public static void setRerenderer(IRerender r) {
        rerender = r;
    }

    /**
     * Gets the rerenderer used by the application.
     * @return The rerenderer used by the application.
     */
    public static IRerender rerenderer() {
        if (rerender == null) {
            throw new UnsupportedOperationException("Rerenderer has not been initialized.");
        }
        return rerender;
    }

    /**
     * Private constructor so no instances of the class can be initialized.
     */
    private Factory() {}
}
