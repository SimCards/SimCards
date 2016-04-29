package io.github.simcards.desktop;

import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import io.github.simcards.libcards.graphics.GraphicsUtil;
import io.github.simcards.libcards.graphics.IGLWrapper;
import io.github.simcards.libcards.util.Factory;
import io.github.simcards.libcards.util.Logger;

/**
 * Acts on OpenGL events triggered by the desktop application.
 */
public class GLEventListenerDesktop implements GLEventListener {

    /** The vertex shader used for rendering. */
    private int vertexShader;
    /** The fragment shader used for rendering. */
    private int fragmentShader;

    @Override
    public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
        SimCardsDesktop.renderer.onSurfaceChanged(width, height);
    }

    @Override
    public void init(GLAutoDrawable glautodrawable) {
        GL2ES2 gl = glautodrawable.getGL().getGL2ES2();
        Factory.init(new Logger(), new DesktopGLWrapper(gl), new NoRerender(), new DesktopMiddleman());
        vertexShader = DesktopGraphicsUtil.loadShader(IGLWrapper.GL_VERTEX_SHADER, R.raw.default_vert);
        fragmentShader = DesktopGraphicsUtil.loadShader(IGLWrapper.GL_FRAGMENT_SHADER, R.raw.default_frag);
        SimCardsDesktop.renderer.onSurfaceCreated(vertexShader, fragmentShader);
    }

    @Override
    public void dispose(GLAutoDrawable glautodrawable) {
        SimCardsDesktop.renderer.exit(vertexShader, fragmentShader);
        SimCardsDesktop.exit();
    }

    @Override
    public void display(GLAutoDrawable glautodrawable) {
        SimCardsDesktop.renderer.onDrawFrame();
    }
}
