package io.github.simcards.simcards.client.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;

import io.github.simcards.simcards.client.graphics.Camera;
import io.github.simcards.simcards.client.graphics.GLRenderer;
import io.github.simcards.simcards.client.graphics.GLSurfaceViewWrapper;
import io.github.simcards.simcards.game.Deck;
import io.github.simcards.simcards.game.Environment;
import io.github.simcards.simcards.util.Position;

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
        for (Deck deck : environment.decks) {
            deck.touch(event);
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Camera camera = GLRenderer.sCamera;
        float offsetX = distanceX * PAN_SPEED;
        float offsetY = -distanceY * PAN_SPEED;
        camera.offsetPosition(offsetX, offsetY);
        GLSurfaceViewWrapper.rerender();
        return false;
    }
}
