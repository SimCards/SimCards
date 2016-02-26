package io.github.simcards.simcards.client.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;

import io.github.simcards.libcards.graphics.Camera;
import io.github.simcards.libcards.graphics.OtherGLRenderer;
import io.github.simcards.simcards.client.graphics.GLRenderer;
import io.github.simcards.libcards.graphics.GraphicsUtil;
import io.github.simcards.libcards.game.Environment;
import io.github.simcards.libcards.util.Position;

/**
 * Listens for touch gestures.
 */
public class TouchListener extends GestureDetector.SimpleOnGestureListener {

    /** Multiplier for controlling the speed at which the screen is panned. */
    private static final float PAN_SPEED = 0.0026f;

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Environment environment = Environment.getEnvironment();
        environment.touch(new Position(event.getX(), event.getY()));
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Camera camera = OtherGLRenderer.sCamera;
        float speed = PAN_SPEED * 800 / GraphicsUtil.screenHeight * OtherGLRenderer.sCamera.scale;
        float offsetX = distanceX * speed;
        float offsetY = -distanceY * speed;
        camera.offsetPosition(offsetX, offsetY);
        OtherGLRenderer.rerender();
        return false;
    }
}
