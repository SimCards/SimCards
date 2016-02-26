package io.github.simcards.simcards.client.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.util.texture.TextureData;

import java.io.IOException;
import java.nio.Buffer;

import io.github.simcards.libcards.graphics.IGLWrapper;

/**
 * Wraps GLES2.0 functions to take care of differences between Android and desktop.
 */
public class AndroidGLWrapper implements IGLWrapper {


    public boolean isDesktopGL() {
        return false;
    }

    /**
     * Sets the active texture index.
     * @param texture The active texture index.
     */
    public void glActiveTexture(int texture) {
        GLES20.glActiveTexture(texture);
    }

    /**
     * Attaches a shader to a shader program.
     * @param program The program to attach the shader to.
     * @param shader The shader to attach to the program.
     */
    public void glAttachShader(int program, int shader) {
        GLES20.glAttachShader(program, shader);

    }

    /**
     * Binds a texture to a texture index.
     * @param target The texture index to bind to.
     * @param texture The texture to bind.
     */
    public void glBindTexture(int target, int texture) {
        GLES20.glBindTexture(target, texture);
    }

    /**
     * Sets the background color.
     * @param mask A bitmask containing the background color.
     */
    public void glClear(int mask) {
        GLES20.glClear(mask);
    }

    /**
     * Sets the background color.
     * @param red The red component of the color.
     * @param green The green component of the color.
     * @param blue The blue component of the color.
     * @param alpha The alpha component of the color.
     */
    public void glClearColor(float red, float green, float blue, float alpha) {
        GLES20.glClearColor(red, green, blue, alpha);
    }

    /**
     * Compiles a shader.
     * @param shader The shader to compile.
     */
    public void glCompileShader(int shader) {
        GLES20.glCompileShader(shader);
    }

    /**
     * Creates a shader program.
     * @return The created shader program.
     */
    public int glCreateProgram() {
        return GLES20.glCreateProgram();
    }

    /**
     * Initializes a shader.
     * @param type The type of shader to initialize.
     * @return The initialized shader.
     */
    public int glCreateShader(int type) {
        return GLES20.glCreateShader(type);
    }

    /**
     * Disables a vertex attribute.
     * @param index The vertex attribute to disable.
     */
    public void glDisableVertexAttribArray(int index) {
        GLES20.glDisableVertexAttribArray(index);
    }

    /**
     * Draws vertices on the screen.
     * @param mode The drawing mode to use.
     * @param count The number of vertices to draw.
     * @param type The primitive data type of the vertices.
     * @param indices The vertices to draw.
     * @param bufferIndex The index of the buffer handle to use.
     * @param bufferSize The size of the triangle buffer.
     */
    public void glDrawElements(int mode, int count, int type,
                                      Buffer indices, int bufferIndex, int bufferSize) {
        GLES20.glDrawElements(mode, count, type, indices);
    }

    /**
     * Enables a vertex attribute.
     * @param index The vertex attribute to enable.
     */
    public void glEnableVertexAttribArray(int index) {
        GLES20.glEnableVertexAttribArray(index);
    }

    /**
     * Generates textures in a given array.
     * @param n The number of textures to generate.
     * @param textures The array to generate textures in.
     * @param offset The offset for the starting position in the array to generate textures at.
     */
    public void glGenTextures(int n, int[] textures, int offset) {
        GLES20.glGenTextures(n, textures, offset);
    }

    /**
     * Gets a shader attribute.
     * @param program The program to get the shader attribute from.
     * @param name The name of the shader attribute to get.
     * @return The specified shader attribute.
     */
    public int glGetAttribLocation(int program, String name) {
        return GLES20.glGetAttribLocation(program, name);
    }

    /**
     * Gets a uniform shader attribute.
     * @param program The program to get the shader attribute from.
     * @param name The name of the uniform shader attribute to get.
     * @return The specified uniform shader attribute.
     */
    public int glGetUniformLocation(int program, String name) {
        return GLES20.glGetUniformLocation(program, name);
    }

    /**
     * Links a shader program to OpenGL.
     * @param program The program to link.
     */
    public void glLinkProgram(int program) {
        GLES20.glLinkProgram(program);
    }

    /**
     * Loads shader code into a shader.
     * @param shader The shader to load code into.
     * @param string The shader code to load.
     */
    public void glShaderSource(int shader, String string) {
        GLES20.glShaderSource(shader, string);
    }

    /**
     * Sets the texture paramters for OpenGL.
     * @param target The type of the currently active texture.
     * @param pname The parameter to set.
     * @param param The value to set the parameter to.
     */
    public void glTexParameteri(int target, int pname, int param) {
        GLES20.glTexParameteri(target, pname, param);
    }

    /**
     * Sets a uniform shader attribute.
     * @param location The attribute to set.
     * @param x The value to set the attribute to.
     */
    public void glUniform1i(int location, int x) {
        GLES20.glUniform1i(location, x);
    }

    /**
     * Sets a uniform shader attribute.
     * @param location The attribute to set.
     * @param count The number of elements in the new value.
     * @param v The value to set the attribute to.
     * @param offset The offset of the starting index to set.
     */
    public void glUniform4fv(int location, int count, float[] v, int offset) {
        GLES20.glUniform4fv(location, count, v, offset);
    }

    /**
     * Sets a matrix attribute.
     * @param location The matrix attribute to set.
     * @param count The number of elements in the array of matrices.
     * @param transpose Whether to transpose the matrix.
     * @param value The value to set the matrix to.
     * @param offset The offset of the starting index to set in the array of matrices.
     */
    public void glUniformMatrix4fv(int location, int count, boolean transpose,
                                                 float[] value, int offset) {
        GLES20.glUniformMatrix4fv(location, count, transpose, value, offset);
    }

    /**
     * Sets a shader program to be used by OpenGL.
     * @param program The program to use.
     */
    public void glUseProgram(int program) {
        GLES20.glUseProgram(program);
    }

    /**
     * Sets the initial vertices in the vertex shader.
     * @param indx The vertex attribute to use.
     * @param size The number of coordinates per vertex.
     * @param type The type of primitive that the vertices are stored as.
     * @param normalized Whether the vertices are normalized.
     * @param stride The number of bytes per vertex.
     * @param ptr The vertex array.
     * @param bufferIndex The index of the buffer handle to use.
     * @param bufferSize The size of the vertex array in bytes.
     */
    public void glVertexAttribPointer(int indx, int size, int type, boolean normalized,
                                             int stride, Buffer ptr, int bufferIndex, int bufferSize) {
        GLES20.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
    }

    /**
     * Sets the viewport of the camera.
     * @param x The x coordinate of the viewport's center.
     * @param y The y coordinate of the viewport's center.
     * @param width The width of the viewport.
     * @param height The height of the viewport.
     */
    public void glViewport(int x, int y, int width, int height) {
        GLES20.glViewport(x, y, width, height);
    }

    /**
     * Loads a texture into OpenGL.
     * @param target The texture to load the bitmap to.
     * @param level The detail level of the image.
     * @param resourceId The image file to load from
     * @param border 0.
     */
    public void texImage2D(int target, int level, int resourceId, int border) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // No pre-scaling.
        options.inScaled = false;

        // Read in the resource.
        Bitmap bitmap = BitmapFactory.decodeResource(ResourceUtil.sResources, resourceId, options);

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(target, level, bitmap, border);

        // Recycle the bitmap, since its data has been loaded into OpenGL.
        bitmap.recycle();
    }

    /**
     * Cleans up OpenGL after closing the desktop application.
     */
    public void exit(int vertexShader, int fragmentShader) {
        throw new UnsupportedOperationException("Android OpenGL cannot be exited");

    }
}
