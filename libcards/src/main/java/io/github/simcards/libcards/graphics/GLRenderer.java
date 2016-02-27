package io.github.simcards.libcards.graphics;

import java.util.ArrayList;
import java.util.List;

import io.github.simcards.libcards.util.Factory;
import io.github.simcards.libcards.util.MathUtil;
import io.github.simcards.libcards.util.Matrix;
import io.github.simcards.libcards.util.Position;

/**
 * Renders shapes on the screen.
 */
public class GLRenderer {

    /** Shapes to draw on the screen. */
    private static final List<Shape> shapes = new ArrayList<>();

    /** The model view projection matrix. */
    public static float[] mvpMatrix = new float[16];
    /** The projection matrix. */
    private float[] projectionMatrix = new float[16];
    /** The unscaled projection matrix. */
    private float[] baseProjectionMatrix = new float[16];
    /** The camera view matrix. */
    private float[] viewMatrix = new float[16];

    /** The camera used to look at the playing field. */
    public static Camera camera = new Camera();

    /**
     * Initializes the renderer.
     * @param vertexShader The vertex shader to use when rendering.
     * @param fragmentShader The fragment shader to use when rendering.
     */
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

    /**
     * Updates shapes on the screen.
     */
    public void onDrawFrame() {
        IGLWrapper gl = Factory.gl();
        if (camera.checkScaleChange()) {
            projectionMatrix = MathUtil.scaleMatrix(baseProjectionMatrix, 1.0f / camera.scale);
        }
        // Redraw background color.
        gl.glClear(IGLWrapper.GL_COLOR_BUFFER_BIT);

        // Set the camera position (view matrix).
        Position cameraPosition = camera.position;

        Matrix.setLookAtM(viewMatrix, 0, -cameraPosition.x, cameraPosition.y, -3,
                -cameraPosition.x, cameraPosition.y, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation.
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        // Draw the shapes.
        synchronized(shapes) {
            for (Shape shape : shapes) {
                shape.initializeTexture();
                shape.draw(mvpMatrix);
            }
        }
    }

    /**
     * Responds to a change in window size.
     * @param width The new window width.
     * @param height The new window height.
     */
    public void onSurfaceChanged(int width, int height) {
        IGLWrapper gl = Factory.gl();
        gl.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // This projection matrix is applied to object coordinates in the onDrawFrame() method.
        Matrix.orthoM(baseProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 50);

        rerender();
    }

    /**
     * Cleans up the renderer when the program terminates.
     * @param vertexShader The vertex shader being used.
     * @param fragmentShader The fragment shader being used.
     */
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
        camera.prevScale = -1;
    }
}
