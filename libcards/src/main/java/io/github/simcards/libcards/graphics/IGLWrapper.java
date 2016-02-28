package io.github.simcards.libcards.graphics;

import java.nio.Buffer;

/**
 * Wraps GLES2.0 functions to take care of differences between Android and desktop.
 */
public interface IGLWrapper {

    /** Indicates that the color buffer should be cleared. */
    int GL_COLOR_BUFFER_BIT = 0x00004000;
    /** Indicates that elements should be drawn as triangles using indices. */
    int GL_TRIANGLES = 0x0004;
    /** Indicates that the texture should be loaded to 2D. */
    int GL_TEXTURE_2D = 0x0DE1;
    /** Indicates that the data type is an unsigned short. */
    int GL_UNSIGNED_SHORT = 0x1403;
    /** Indicates that the data type is a float. */
    int GL_FLOAT = 0x1406;
    /** Indicates that the fragment shader should be loaded. */
    int GL_FRAGMENT_SHADER = 0x8B30;
    /** Indicates that the vertex shader should be loaded. */
    int GL_VERTEX_SHADER = 0x8B31;
    /** Indicates that the nearest neighbor should be used for interpolation. */
    int GL_NEAREST = 0x2600;
    /** Indicates that the texture magnification filter should be modified. */
    int GL_TEXTURE_MAG_FILTER = 0x2800;
    /** Indicates that the texture minification filter should be modified. */
    int GL_TEXTURE_MIN_FILTER = 0x2801;
    /** The 0th texture location. */
    int GL_TEXTURE0 = 0x84C0;

    /**
     * Checks whether the application is on desktop or Android.
     * @return True if the application is on desktop, false if on Android.
     */
    boolean isDesktopGL();

    /**
     * Sets the active texture index.
     * @param texture The active texture index.
     */
    void glActiveTexture(int texture);

    /**
     * Attaches a shader to a shader program.
     * @param program The program to attach the shader to.
     * @param shader The shader to attach to the program.
     */
    void glAttachShader(int program, int shader);

    /**
     * Binds a texture to a texture index.
     * @param target The texture index to bind to.
     * @param texture The texture to bind.
     */
    void glBindTexture(int target, int texture);

    /**
     * Sets the background color.
     * @param mask A bitmask containing the background color.
     */
    void glClear(int mask);

    /**
     * Sets the background color.
     * @param red The red component of the color.
     * @param green The green component of the color.
     * @param blue The blue component of the color.
     * @param alpha The alpha component of the color.
     */
    void glClearColor(float red, float green, float blue, float alpha);

    /**
     * Compiles a shader.
     * @param shader The shader to compile.
     */
    void glCompileShader(int shader);

    /**
     * Creates a shader program.
     * @return The created shader program.
     */
    int glCreateProgram();

    /**
     * Initializes a shader.
     * @param type The type of shader to initialize.
     * @return The initialized shader.
     */
    int glCreateShader(int type);

    /**
     * Disables a vertex attribute.
     * @param index The vertex attribute to disable.
     */
    void glDisableVertexAttribArray(int index);

    /**
     * Draws vertices on the screen.
     * @param mode The drawing mode to use.
     * @param count The number of vertices to draw.
     * @param type The primitive data type of the vertices.
     * @param indices The vertices to draw.
     * @param bufferIndex The index of the buffer handle to use.
     * @param bufferSize The size of the triangle buffer.
     */
    void glDrawElements(int mode, int count, int type,
                        Buffer indices, int bufferIndex, int bufferSize);

    /**
     * Enables a vertex attribute.
     * @param index The vertex attribute to enable.
     */
    void glEnableVertexAttribArray(int index);

    /**
     * Generates textures in a given array.
     * @param n The number of textures to generate.
     * @param textures The array to generate textures in.
     * @param offset The offset for the starting position in the array to generate textures at.
     */
    void glGenTextures(int n, int[] textures, int offset);

    /**
     * Gets a shader attribute.
     * @param program The program to get the shader attribute from.
     * @param name The name of the shader attribute to get.
     * @return The specified shader attribute.
     */
    int glGetAttribLocation(int program, String name);

    /**
     * Gets a uniform shader attribute.
     * @param program The program to get the shader attribute from.
     * @param name The name of the uniform shader attribute to get.
     * @return The specified uniform shader attribute.
     */
    int glGetUniformLocation(int program, String name);

    /**
     * Links a shader program to OpenGL.
     * @param program The program to link.
     */
    void glLinkProgram(int program);

    /**
     * Loads shader code into a shader.
     * @param shader The shader to load code into.
     * @param string The shader code to load.
     */
    void glShaderSource(int shader, String string);

    /**
     * Sets the texture paramters for OpenGL.
     * @param target The type of the currently active texture.
     * @param pname The parameter to set.
     * @param param The value to set the parameter to.
     */
    void glTexParameteri(int target, int pname, int param);

    /**
     * Sets a uniform shader attribute.
     * @param location The attribute to set.
     * @param x The value to set the attribute to.
     */
    void glUniform1i(int location, int x);

    /**
     * Sets a uniform shader attribute.
     * @param location The attribute to set.
     * @param count The number of elements in the new value.
     * @param v The value to set the attribute to.
     * @param offset The offset of the starting index to set.
     */
    void glUniform4fv(int location, int count, float[] v, int offset);

    /**
     * Sets a matrix attribute.
     * @param location The matrix attribute to set.
     * @param count The number of elements in the array of matrices.
     * @param transpose Whether to transpose the matrix.
     * @param value The value to set the matrix to.
     * @param offset The offset of the starting index to set in the array of matrices.
     */
    void glUniformMatrix4fv(int location, int count, boolean transpose,
                            float[] value, int offset);

    /**
     * Sets a shader program to be used by OpenGL.
     * @param program The program to use.
     */
    void glUseProgram(int program);

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
    void glVertexAttribPointer(int indx, int size, int type, boolean normalized,
                               int stride, Buffer ptr, int bufferIndex, int bufferSize);

    /**
     * Sets the viewport of the camera.
     * @param x The x coordinate of the viewport's center.
     * @param y The y coordinate of the viewport's center.
     * @param width The width of the viewport.
     * @param height The height of the viewport.
     */
    void glViewport(int x, int y, int width, int height);

    /**
     * Loads a texture into OpenGL.
     * @param target The texture to load the bitmap to.
     * @param level The detail level of the image.
     * @param resourceId The image file to load from
     * @param border 0.
     */
    void texImage2D(int target, int level, int resourceId, int border);

    /**
     * Cleans up OpenGL after closing the desktop application.
     */
    void exit(int vertexShader, int fragmentShader);
}
