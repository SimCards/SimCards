package io.github.simcards.simcards.client.graphics;

import java.io.IOException;
import java.io.InputStream;

import io.github.simcards.libcards.graphics.IGLWrapper;
import io.github.simcards.libcards.util.Factory;

public class AndroidGraphicsUtil {
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
        } catch (IOException e) {
            System.out.println("Error reading shader resource.");
            e.printStackTrace();
            return -1;
        }

        IGLWrapper gl = Factory.gl();

        int shader = gl.glCreateShader(type);

        // Add the source code to the shader and compile it.
        gl.glShaderSource(shader, shaderCode);
        gl.glCompileShader(shader);

        return shader;
    }
}
