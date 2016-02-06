package io.github.simcards.simcards.client.graphics;

import io.github.simcards.simcards.client.util.Vector3;

/**
 * Controls what the user sees on the playing field.
 */
public class Camera {

    /** The initial z coordinate of the camera's position when initialized. */
    public static final float INITIAL_Z = -3;
    /** The limit to how close the camera can get to the playing field. */
    private static final float MAX_Z = -1;

    /** The position of the camera in space. */
    private Vector3 position;

    /**
     * Sets up a camera at a default distance away from the playing field.
     */
    public Camera() {
        position = new Vector3(0, 0, INITIAL_Z);
    }

    /**
     * Returns the position of the camera.
     * @return The position of the camera.
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Returns the z coordinate of the position of the camera.
     * @return The z coordinate of the position of the camera.
     */
    public float getZ() { return position.z; };

    /**
     * Adds x and y values to the camera position.
     * @param offsetX The amount to add to the camera position's x coordinate.
     * @param offsetY The amount to add to the camera position's y coordinate.
     */
    public void offsetPosition(float offsetX, float offsetY) {
        position.addX(offsetX);
        position.addY(offsetY);
    }

    /**
     * Adds a z value to the camera position, capped at a certain limit of closeness.
     * @param offsetZ The amount to add the the camera position's z coordinate.
     */
    public void offsetZ(float offsetZ) {
        position.addZ(offsetZ);
        position.z = Math.min(position.z, MAX_Z);
    }
}
