package io.github.simcards.simcards.client.desktop;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import io.github.simcards.libcards.graphics.Camera;
import io.github.simcards.libcards.graphics.OtherGLRenderer;

/**
 * Listens for keyboard input on the desktop application.
 */
public class KeyboardListener implements KeyListener {

    /** The speed at which the camera is zoomed. */
    private static final float ZOOM_SPEED = 0.2f;

    /** The speed at which the camera is panned. */
    private static final float PAN_SPEED = 0.05f;

    @Override
    public void keyPressed(KeyEvent key) {
        float offsetX = 0;
        float offsetY = 0;
        float zoom = 0;
        short keyCode = key.getKeyCode();
        char keyChar = key.getKeyChar();
        Camera camera = OtherGLRenderer.sCamera;

        if (keyCode == 0x10 || keyChar == '-') {
            // Page up
            zoom += ZOOM_SPEED;
        } else if (keyCode == 0xb || keyChar == '+') {
            // Page down
            zoom -= ZOOM_SPEED;
        } else if (keyCode == 0x95) {
            // Left
            offsetX -= PAN_SPEED;
        } else if (keyCode == 0x96) {
            // Up
            offsetY += PAN_SPEED;
        } else if (keyCode == 0x97) {
            // Right
            offsetX += PAN_SPEED;
        } else if (keyCode == 0x98) {
            // Down
            offsetY -= PAN_SPEED;
        }

        if (zoom != 0) {
            camera.addScale(zoom);
        }
        if (offsetX != 0 || offsetY != 0) {
            camera.offsetPosition(offsetX, offsetY);
            OtherGLRenderer.rerender();
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
    }
}
