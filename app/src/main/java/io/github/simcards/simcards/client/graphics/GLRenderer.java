package io.github.simcards.simcards.client.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.jogamp.opengl.GL2ES2;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.github.simcards.simcards.R;
import io.github.simcards.simcards.util.MathUtil;
import io.github.simcards.simcards.util.Matrix;
import io.github.simcards.simcards.util.Position;

/**
 * Renders shapes on the screen.
 */
public class GLRenderer implements GLSurfaceView.Renderer {

    /** Shapes to draw on the screen. */
    private static final List<Shape> shapes = new ArrayList<>();

    /** The model view projection matrix. */
    public static float[] mMVPMatrix = new float[16];
    /** The projection matrix. */
    private float[] mProjectionMatrix = new float[16];
    /** The unscaled projection matrix. */
    private float[] mBaseProjectionMatrix = new float[16];
    /** The camera view matrix. */
    private float[] mViewMatrix = new float[16];

    /** The camera used to look at the playing field. */
    public static Camera sCamera = new Camera();

    /** The vertex shader to use. */
    private int vertexShader;
    /** The fragment shader to use. */
    private int fragmentShader;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        vertexShader = GraphicsUtil.loadShader(GLES20.GL_VERTEX_SHADER, R.raw.default_vert);
        fragmentShader = GraphicsUtil.loadShader(GLES20.GL_FRAGMENT_SHADER, R.raw.default_frag);
        // Create empty OpenGL ES Program.
        int shaderProgram = GLWrapper.glCreateProgram();
        // Add the vertex shader to program.
        GLWrapper.glAttachShader(shaderProgram, vertexShader);
        // Add the fragment shader to program.
        GLWrapper.glAttachShader(shaderProgram, fragmentShader);
        // Creates OpenGL ES program executables.
        GLWrapper.glLinkProgram(shaderProgram);
        GraphicsUtil.sShaderProgram = shaderProgram;

        // Set the background frame color.
        GLWrapper.glClearColor(0.066f, 0.567f, 0.404f, 1.0f);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        if (sCamera.checkScaleChange()) {
            mProjectionMatrix = MathUtil.scaleMatrix(mBaseProjectionMatrix, 1.0f / sCamera.scale);
        }
        // Redraw background color.
        GLWrapper.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (view matrix).
        Position cameraPosition = sCamera.position;

        Matrix.setLookAtM(mViewMatrix, 0, -cameraPosition.x, cameraPosition.y, -3,
                -cameraPosition.x, cameraPosition.y, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation.
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw the shapes.
        synchronized(shapes) {
            for (Shape shape : shapes) {
                shape.initializeTexture();
                shape.draw(mMVPMatrix);
            }
        }
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLWrapper.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // This projection matrix is applied to object coordinates in the onDrawFrame() method.
        Matrix.orthoM(mBaseProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 50);
    }

    /**
     * Cleans up shaders after closing the desktop application.
     */
    public void exit() {
        GL2ES2 gl = GLWrapper.desktopGL;
        gl.glUseProgram(0);
        gl.glDeleteBuffers(3, GLWrapper.vboHandles, 0);
        gl.glDetachShader(GraphicsUtil.sShaderProgram, vertexShader);
        gl.glDeleteShader(vertexShader);
        gl.glDetachShader(GraphicsUtil.sShaderProgram, fragmentShader);
        gl.glDeleteShader(fragmentShader);
        gl.glDeleteProgram(GraphicsUtil.sShaderProgram);
    }

    /**
     * Adds an shape to the render list.
     * @param shape The shape to add to the render list.
     */
    public static void addShape(Shape shape) {
        synchronized(shapes) {
            shapes.add(shape);
        }
        GLSurfaceViewWrapper.rerender();
    }

    /**
     * Removes a shape from the render list.
     * @param shape The shape to remove from the render list.
     */
    public static void removeShape(Shape shape) {
        synchronized(shapes) {
            shapes.remove(shape);
        }
        GLSurfaceViewWrapper.rerender();
    }
}
