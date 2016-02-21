package io.github.simcards.simcards.client.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.util.texture.TextureData;

import java.io.IOException;
import java.nio.Buffer;

/**
 * Wraps GLES2.0 functions to take care of differences between Android and desktop.
 */
public class GLWrapper {

    /** The desktop GLES2.0 if the desktop application is being used. */
    public static GL2ES2 desktopGL = null;

    /** Handles to use when referencing vertex buffers. */
    public static int[] vboHandles = new int[3];

    /**
     * Sets the desktop GL instance and initializes needed buffers.
     * @param gl The GL instance.
     */
    public static void setGL(GL2ES2 gl) {
        desktopGL = gl;
        desktopGL.glGenBuffers(3, vboHandles, 0);
    }

    /**
     * Sets the active texture index.
     * @param texture The active texture index.
     */
    public static void glActiveTexture(int texture) {
        if (desktopGL == null) {
            GLES20.glActiveTexture(texture);
        } else {
            desktopGL.glActiveTexture(texture);
        }
    }

    /**
     * Attaches a shader to a shader program.
     * @param program The program to attach the shader to.
     * @param shader The shader to attach to the program.
     */
    public static void glAttachShader(int program, int shader) {
        if (desktopGL == null) {
            GLES20.glAttachShader(program, shader);
        } else {
            desktopGL.glAttachShader(program, shader);
        }
    }

    /**
     * Binds a texture to a texture index.
     * @param target The texture index to bind to.
     * @param texture The texture to bind.
     */
    public static void glBindTexture(int target, int texture) {
        if (desktopGL == null) {
            GLES20.glBindTexture(target, texture);
        } else {
            desktopGL.glBindTexture(target, texture);
        }
    }

    /**
     * Sets the background color.
     * @param mask A bitmask containing the background color.
     */
    public static void glClear(int mask) {
        if (desktopGL == null) {
            GLES20.glClear(mask);
        } else {
            desktopGL.glClear(mask);
        }
    }

    /**
     * Sets the background color.
     * @param red The red component of the color.
     * @param green The green component of the color.
     * @param blue The blue component of the color.
     * @param alpha The alpha component of the color.
     */
    public static void glClearColor(float red, float green, float blue, float alpha) {
        if (desktopGL == null) {
            GLES20.glClearColor(red, green, blue, alpha);
        } else {
            desktopGL.glClearColor(red, green, blue, alpha);
        }
    }

    /**
     * Compiles a shader.
     * @param shader The shader to compile.
     */
    public static void glCompileShader(int shader) {
        if (desktopGL == null) {
            GLES20.glCompileShader(shader);
        } else {
            desktopGL.glCompileShader(shader);
        }
    }

    /**
     * Creates a shader program.
     * @return The created shader program.
     */
    public static int glCreateProgram() {
        return desktopGL == null ? GLES20.glCreateProgram() : desktopGL.glCreateProgram();
    }

    /**
     * Initializes a shader.
     * @param type The type of shader to initialize.
     * @return The initialized shader.
     */
    public static int glCreateShader(int type) {
        return desktopGL == null ? GLES20.glCreateShader(type) : desktopGL.glCreateShader(type);
    }

    /**
     * Disables a vertex attribute.
     * @param index The vertex attribute to disable.
     */
    public static void glDisableVertexAttribArray(int index) {
        if (desktopGL == null) {
            GLES20.glDisableVertexAttribArray(index);
        } else {
            desktopGL.glDisableVertexAttribArray(index);
        }
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
    public static void glDrawElements(int mode, int count, int type,
                                      Buffer indices, int bufferIndex, int bufferSize) {
        if (desktopGL == null) {
            GLES20.glDrawElements(mode, count, type, indices);
        } else {
            desktopGL.glBindBuffer(GL2ES2.GL_ELEMENT_ARRAY_BUFFER, vboHandles[bufferIndex]);
            desktopGL.glBufferData(GL2ES2.GL_ELEMENT_ARRAY_BUFFER, bufferSize, indices,
                    GL2ES2.GL_STATIC_DRAW);
            desktopGL.glDrawElements(mode, count, type, 0);
        }
    }

    /**
     * Enables a vertex attribute.
     * @param index The vertex attribute to enable.
     */
    public static void glEnableVertexAttribArray(int index) {
        if (desktopGL == null) {
            GLES20.glEnableVertexAttribArray(index);
        } else {
            desktopGL.glEnableVertexAttribArray(index);
        }
    }

    /**
     * Generates textures in a given array.
     * @param n The number of textures to generate.
     * @param textures The array to generate textures in.
     * @param offset The offset for the starting position in the array to generate textures at.
     */
    public static void glGenTextures(int n, int[] textures, int offset) {
        if (desktopGL == null) {
            GLES20.glGenTextures(n, textures, offset);
        } else {
            desktopGL.glGenTextures(n, textures, offset);
        }
    }

    /**
     * Gets a shader attribute.
     * @param program The program to get the shader attribute from.
     * @param name The name of the shader attribute to get.
     * @return The specified shader attribute.
     */
    public static int glGetAttribLocation(int program, String name) {
        return desktopGL == null ? GLES20.glGetAttribLocation(program, name) :
                desktopGL.glGetAttribLocation(program, name);
    }

    /**
     * Gets a uniform shader attribute.
     * @param program The program to get the shader attribute from.
     * @param name The name of the uniform shader attribute to get.
     * @return The specified uniform shader attribute.
     */
    public static int glGetUniformLocation(int program, String name) {
        return desktopGL == null ? GLES20.glGetUniformLocation(program, name) :
                desktopGL.glGetUniformLocation(program, name);
    }

    /**
     * Links a shader program to OpenGL.
     * @param program The program to link.
     */
    public static void glLinkProgram(int program) {
        if (desktopGL == null) {
            GLES20.glLinkProgram(program);
        } else {
            desktopGL.glLinkProgram(program);
        }
    }

    /**
     * Loads shader code into a shader.
     * @param shader The shader to load code into.
     * @param string The shader code to load.
     */
    public static void glShaderSource(int shader, String string) {
        if (desktopGL == null) {
            GLES20.glShaderSource(shader, string);
        } else {
            desktopGL.glShaderSource(shader, 1, new String[]{string}, null);
        }
    }

    /**
     * Sets the texture paramters for OpenGL.
     * @param target The type of the currently active texture.
     * @param pname The parameter to set.
     * @param param The value to set the parameter to.
     */
    public static void glTexParameteri(int target, int pname, int param) {
        if (desktopGL == null) {
            GLES20.glTexParameteri(target, pname, param);
        } else {
            desktopGL.glTexParameteri(target, pname, param);
        }
    }

    /**
     * Sets a uniform shader attribute.
     * @param location The attribute to set.
     * @param x The value to set the attribute to.
     */
    public static void glUniform1i(int location, int x) {
        if (desktopGL == null) {
            GLES20.glUniform1i(location, x);
        } else {
            desktopGL.glUniform1i(location, x);
        }
    }

    /**
     * Sets a uniform shader attribute.
     * @param location The attribute to set.
     * @param count The number of elements in the new value.
     * @param v The value to set the attribute to.
     * @param offset The offset of the starting index to set.
     */
    public static void glUniform4fv(int location, int count, float[] v, int offset) {
        if (desktopGL == null) {
            GLES20.glUniform4fv(location, count, v, offset);
        } else {
            desktopGL.glUniform4fv(location, count, v, offset);
        }
    }

    /**
     * Sets a matrix attribute.
     * @param location The matrix attribute to set.
     * @param count The number of elements in the array of matrices.
     * @param transpose Whether to transpose the matrix.
     * @param value The value to set the matrix to.
     * @param offset The offset of the starting index to set in the array of matrices.
     */
    public static void glUniformMatrix4fv(int location, int count, boolean transpose,
                                                 float[] value, int offset) {
        if (desktopGL == null) {
            GLES20.glUniformMatrix4fv(location, count, transpose, value, offset);
        } else {
            desktopGL.glUniformMatrix4fv(location, count, transpose, value, offset);
        }
    }

    /**
     * Sets a shader program to be used by OpenGL.
     * @param program The program to use.
     */
    public static void glUseProgram(int program) {
        if (desktopGL == null) {
            GLES20.glUseProgram(program);
        } else {
            desktopGL.glUseProgram(program);
        }
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
    public static void glVertexAttribPointer(int indx, int size, int type, boolean normalized,
                                             int stride, Buffer ptr, int bufferIndex, int bufferSize) {
        if (desktopGL == null) {
            GLES20.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
        } else {
            desktopGL.glBindBuffer(GL2ES2.GL_ARRAY_BUFFER, vboHandles[bufferIndex]);
            desktopGL.glBufferData(GL2ES2.GL_ARRAY_BUFFER, bufferSize, ptr, GL2ES2.GL_STATIC_DRAW);
            desktopGL.glVertexAttribPointer(indx, size, type, normalized, stride, 0);
        }
    }

    /**
     * Sets the viewport of the camera.
     * @param x The x coordinate of the viewport's center.
     * @param y The y coordinate of the viewport's center.
     * @param width The width of the viewport.
     * @param height The height of the viewport.
     */
    public static void glViewport(int x, int y, int width, int height) {
        if (desktopGL == null) {
            GLES20.glViewport(x, y, width, height);
        } else {
            desktopGL.glViewport(x, y, width, height);
        }
    }

    /**
     * Loads a texture into OpenGL.
     * @param target The texture to load the bitmap to.
     * @param level The detail level of the image.
     * @param resourceId The image file to load from
     * @param border 0.
     */
    public static void texImage2D(int target, int level, int resourceId, int border) {
        if (desktopGL == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // No pre-scaling.
            options.inScaled = false;

            // Read in the resource.
            Bitmap bitmap = BitmapFactory.decodeResource(ResourceUtil.sResources, resourceId, options);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(target, level, bitmap, border);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        } else {
            try {
                TextureData texture = ResourceUtil.loadTexture(resourceId);
                desktopGL.glTexImage2D(target, level, texture.getInternalFormat(),
                        texture.getWidth(), texture.getHeight(), border, texture.getPixelFormat(),
                        texture.getPixelType(), texture.getBuffer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
