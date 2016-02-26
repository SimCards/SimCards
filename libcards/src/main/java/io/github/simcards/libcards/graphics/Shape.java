package io.github.simcards.simcards.client.graphics;

import android.opengl.GLES20;

import com.jogamp.common.nio.Buffers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * A polygon to be rendered on the screen.
 */
public class Shape {

    /** Buffer of vertices in the shape. */
    private FloatBuffer mVertexBuffer;
    /** Buffer of triangle indices in the shape. */
    private ShortBuffer mDrawListBuffer;
    /** Store our model data in a float buffer. */
    private FloatBuffer mTextureBuffer;
    /** The number of coordinates per vertex. */
    private final int COORDS_PER_VERTEX = 3;
    /** The coordinates of the vertices in the shape. */
    private float[] mShapeCoords;
    /** The triangle indices for the shape. */
    private short[] mDrawOrder;
    /** The number of bytes per vertex. */
    private int mVertexStride;

    /** The color of the shape. */
    private final float COLOR[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    /** Size of the texture coordinate data in elements. */
    private final int COORDS_PER_TEXTURE = 2;
    /** A handle to the shape's texture data. */
    private int mTextureDataHandle = -1;

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
        mDrawOrder = vertices;
        mShapeCoords = coords;
        // 4 bytes per vertex.
        mVertexStride = COORDS_PER_VERTEX * 4;

        // Initialize vertex byte buffer for shape coordinates.
        // (# of coordinate values * 4 bytes per float)
        mVertexBuffer = makeFloatBuffer(mShapeCoords);

        // Initialize byte buffer for the draw list.
        // (# of coordinate values * 2 bytes per short)
        ByteBuffer dlb = ByteBuffer.allocateDirect(mDrawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        mDrawListBuffer = dlb.asShortBuffer();
        mDrawListBuffer.put(mDrawOrder);
        mDrawListBuffer.position(0);

        mTextureBuffer = makeFloatBuffer(textureCoords);

        this.textureID = textureID;
    }

    /**
     * Initializes the shape's texture if it is not initialized;
     */
    void initializeTexture() {
        if (mTextureDataHandle == -1) {
            mTextureDataHandle = GraphicsUtil.loadTexture(textureID);
        }
    }

    /**
     * Refreshes the shape's texture.
     */
    public void resetTexture() {
        mTextureDataHandle = -1;
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
        GLWrapper.glUseProgram(shaderProgram);

        // Get handle to vertex shader's vPosition member.
        int positionHandle = GLWrapper.glGetAttribLocation(shaderProgram, "vPosition");

        // Enable a handle to the shape vertices.
        GLWrapper.glEnableVertexAttribArray(positionHandle);

        // Prepare the shape coordinate data.
        GLWrapper.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, mVertexStride, mVertexBuffer, 0,
                mVertexBuffer.capacity() * Buffers.SIZEOF_FLOAT);

        // Get handle to fragment shader's vColor member.
        int colorHandle = GLWrapper.glGetUniformLocation(shaderProgram, "vColor");

        int textureUniformHandle = GLWrapper.glGetUniformLocation(shaderProgram, "uTexture");
        int textureCoordinateHandle = GLWrapper.glGetAttribLocation(shaderProgram, "aTexCoordinate");

        // Set color for drawing the shape.
        GLWrapper.glUniform4fv(colorHandle, 1, COLOR, 0);

        // Set the active texture unit to texture unit 0.
        GLWrapper.glActiveTexture(GLES20.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLWrapper.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);

        // Tell the texture uniform sampler to use this texture in the shader
        // by binding to texture unit 0.
        GLWrapper.glUniform1i(textureUniformHandle, 0);

        mTextureBuffer.position(0);
        GLWrapper.glVertexAttribPointer(textureCoordinateHandle, COORDS_PER_TEXTURE,
                GLES20.GL_FLOAT, false, 0, mTextureBuffer, 1,
                mTextureBuffer.capacity() * Buffers.SIZEOF_FLOAT);
        Float.SIZE;

        GLWrapper.glEnableVertexAttribArray(textureCoordinateHandle);

        // Get handle to shape's transformation matrix.
        int mvpMatrixHandle = GLWrapper.glGetUniformLocation(shaderProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader.
        GLWrapper.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the shape.
        GLWrapper.glDrawElements(GLES20.GL_TRIANGLES, mDrawOrder.length, GLES20.GL_UNSIGNED_SHORT,
                mDrawListBuffer, 2, mDrawOrder.length * Buffers.SIZEOF_SHORT);

        // Disable arrays.
        GLWrapper.glDisableVertexAttribArray(positionHandle);
        GLWrapper.glDisableVertexAttribArray(textureCoordinateHandle);
    }
}
