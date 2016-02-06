package io.github.simcards.simcards.client.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.github.simcards.simcards.R;
import io.github.simcards.simcards.client.util.GraphicsUtil;
import io.github.simcards.simcards.client.util.Vector3;

/**
 * Renders shapes on the screen.
 */
public class GLRenderer implements GLSurfaceView.Renderer {

    /** A card to render on the screen. */
    private Card card;

    /** Model view projection matrix. */
    private float[] mvpMatrix = new float[16];
    /** Projection matrix. */
    private float[] projectionMatrix = new float[16];
    /** Camera view matrix. */
    private float[] viewMatrix = new float[16];

    /** The camera used to look at the playing field. */
    public static Camera camera = new Camera();

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        int vertexShader = GraphicsUtil.loadShader(GLES20.GL_VERTEX_SHADER, R.raw.default_vert);
        int fragmentShader = GraphicsUtil.loadShader(GLES20.GL_FRAGMENT_SHADER, R.raw.default_frag);
        // Create empty OpenGL ES Program.
        int shaderProgram = GLES20.glCreateProgram();
        // Add the vertex shader to program.
        GLES20.glAttachShader(shaderProgram, vertexShader);
        // Add the fragment shader to program.
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        // Creates OpenGL ES program executables.
        GLES20.glLinkProgram(shaderProgram);
        GraphicsUtil.shaderProgram = shaderProgram;

        card = new Card();

        // Set the background frame color.
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // Redraw background color.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (view matrix).
        Vector3 cameraPosition = camera.getPosition();
        System.out.println(cameraPosition.toString());

        Matrix.setLookAtM(viewMatrix, 0, cameraPosition.x, cameraPosition.y, cameraPosition.z, cameraPosition.x, cameraPosition.y, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation.
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        // Draw the shape.
        card.draw(mvpMatrix);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // This projection matrix is applied to object coordinates in the onDrawFrame() method.
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 50);
    }
}
