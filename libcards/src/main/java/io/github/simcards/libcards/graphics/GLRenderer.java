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

    /** Shapes to draw on the screen as part of the world. */
    private static final List<Shape> shapes = new ArrayList<>();

    /** The model view projection matrix. */
    private static float[] mvpMatrix = new float[16];
    /** The projection matrix. */
    private static float[] projectionMatrix = new float[16];
    /** The unscaled projection matrix. */
    private static float[] baseProjectionMatrix = new float[16];
    /** The camera view matrix. */
    private static float[] viewMatrix = new float[16];

    /* The static model view projection matrix. */
    private static float[] fixedMVPMatrix = new float[16];
    /* The static camera view matrix. */
    private static float[] fixedViewMatrix = new float[16];

    /** The camera used to look at the playing field. */
    public static Camera camera = new Camera();

    /** The z coordinate of the camera. */
    private static final float ZCAMERA = -3;

    /**
     * Initializes the fixed view matrix.
     */
    public GLRenderer() {
        Matrix.setLookAtM(fixedViewMatrix, 0, 0, 0, ZCAMERA, 0, 0, 0f, 0f, 1.0f, 0.0f);
    }

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

        Matrix.setLookAtM(viewMatrix, 0, -cameraPosition.x, cameraPosition.y, ZCAMERA,
                -cameraPosition.x, cameraPosition.y, 0f, 0f, 1.0f, 0.0f);

        Matrix.rotateM(viewMatrix, 0, camera.rotation, 0, 0, 1);

        // Calculate the projection and view transformation.
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        Matrix.multiplyMM(fixedMVPMatrix, 0, baseProjectionMatrix, 0, fixedViewMatrix, 0);

        // Draw the shapes.
        synchronized(shapes) {
            List<Shape> fixedShapes = new ArrayList<>();
            for (Shape shape : shapes) {
                shape.initializeTexture();
                if (shape.fixed) {
                    fixedShapes.add(shape);
                } else {
                    shape.draw(mvpMatrix);
                }
            }
            // Draw fixed shapes after world shapes.
            for (Shape shape : fixedShapes) {
                shape.draw(fixedMVPMatrix);
            }
        }
    }

    /**
     * Responds to a change in window size.
     * @param width The new window width.
     * @param height The new window height.
     */
    public void onSurfaceChanged(int width, int height) {
        GraphicsUtil.screenWidth = width;
        GraphicsUtil.screenHeight = height;

        IGLWrapper gl = Factory.gl();
        gl.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // This projection matrix is applied to object coordinates in the onDrawFrame() method.
        Matrix.orthoM(baseProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 50);

        GameScreen.getScreen().redrawHand();

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
     * Adds a shape to the render list.
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
