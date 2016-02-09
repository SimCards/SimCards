package io.github.simcards.simcards.client.ui;

import android.view.ScaleGestureDetector;

import io.github.simcards.simcards.client.graphics.Camera;
import io.github.simcards.simcards.client.graphics.GLRenderer;

/**
 * Listens for zoom gestures.
 */
public class ZoomListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    /** Multiplier for controlling the speed at which the screen is zoomed. */
    private static final float ZOOM_SPEED = 0.5f;

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Camera camera = GLRenderer.sCamera;
        float cameraZ = camera.scale;
        float speed = cameraZ * detector.getScaleFactor() - cameraZ;
        speed *= ZOOM_SPEED;
        camera.offsetScale(speed);

        return false;
    }
}
