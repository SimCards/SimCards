package io.github.simcards.simcards.client.graphics;

import android.opengl.GLSurfaceView;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.github.simcards.libcards.graphics.Camera;
import io.github.simcards.libcards.graphics.GraphicsUtil;
import io.github.simcards.libcards.graphics.IGLWrapper;
import io.github.simcards.libcards.graphics.OtherGLRenderer;
import io.github.simcards.libcards.graphics.Shape;
import io.github.simcards.libcards.util.Factory;
import io.github.simcards.libcards.util.MathUtil;
import io.github.simcards.libcards.util.Matrix;
import io.github.simcards.libcards.util.Position;
import io.github.simcards.simcards.R;

/**
 * Renders shapes on the screen.
 */
public class GLRenderer implements GLSurfaceView.Renderer {

    private OtherGLRenderer glRenderer;

    private int vertexShader;
    private int fragmentShader;

    public GLRenderer() {
        super();
        glRenderer = new OtherGLRenderer();
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

    public void exit() {
        glRenderer.exit(vertexShader, fragmentShader);
    }
}
