package io.github.simcards.libcards.graphics;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.libcards.util.Factory;
import io.github.simcards.libcards.util.MathUtil;
import io.github.simcards.libcards.util.Matrix;
import io.github.simcards.libcards.util.Position;

import java.util.ArrayList;
import java.util.List;


import io.github.simcards.libcards.graphics.Camera;
import io.github.simcards.libcards.graphics.GraphicsUtil;
import io.github.simcards.libcards.graphics.IGLWrapper;
import io.github.simcards.libcards.graphics.Shape;
import io.github.simcards.libcards.util.Factory;
import io.github.simcards.libcards.util.MathUtil;
import io.github.simcards.libcards.util.Matrix;
import io.github.simcards.libcards.util.Position;

/**
 * Renders shapes on the screen.
 */
public class OtherGLRenderer {

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

    public void onSurfaceCreated(int vertexShader, int fragmentShader) {
        IGLWrapper gl = Factory.gl();
        // Create empty OpenGL ES Program.
        int shaderProgram = gl.glCreateProgram();
        // Add the vertex shader to program.
        gl.glAttachShader(shaderProgram, vertexShader);
        // Add the fragment shader to program.
        gl.glAttachShader(shaderProgram, fragmentShader);
        // Creates OpenGL ES program executables.
        gl.glLinkProgram(shaderProgram);
        GraphicsUtil.sShaderProgram = shaderProgram;

        // Set the background frame color.
        gl.glClearColor(0.066f, 0.567f, 0.404f, 1.0f);
    }

    public void onDrawFrame() {
        IGLWrapper gl = Factory.gl();
        if (sCamera.checkScaleChange()) {
            mProjectionMatrix = MathUtil.scaleMatrix(mBaseProjectionMatrix, 1.0f / sCamera.scale);
        }
        // Redraw background color.
        gl.glClear(IGLWrapper.GL_COLOR_BUFFER_BIT);

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

    public void onSurfaceChanged(int width, int height) {
        IGLWrapper gl = Factory.gl();
        gl.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // This projection matrix is applied to object coordinates in the onDrawFrame() method.
        Matrix.orthoM(mBaseProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 50);

        rerender();
    }

    public void exit(int vertexShader, int fragmentShader) {
        IGLWrapper gl = Factory.gl();
        gl.exit(vertexShader, fragmentShader);
    }

    /**
     * Adds an shape to the render list.
     * @param shape The shape to add to the render list.
     */
    public static void addShape(Shape shape) {
        synchronized(shapes) {
            shapes.add(shape);
        }
        rerender();
    }

    /**
     * Removes a shape from the render list.
     * @param shape The shape to remove from the render list.
     */
    public static void removeShape(Shape shape) {
        synchronized(shapes) {
            shapes.remove(shape);
        }
        rerender();
    }

    /**
     * Rerenders the screen.
     */
    public static void rerender() {
        Factory.rerenderer().rerender();
        sCamera.prevScale = -1;
    }
}
