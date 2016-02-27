package io.github.simcards.simcards.client.graphics;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.github.simcards.libcards.graphics.IGLWrapper;
import io.github.simcards.libcards.graphics.GLRenderer;
import io.github.simcards.simcards.R;

/**
 * Android interface to the renderer.
 */
public class AndroidGLRenderer implements GLSurfaceView.Renderer {

    /** The renderer displaying objects on the screen. */
    private GLRenderer glRenderer;

    /** The vertex shader to use when rendering. */
    private int vertexShader;
    /** The fragment shader to use when rendering. */
    private int fragmentShader;

    /**
     * Initializes the renderer.
     */
    public AndroidGLRenderer() {
        super();
        glRenderer = new GLRenderer();
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        vertexShader = AndroidGraphicsUtil.loadShader(IGLWrapper.GL_VERTEX_SHADER, R.raw.default_vert);
        fragmentShader = AndroidGraphicsUtil.loadShader(IGLWrapper.GL_FRAGMENT_SHADER, R.raw.default_frag);
        glRenderer.onSurfaceCreated(vertexShader, fragmentShader);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        glRenderer.onDrawFrame();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glRenderer.onSurfaceChanged(width, height);
    }

    /**
     * Cleans up shaders when the program terminates.
     */
    public void exit() {
        glRenderer.exit(vertexShader, fragmentShader);
    }
}
