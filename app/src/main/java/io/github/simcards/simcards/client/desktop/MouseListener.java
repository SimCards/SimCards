package io.github.simcards.simcards.client.desktop;

import com.jogamp.newt.event.MouseEvent;

import io.github.simcards.simcards.client.graphics.Camera;
import io.github.simcards.simcards.client.graphics.GLRenderer;
import io.github.simcards.simcards.client.graphics.GraphicsUtil;
import io.github.simcards.simcards.game.Environment;
import io.github.simcards.simcards.util.Position;

/**
 * Listens for mouse input on the desktop application.
 */
public class MouseListener implements com.jogamp.newt.event.MouseListener {

    /** Multiplier for controlling the speed at which the screen is panned. */
    private static final float PAN_SPEED = 0.0026f;

    /** The speed at which the camera is zoomed. */
    private static final float ZOOM_SPEED = 0.05f;

    /** The position of the last mouse press. */
    int lastX, lastY;

    @Override
    public void mouseClicked(MouseEvent event) {
        Environment environment = Environment.getEnvironment();
        environment.touch(new Position(event));
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        int mouseX = event.getX();
        int mouseY = event.getY();

        int deltaX = mouseX - lastX;
        int deltaY = mouseY - lastY;

        Camera camera = GLRenderer.sCamera;
        float speed = PAN_SPEED * 800 / GraphicsUtil.screenHeight * GLRenderer.sCamera.scale;
        float offsetX = -deltaX * speed;
        float offsetY = deltaY * speed;
        camera.offsetPosition(offsetX, offsetY);
        GLRenderer.rerender();

        lastX = event.getX();
        lastY = event.getY();
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void mouseMoved(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        lastX = event.getX();
        lastY = event.getY();
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mouseWheelMoved(MouseEvent event) {
        GLRenderer.sCamera.addScale(-ZOOM_SPEED * event.getRotation()[1]);
    }
}
