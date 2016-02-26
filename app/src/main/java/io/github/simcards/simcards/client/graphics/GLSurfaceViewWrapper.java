package io.github.simcards.simcards.client.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;

import io.github.simcards.libcards.util.IRerender;

/**
 * Wrapper for GLSurfaceView for touch screen compatibility.
 */
public class GLSurfaceViewWrapper extends GLSurfaceView implements IRerender {

    /** The surface view object currently in use. */
    private static GLSurfaceViewWrapper sSurfaceView;

    /** The Renderer used by the surface. */
    private final GLRenderer mRenderer;

    /**
     * Initializes a surface view.
     * @param context The context creating this surface.
     */
    public GLSurfaceViewWrapper(Context context) {
        super(context);

        sSurfaceView = this;

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        mRenderer = new GLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView.
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data.
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    /**
     * Returns the surface view currently in use.
     * @return The surface view currently in use.
     */
    public static GLSurfaceViewWrapper getInstance() {
        return sSurfaceView;
    }

    /**
     * Marks the view as dirty, requiring a re-render.
     */
    public void rerender() {
        if (sSurfaceView != null) {
            sSurfaceView.requestRender();
        }
    }
}
