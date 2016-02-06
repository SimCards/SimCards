package io.github.simcards.simcards.client.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;

import io.github.simcards.simcards.client.graphics.Camera;
import io.github.simcards.simcards.client.graphics.GLRenderer;
import io.github.simcards.simcards.client.graphics.GLSurfaceViewWrapper;

/**
 * Listens for panning and zooming gestures.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    /** Multiplier for controlling the speed at which the screen is panned. */
    private static final float SPEED_BASE = 0.005f;

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Camera camera = GLRenderer.camera;
        float speed = -SPEED_BASE * camera.getZ() / Camera.INITIAL_Z;
        float offsetX = distanceX * speed;
        float offsetY = distanceY * speed;
        camera.offsetPosition(offsetX, offsetY);
        GLSurfaceViewWrapper.rerender();
        return true;
    }
}
