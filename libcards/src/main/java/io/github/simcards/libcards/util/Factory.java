package io.github.simcards.libcards.util;

import io.github.simcards.libcards.graphics.IGLWrapper;

/**
 * Singleton class for this project
 */
public class Factory {
    static {}

    private static ILog log = null;
    private static IGLWrapper glWrapper = null;
    private static IRerender rerender = null;

    public static void init(ILog log, IGLWrapper glWrapper, IRerender rerenderer, IMiddleman middleman) {
        System.out.println("initializing factory");
        Middleman.init(middleman);
        Factory.log = log;
        Factory.glWrapper = glWrapper;
        Factory.rerender = rerenderer;
    }

    public static ILog log() {
        if(log == null) {
            throw new IllegalStateException("Util must be initialized before use");
        }
        return log;
    }


    public static IGLWrapper gl() {
        if (glWrapper == null) {
            throw new IllegalStateException("gl must be initialized before use");
        }
        return glWrapper;
    }

    public static void setRerenderer(IRerender r) {
        rerender = r;
    }

    public static IRerender rerenderer() {
        if (rerender == null) {
            throw new UnsupportedOperationException("rerenderer has not been initialized");
        }
        return rerender;
    }

    private Factory() {}


}
