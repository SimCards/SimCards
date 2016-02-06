package io.github.simcards.simcards.client.util;

/**
 * A 3D vector.
 */
public class Vector3 {
    /** The coordinates of the vector. */
    public volatile float x, y, z;

    /**
     * Initializes a vector at (0,0,0).
     */
    public Vector3() {
    }

    /**
     * Initializes a vector with initial coordinates.
     * @param x The x coordinate of the vector.
     * @param y The y coordinate of the vector.
     * @param z The z coordinate of the vector.
     */
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Adds a value to the x coordinate.
     * @param offsetX The value to add to the x coordinate.
     */
    public void addX(float offsetX) {
        this.x += offsetX;
    }

    /**
     * Adds a value to the y coordinate.
     * @param offsetY The value to add to the y coordinate.
     */
    public void addY(float offsetY) {
        this.y += offsetY;
    }

    /**
     * Adds a value to the z coordinate.
     * @param offsetZ The value to add to the z coordinate.
     */
    public void addZ(float offsetZ) {
        this.z += offsetZ;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
