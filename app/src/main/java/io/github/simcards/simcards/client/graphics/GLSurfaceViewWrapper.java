package io.github.simcards.simcards.client.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Wrapper for GLSurfaceView for touch screen compatibility.
 */
public class GLSurfaceViewWrapper extends GLSurfaceView {

    /** The surface view object currently in use. */
    private static GLSurfaceViewWrapper surfaceView;

    /** The Renderer used by the surface. */
    private final GLRenderer renderer;

    /**
     * Initializes a surface view.
     * @param context The context creating this surface.
     */
    public GLSurfaceViewWrapper(Context context) {
        super(context);

        surfaceView = this;

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        renderer = new GLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView.
        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data.
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    /**
     * Returns the surface view currently in use.
     * @return The surface view currently in use.
     */
    public static GLSurfaceViewWrapper getInstance() {
        return surfaceView;
    }

    /**
     * Marks the view as dirty, requiring a re-render.
     */
    public static void rerender() {
        if (surfaceView != null) {
            surfaceView.requestRender();
        }
    }
}
