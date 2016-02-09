package io.github.simcards.simcards.client.graphics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utility methods and constants for graphics.
 */
public class GraphicsUtil {

    /** The resources for the app. */
    public static Resources sResources;
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
            InputStream input = sResources.openRawResource(resource);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            shaderCode = new String(buffer);
        } catch (Resources.NotFoundException e) {
            System.out.println("Shader resource not found.");
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            System.out.println("Error reading shader resource.");
            e.printStackTrace();
            return -1;
        }
        int shader = GLES20.glCreateShader(type);

        // Add the source code to the shader and compile it.
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Binds a texture to the GL renderer.
     * @param resourceId The ID of the resource to bind.
     * @return The loaded texture.
     */
    public static int loadTexture(int resourceId) {
        int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // No pre-scaling.
            options.inScaled = false;

            // Read in the resource.
            Bitmap bitmap = BitmapFactory.decodeResource(sResources, resourceId, options);

            // Bind to the texture in OpenGL.
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering.
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        } else {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }
}
