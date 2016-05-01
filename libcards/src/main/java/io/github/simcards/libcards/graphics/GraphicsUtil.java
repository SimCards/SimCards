package io.github.simcards.libcards.graphics;

import io.github.simcards.libcards.util.Factory;

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
     * Binds a texture to the GL renderer.
     * @param resourceId The ID of the resource to bind.
     * @return The loaded texture.
     */
    public static int loadTexture(int resourceId) {
        int[] textureHandle = new int[1];

        IGLWrapper gl = Factory.gl();

        gl.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0) {
            // Bind to the texture in OpenGL.
            gl.glBindTexture(IGLWrapper.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering.
            gl.glTexParameteri(IGLWrapper.GL_TEXTURE_2D, IGLWrapper.GL_TEXTURE_MIN_FILTER, IGLWrapper.GL_NEAREST);
            gl.glTexParameteri(IGLWrapper.GL_TEXTURE_2D, IGLWrapper.GL_TEXTURE_MAG_FILTER, IGLWrapper.GL_NEAREST);

            // Load the image into the texture.
            gl.texImage2D(IGLWrapper.GL_TEXTURE_2D, 0, resourceId, 0);
        } else {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }

    /**
     * Gets the width of the screen in world coordinates.
     * @return The width of the screen in world coordinates.
     */
    public static float getWorldScreenWidth() {
        return (float)GraphicsUtil.screenWidth / GraphicsUtil.screenHeight * 2;
    }
}
