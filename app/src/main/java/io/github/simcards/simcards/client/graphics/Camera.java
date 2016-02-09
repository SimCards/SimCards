package io.github.simcards.simcards.client.graphics;

import io.github.simcards.simcards.util.MathUtil;
import io.github.simcards.simcards.util.Position;

/**
 * Controls what the user sees on the playing field.
 */
public class Camera {

    /** The minimum value of the camera position's x coordinate. */
    public float minX = -1;
    /** The maximum value of the camera position's x coordinate. */
    public float maxX = 1;
    /** The minimum value of the camera position's y coordinate. */
    public float minY = -1;
    /** The maximum value of the camera position's y coordinate. */
    public float maxY = 1;

    /** The initial z coordinate of the camera's position when initialized. */
    public static final float INITIAL_SCALE = 1f;
    /** The minimum scale of the camera */
    private static final float MIN_SCALE = 0.5f;
    /** The maximum scale of the camera. */
    public float maxScale = 10;

    /** The scale of the camera view. */
    public float scale = INITIAL_SCALE;
    /** The scale of the camera view on the previous frame. */
    public float prevScale = -1;

    /** The position of the camera in space. */
    public Position position;

    /**
     * Sets up a camera at a default position.
     */
    public Camera() {
        position = new Position();
    }

    /**
     * Adds an offset to the camera position.
     * @param offsetX The offset to add to the camera position's x coordinate.
     * @param offsetY The offset to add to the camera position's y coordinate.
     */
    public void offsetPosition(float offsetX, float offsetY) {
        position.addXLimited(offsetX, minX, maxX);
        position.addYLimited(offsetY, minY, maxY);
    }

    /**
     * Adds a value to the camera scale, limited to a range of values.
     * @param offset The amount to add to the camera scale.
     */
    public void offsetScale(float offset) {
        scale = MathUtil.addLimited(scale, offset, MIN_SCALE, maxScale);
    }

    /**
     * Checks if the camera's scale has changed between the previous and the current frames.
     * Updates the previous scale variable after checking.
     * @return Whether the camera's scale has changed between the previous and the current frames.
     */
    public boolean checkScaleChange() {
        boolean changed = false;
        if (prevScale != scale) {
            changed = true;
        }
        prevScale = scale;
        return changed;
    }
}
