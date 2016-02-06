package io.github.simcards.simcards.client.graphics;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import io.github.simcards.simcards.client.util.GraphicsUtil;

/**
 * A polygon to be rendered on the screen.
 */
public class Shape {

    /** Buffer of vertices in the shape. */
    protected FloatBuffer vertexBuffer;
    /** Buffer of triangle indices in the shape. */
    protected ShortBuffer drawListBuffer;
    /** Store our model data in a float buffer. */
    protected FloatBuffer textureBuffer;
    /** The number of coordinates per vertex. */
    protected final int COORDS_PER_VERTEX = 3;
    /** The coordinates of the vertices in the shape. */
    protected float[] shapeCoords;
    /** The triangle indices for the shape. */
    protected short[] drawOrder;
    /** The number of vertices in the shape. */
    protected int vertexCount;
    /** The number of bytes per vertex. */
    protected int vertexStride;

    /** The color of the shape. */
    protected final float color[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    /** Size of the texture coordinate data in elements. */
    protected final int COORDS_PER_TEXTURE = 2;
    /** A handle to the shape's texture data. */
    protected int textureDataHandle;

    /**
     * Creates a shape.
     * @param coords The coordinates of the vertices in the shape.
     * @param vertices The triangle indices for the shape.
     * @param textureCoords The texture coordinates for the shape's texture.
     * @param textureID The texture to use for the shape.
     */
    public Shape(float[] coords, short[] vertices, float[] textureCoords, int textureID) {
        drawOrder = vertices;
        shapeCoords = coords;
        vertexCount = shapeCoords.length / COORDS_PER_VERTEX;
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

        textureDataHandle = GraphicsUtil.loadTexture(textureID);
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
    public void draw(float[] mvpMatrix) {
        int shaderProgram = GraphicsUtil.shaderProgram;
        // Add program to OpenGL ES environment.
        GLES20.glUseProgram(shaderProgram);

        // Get handle to vertex shader's vPosition member.
        int positionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");

        // Enable a handle to the shape vertices.
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the shape coordinate data.
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        // Get handle to fragment shader's vColor member.
        int colorHandle = GLES20.glGetUniformLocation(shaderProgram, "vColor");

        int textureUniformHandle = GLES20.glGetUniformLocation(shaderProgram, "uTexture");
        int textureCoordinateHandle = GLES20.glGetAttribLocation(shaderProgram, "aTexCoordinate");

        // Set color for drawing the shape.
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureDataHandle);

        // Tell the texture uniform sampler to use this texture in the shader
        // by binding to texture unit 0.
        GLES20.glUniform1i(textureUniformHandle, 0);

        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(textureCoordinateHandle, COORDS_PER_TEXTURE, GLES20.GL_FLOAT, false,
                0, textureBuffer);

        GLES20.glEnableVertexAttribArray(textureCoordinateHandle);

        // Get handle to shape's transformation matrix.
        int mvpMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader.
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the shape.
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT,
                drawListBuffer);

        // Disable vertex array.
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
