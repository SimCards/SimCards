package io.github.simcards.libcards.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import io.github.simcards.libcards.util.Factory;

/**
 * A polygon to be rendered on the screen.
 */
public class Shape {

    /** Buffer of vertices in the shape. */
    private FloatBuffer vertexBuffer;
    /** Buffer of triangle indices in the shape. */
    private ShortBuffer drawListBuffer;
    /** Store our model data in a float buffer. */
    private FloatBuffer textureBuffer;
    /** The number of coordinates per vertex. */
    private final int COORDS_PER_VERTEX = 3;
    /** The coordinates of the vertices in the shape. */
    private float[] shapeCoords;
    /** The triangle indices for the shape. */
    private short[] drawOrder;
    /** The number of bytes per vertex. */
    private int vertexStride;

    /** The color of the shape. */
    private final float COLOR[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    /** Size of the texture coordinate data in elements. */
    private final int COORDS_PER_TEXTURE = 2;
    /** A handle to the shape's texture data. */
    private int textureDataHandle = -1;

    /** The index of the texture to use for the shape. */
    public int textureID;

    /**
     * Creates a shape.
     * @param coords The coordinates of the vertices in the shape.
     * @param vertices The triangle indices for the shape.
     * @param textureCoords The texture coordinates for the shape's texture.
     * @param textureID The index of the texture to use for the shape.
     */
    public Shape(float[] coords, short[] vertices, float[] textureCoords, int textureID) {
        drawOrder = vertices;
        shapeCoords = coords;
        // 4 bytes per vertex.
        vertexStride = COORDS_PER_VERTEX * 4;

        // Initialize vertex byte buffer for shape coordinates.
        // (# of coordinate values * 4 bytes per float)
        vertexBuffer = makeFloatBuffer(shapeCoords);

        // Initialize byte buffer for the draw list.
        // (# of coordinate values * 2 bytes per short)
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        textureBuffer = makeFloatBuffer(textureCoords);

        this.textureID = textureID;
    }

    /**
     * Initializes the shape's texture if it is not initialized;
     */
    void initializeTexture() {
        if (textureDataHandle == -1) {
            textureDataHandle = GraphicsUtil.loadTexture(textureID);
        }
    }

    /**
     * Refreshes the shape's texture.
     */
    public void resetTexture() {
        textureDataHandle = -1;
    }

    /**
     * Loads a float buffer from a float array.
     * @param array The float array to load from.
     */
    private FloatBuffer makeFloatBuffer(float[] array) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(array.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = byteBuffer.asFloatBuffer();
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }
    /**
     * Draws the shape on the screen.
     * @param mvpMatrix The transformation matrix to move the shape with.
     */
    void draw(float[] mvpMatrix) {
        int shaderProgram = GraphicsUtil.sShaderProgram;
        // Add program to OpenGL ES environment.
        IGLWrapper gl = Factory.gl();

        gl.glUseProgram(shaderProgram);

        // Get handle to vertex shader's vPosition member.
        int positionHandle = gl.glGetAttribLocation(shaderProgram, "vPosition");

        // Enable a handle to the shape vertices.
        gl.glEnableVertexAttribArray(positionHandle);

        // Prepare the shape coordinate data.
        gl.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                IGLWrapper.GL_FLOAT, false, vertexStride, vertexBuffer, 0,
                vertexBuffer.capacity() * Float.SIZE);

        // Get handle to fragment shader's vColor member.
        int colorHandle = gl.glGetUniformLocation(shaderProgram, "vColor");

        int textureUniformHandle = gl.glGetUniformLocation(shaderProgram, "uTexture");
        int textureCoordinateHandle = gl.glGetAttribLocation(shaderProgram, "aTexCoordinate");

        // Set color for drawing the shape.
        gl.glUniform4fv(colorHandle, 1, COLOR, 0);

        // Set the active texture unit to texture unit 0.
        gl.glActiveTexture(IGLWrapper.GL_TEXTURE0);

        // Bind the texture to this unit.
        gl.glBindTexture(IGLWrapper.GL_TEXTURE_2D, textureDataHandle);

        // Tell the texture uniform sampler to use this texture in the shader
        // by binding to texture unit 0.
        gl.glUniform1i(textureUniformHandle, 0);

        textureBuffer.position(0);
        gl.glVertexAttribPointer(textureCoordinateHandle, COORDS_PER_TEXTURE,
                IGLWrapper.GL_FLOAT, false, 0, textureBuffer, 1,
                textureBuffer.capacity() * Float.SIZE);

        gl.glEnableVertexAttribArray(textureCoordinateHandle);

        // Get handle to shape's transformation matrix.
        int mvpMatrixHandle = gl.glGetUniformLocation(shaderProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader.
        gl.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the shape.
        gl.glDrawElements(IGLWrapper.GL_TRIANGLES, drawOrder.length, IGLWrapper.GL_UNSIGNED_SHORT,
                drawListBuffer, 2, drawOrder.length * Short.SIZE);

        // Disable arrays.
        gl.glDisableVertexAttribArray(positionHandle);
        gl.glDisableVertexAttribArray(textureCoordinateHandle);
    }
}
