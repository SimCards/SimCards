package io.github.simcards.simcards.client.ui;

import android.view.ScaleGestureDetector;

import io.github.simcards.libcards.graphics.OtherGLRenderer;
import io.github.simcards.simcards.client.graphics.GLRenderer;

/**
 * Listens for zoom gestures.
 */
public class ZoomListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    /** Multiplier for controlling the speed at which the screen is zoomed. */
    private static final float ZOOM_SPEED = 1;

    /** The initial camera scale when scaling began. */
    private float initScale;

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        initScale = OtherGLRenderer.sCamera.scale;
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float newScale = initScale * detector.getScaleFactor() * ZOOM_SPEED;
        OtherGLRenderer.sCamera.setScale(newScale);

        return false;
    }
}
