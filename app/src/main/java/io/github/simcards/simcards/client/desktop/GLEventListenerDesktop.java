package io.github.simcards.simcards.client.desktop;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import io.github.simcards.simcards.client.graphics.GLWrapper;

/**
 * Acts on OpenGL events triggered by the desktop application.
 */
public class GLEventListenerDesktop implements GLEventListener {

    @Override
    public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
        SimCardsDesktop.sRenderer.onSurfaceChanged(null, width, height);
    }

    @Override
    public void init(GLAutoDrawable glautodrawable) {
        GLWrapper.setGL(glautodrawable.getGL().getGL2ES2());
        SimCardsDesktop.sRenderer.onSurfaceCreated(null, null);
    }

    @Override
    public void dispose(GLAutoDrawable glautodrawable) {
        SimCardsDesktop.sRenderer.exit();
        SimCardsDesktop.exit();
    }

    @Override
    public void display(GLAutoDrawable glautodrawable) {
        SimCardsDesktop.sRenderer.onDrawFrame(null);
    }
}
