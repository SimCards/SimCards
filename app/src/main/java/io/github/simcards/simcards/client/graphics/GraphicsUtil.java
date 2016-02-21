package io.github.simcards.simcards.client.graphics;

import android.content.res.Resources.NotFoundException;
import android.opengl.GLES20;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utility methods and constants for graphics.
 */
public class GraphicsUtil {
    /** The default shader program with no special effects. */
    public static int sShaderProgram;

    /** The width of the Android screen. */
    public static int screenWidth;
    /** The height of the Android screen. */
    public static int screenHeight;

    /**
     * Compiles a shader from shader code.
     * @param type The type of shader to create.
     *             GLES20.GL_VERTEX_SHADER or GLES20.GL_FRAGMENT_SHADER.
     * @param resource The shader file to load the shader code from.
     * @return The compiled shader.
     */
    public static int loadShader(int type, int resource) {
        String shaderCode = "";
        try {
            InputStream input = ResourceUtil.openRawResource(resource);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            shaderCode = new String(buffer);
        } catch (NotFoundException e) {
            System.out.println("Shader resource not found.");
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            System.out.println("Error reading shader resource.");
            e.printStackTrace();
            return -1;
        }

        int shader = GLWrapper.glCreateShader(type);

        // Add the source code to the shader and compile it.
        GLWrapper.glShaderSource(shader, shaderCode);
        GLWrapper.glCompileShader(shader);

        return shader;
    }

    /**
     * Binds a texture to the GL renderer.
     * @param resourceId The ID of the resource to bind.
     * @return The loaded texture.
     */
    public static int loadTexture(int resourceId) {
        int[] textureHandle = new int[1];

        GLWrapper.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0) {
            // Bind to the texture in OpenGL.
            GLWrapper.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering.
            GLWrapper.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLWrapper.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the image into the texture.
            GLWrapper.texImage2D(GLES20.GL_TEXTURE_2D, 0, resourceId, 0);
        } else {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }
}
