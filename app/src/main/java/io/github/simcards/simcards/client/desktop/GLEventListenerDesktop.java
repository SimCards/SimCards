package io.github.simcards.simcards.client.desktop;

import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import io.github.simcards.libcards.graphics.IGLWrapper;
import io.github.simcards.libcards.util.Factory;
import io.github.simcards.libcards.util.Logger;
import io.github.simcards.simcards.R;
import io.github.simcards.simcards.client.graphics.AndroidGraphicsUtil;
import io.github.simcards.simcards.client.util.AndroidMiddleman;

/**
 * Acts on OpenGL events triggered by the desktop application.
 */
public class GLEventListenerDesktop implements GLEventListener {

    private int vertexShader;
    private int fragmentShader;

    @Override
    public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
        SimCardsDesktop.sRenderer.onSurfaceChanged(width, height);
    }

    @Override
    public void init(GLAutoDrawable glautodrawable) {
        GL2ES2 gl = glautodrawable.getGL().getGL2ES2();
        Factory.init(new Logger(), new DesktopGLWrapper(gl), new NoRerender(), new AndroidMiddleman());
        vertexShader = AndroidGraphicsUtil.loadShader(IGLWrapper.GL_VERTEX_SHADER, R.raw.default_vert);
        fragmentShader = AndroidGraphicsUtil.loadShader(IGLWrapper.GL_FRAGMENT_SHADER, R.raw.default_frag);
        SimCardsDesktop.sRenderer.onSurfaceCreated(vertexShader, fragmentShader);
    }

    @Override
    public void dispose(GLAutoDrawable glautodrawable) {
        SimCardsDesktop.sRenderer.exit(vertexShader, fragmentShader);
        SimCardsDesktop.exit();
    }

    @Override
    public void display(GLAutoDrawable glautodrawable) {
        SimCardsDesktop.sRenderer.onDrawFrame();
    }
}
