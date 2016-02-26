package io.github.simcards.simcards.client.desktop;

import io.github.simcards.libcards.graphics.GraphicsUtil;
import io.github.simcards.libcards.graphics.IGLWrapper;
import io.github.simcards.simcards.client.graphics.ResourceUtil;

import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.util.texture.TextureData;

import java.io.IOException;
import java.nio.Buffer;


public class DesktopGLWrapper implements IGLWrapper {
    /** The desktop GLES2.0 if the desktop application is being used. */
    public GL2ES2 desktopGL = null;

    /** Handles to use when referencing vertex buffers. */
    public int[] vboHandles = new int[3];

    /**
     * Sets the desktop GL instance and initializes needed buffers.
     * @param gl The GL instance.
     */
    public DesktopGLWrapper(GL2ES2 gl) {
        desktopGL = gl;
        desktopGL.glGenBuffers(3, vboHandles, 0);
    }

    public boolean isDesktopGL() {
        return true;
    }

    /**
     * Sets the active texture index.
     * @param texture The active texture index.
     */
    public void glActiveTexture(int texture) {
        desktopGL.glActiveTexture(texture);
    }

    /**
     * Attaches a shader to a shader program.
     * @param program The program to attach the shader to.
     * @param shader The shader to attach to the program.
     */
    public void glAttachShader(int program, int shader) {
        desktopGL.glAttachShader(program, shader);
    }

    /**
     * Binds a texture to a texture index.
     * @param target The texture index to bind to.
     * @param texture The texture to bind.
     */
    public void glBindTexture(int target, int texture) {
        desktopGL.glBindTexture(target, texture);
    }

    /**
     * Sets the background color.
     * @param mask A bitmask containing the background color.
     */
    public void glClear(int mask) {
        desktopGL.glClear(mask);
    }

    /**
     * Sets the background color.
     * @param red The red component of the color.
     * @param green The green component of the color.
     * @param blue The blue component of the color.
     * @param alpha The alpha component of the color.
     */
    public void glClearColor(float red, float green, float blue, float alpha) {
        desktopGL.glClearColor(red, green, blue, alpha);
    }

    /**
     * Compiles a shader.
     * @param shader The shader to compile.
     */
    public void glCompileShader(int shader) {
        desktopGL.glCompileShader(shader);
    }

    /**
     * Creates a shader program.
     * @return The created shader program.
     */
    public int glCreateProgram() {
        return desktopGL.glCreateProgram();
    }

    /**
     * Initializes a shader.
     * @param type The type of shader to initialize.
     * @return The initialized shader.
     */
    public int glCreateShader(int type) {
        return desktopGL.glCreateShader(type);
    }

    /**
     * Disables a vertex attribute.
     * @param index The vertex attribute to disable.
     */
    public void glDisableVertexAttribArray(int index) {
        desktopGL.glDisableVertexAttribArray(index);
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
    desktopGL.glBindBuffer(GL2ES2.GL_ELEMENT_ARRAY_BUFFER, vboHandles[bufferIndex]);
    desktopGL.glBufferData(GL2ES2.GL_ELEMENT_ARRAY_BUFFER, bufferSize, indices,
            GL2ES2.GL_STATIC_DRAW);
    desktopGL.glDrawElements(mode, count, type, 0);
    }

    /**
     * Enables a vertex attribute.
     * @param index The vertex attribute to enable.
     */
    public void glEnableVertexAttribArray(int index) {
        desktopGL.glEnableVertexAttribArray(index);
    }

    /**
     * Generates textures in a given array.
     * @param n The number of textures to generate.
     * @param textures The array to generate textures in.
     * @param offset The offset for the starting position in the array to generate textures at.
     */
    public void glGenTextures(int n, int[] textures, int offset) {
        desktopGL.glGenTextures(n, textures, offset);
    }

    /**
     * Gets a shader attribute.
     * @param program The program to get the shader attribute from.
     * @param name The name of the shader attribute to get.
     * @return The specified shader attribute.
     */
    public int glGetAttribLocation(int program, String name) {
        return desktopGL.glGetAttribLocation(program, name);
    }

    /**
     * Gets a uniform shader attribute.
     * @param program The program to get the shader attribute from.
     * @param name The name of the uniform shader attribute to get.
     * @return The specified uniform shader attribute.
     */
    public int glGetUniformLocation(int program, String name) {
        return desktopGL.glGetUniformLocation(program, name);
    }

    /**
     * Links a shader program to OpenGL.
     * @param program The program to link.
     */
    public void glLinkProgram(int program) {
        desktopGL.glLinkProgram(program);
    }

    /**
     * Loads shader code into a shader.
     * @param shader The shader to load code into.
     * @param string The shader code to load.
     */
    public void glShaderSource(int shader, String string) {
        desktopGL.glShaderSource(shader, 1, new String[]{string}, null);
    }

    /**
     * Sets the texture paramters for OpenGL.
     * @param target The type of the currently active texture.
     * @param pname The parameter to set.
     * @param param The value to set the parameter to.
     */
    public void glTexParameteri(int target, int pname, int param) {
        desktopGL.glTexParameteri(target, pname, param);
    }

    /**
     * Sets a uniform shader attribute.
     * @param location The attribute to set.
     * @param x The value to set the attribute to.
     */
    public void glUniform1i(int location, int x) {
        desktopGL.glUniform1i(location, x);
    }

    /**
     * Sets a uniform shader attribute.
     * @param location The attribute to set.
     * @param count The number of elements in the new value.
     * @param v The value to set the attribute to.
     * @param offset The offset of the starting index to set.
     */
    public void glUniform4fv(int location, int count, float[] v, int offset) {
        desktopGL.glUniform4fv(location, count, v, offset);
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
        desktopGL.glUniformMatrix4fv(location, count, transpose, value, offset);
    }

    /**
     * Sets a shader program to be used by OpenGL.
     * @param program The program to use.
     */
    public void glUseProgram(int program) {
        desktopGL.glUseProgram(program);
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
        desktopGL.glBindBuffer(GL2ES2.GL_ARRAY_BUFFER, vboHandles[bufferIndex]);
        desktopGL.glBufferData(GL2ES2.GL_ARRAY_BUFFER, bufferSize, ptr, GL2ES2.GL_STATIC_DRAW);
        desktopGL.glVertexAttribPointer(indx, size, type, normalized, stride, 0);
    }

    /**
     * Sets the viewport of the camera.
     * @param x The x coordinate of the viewport's center.
     * @param y The y coordinate of the viewport's center.
     * @param width The width of the viewport.
     * @param height The height of the viewport.
     */
    public void glViewport(int x, int y, int width, int height) {
        desktopGL.glViewport(x, y, width, height);
    }

    /**
     * Loads a texture into OpenGL.
     * @param target The texture to load the bitmap to.
     * @param level The detail level of the image.
     * @param resourceId The image file to load from
     * @param border 0.
     */
    public void texImage2D(int target, int level, int resourceId, int border) {
        try {
            TextureData texture = ResourceUtil.loadTexture(resourceId);
            desktopGL.glTexImage2D(target, level, texture.getInternalFormat(),
                    texture.getWidth(), texture.getHeight(), border, texture.getPixelFormat(),
                    texture.getPixelType(), texture.getBuffer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit(int vertexShader, int fragmentShader) {
        desktopGL.glUseProgram(0);
        desktopGL.glDeleteBuffers(3, vboHandles, 0);
        desktopGL.glDetachShader(GraphicsUtil.sShaderProgram, vertexShader);
        desktopGL.glDeleteShader(vertexShader);
        desktopGL.glDetachShader(GraphicsUtil.sShaderProgram, fragmentShader);
        desktopGL.glDeleteShader(fragmentShader);
        desktopGL.glDeleteProgram(GraphicsUtil.sShaderProgram);
    }
}

