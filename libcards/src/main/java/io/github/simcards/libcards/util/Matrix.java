package io.github.simcards.libcards.util;
/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Matrix math utilities. These methods operate on OpenGL ES format
 * matrices and vectors stored in float arrays.
 * <p>
 * Matrices are 4 x 4 column-vector matrices stored in column-major
 * order:
 * <pre>
 *  m[offset +  0] m[offset +  4] m[offset +  8] m[offset + 12]
 *  m[offset +  1] m[offset +  5] m[offset +  9] m[offset + 13]
 *  m[offset +  2] m[offset +  6] m[offset + 10] m[offset + 14]
 *  m[offset +  3] m[offset +  7] m[offset + 11] m[offset + 15]</pre>
 *
 * Vectors are 4 x 1 column vectors stored in order:
 * <pre>
 * v[offset + 0]
 * v[offset + 1]
 * v[offset + 2]
 * v[offset + 3]</pre>
 */
public class Matrix {

    /**
     * @deprecated All methods are static, do not instantiate this class.
     */
    @Deprecated
    public Matrix() {}

    /**
     * Multiplies two 4x4 matrices together and stores the result in a third 4x4
     * matrix. In matrix notation: result = lhs x rhs. Due to the way
     * matrix multiplication works, the result matrix will have the same
     * effect as first multiplying by the rhs matrix, then multiplying by
     * the lhs matrix. This is the opposite of what you might expect.
     * <p>
     * The same float array may be passed for result, lhs, and/or rhs. However,
     * the result element values are undefined if the result elements overlap
     * either the lhs or rhs elements.
     *
     * @param result The float array that holds the result.
     * @param resultOffset The offset into the result array where the result is
     *        stored.
     * @param lhs The float array that holds the left-hand-side matrix.
     * @param lhsOffset The offset into the lhs array where the lhs is stored
     * @param rhs The float array that holds the right-hand-side matrix.
     * @param rhsOffset The offset into the rhs array where the rhs is stored.
     *
     * @throws IllegalArgumentException if result, lhs, or rhs are null, or if
     * resultOffset + 16 > result.length or lhsOffset + 16 > lhs.length or
     * rhsOffset + 16 > rhs.length.
     */
    public static void multiplyMM(float[] result, int resultOffset,
                                         float[] lhs, int lhsOffset, float[] rhs, int rhsOffset) {
//        if (GLWrapper.desktopGL == null) {
//            android.opengl.Matrix.multiplyMM(result, resultOffset, lhs, lhsOffset, rhs, rhsOffset);
//        } else {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    result[i * 4 + j] = 0;
                    for (int k = 0; k < 4; k++) {
                        result[i * 4 + j] += lhs[k * 4 + j] * rhs[i * 4 + k];
                    }
                }
            }
//        }
    }

    /**
     * Computes an orthographic projection matrix.
     *
     * @param m returns the result
     * @param mOffset
     * @param left
     * @param right
     * @param bottom
     * @param top
     * @param near
     * @param far
     */
    public static void orthoM(float[] m, int mOffset,
                              float left, float right, float bottom, float top,
                              float near, float far) {
        if (left == right) {
            throw new IllegalArgumentException("left == right");
        }
        if (bottom == top) {
            throw new IllegalArgumentException("bottom == top");
        }
        if (near == far) {
            throw new IllegalArgumentException("near == far");
        }

        final float r_width  = 1.0f / (right - left);
        final float r_height = 1.0f / (top - bottom);
        final float r_depth  = 1.0f / (far - near);
        final float x =  2.0f * (r_width);
        final float y =  2.0f * (r_height);
        final float z = -2.0f * (r_depth);
        final float tx = -(right + left) * r_width;
        final float ty = -(top + bottom) * r_height;
        final float tz = -(far + near) * r_depth;
        m[mOffset + 0] = x;
        m[mOffset + 5] = y;
        m[mOffset +10] = z;
        m[mOffset +12] = tx;
        m[mOffset +13] = ty;
        m[mOffset +14] = tz;
        m[mOffset +15] = 1.0f;
        m[mOffset + 1] = 0.0f;
        m[mOffset + 2] = 0.0f;
        m[mOffset + 3] = 0.0f;
        m[mOffset + 4] = 0.0f;
        m[mOffset + 6] = 0.0f;
        m[mOffset + 7] = 0.0f;
        m[mOffset + 8] = 0.0f;
        m[mOffset + 9] = 0.0f;
        m[mOffset + 11] = 0.0f;
    }

    /**
     * Computes the length of a vector.
     *
     * @param x x coordinate of a vector
     * @param y y coordinate of a vector
     * @param z z coordinate of a vector
     * @return the length of a vector
     */
    public static float length(float x, float y, float z) {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Translates matrix m by x, y, and z in place.
     *
     * @param m matrix
     * @param mOffset index into m where the matrix starts
     * @param x translation factor x
     * @param y translation factor y
     * @param z translation factor z
     */
    public static void translateM(
            float[] m, int mOffset,
            float x, float y, float z) {
        for (int i=0 ; i<4 ; i++) {
            int mi = mOffset + i;
            m[12 + mi] += m[mi] * x + m[4 + mi] * y + m[8 + mi] * z;
        }
    }

    /**
     * Defines a viewing transformation in terms of an eye point, a center of
     * view, and an up vector.
     *
     * @param rm returns the result
     * @param rmOffset index into rm where the result matrix starts
     * @param eyeX eye point X
     * @param eyeY eye point Y
     * @param eyeZ eye point Z
     * @param centerX center of view X
     * @param centerY center of view Y
     * @param centerZ center of view Z
     * @param upX up vector X
     * @param upY up vector Y
     * @param upZ up vector Z
     */
    public static void setLookAtM(float[] rm, int rmOffset,
                                  float eyeX, float eyeY, float eyeZ,
                                  float centerX, float centerY, float centerZ, float upX, float upY,
                                  float upZ) {

        // See the OpenGL GLUT documentation for gluLookAt for a description
        // of the algorithm. We implement it in a straightforward way:

        float fx = centerX - eyeX;
        float fy = centerY - eyeY;
        float fz = centerZ - eyeZ;

        // Normalize f
        float rlf = 1.0f / Matrix.length(fx, fy, fz);
        fx *= rlf;
        fy *= rlf;
        fz *= rlf;

        // compute s = f x up (x means "cross product")
        float sx = fy * upZ - fz * upY;
        float sy = fz * upX - fx * upZ;
        float sz = fx * upY - fy * upX;

        // and normalize s
        float rls = 1.0f / Matrix.length(sx, sy, sz);
        sx *= rls;
        sy *= rls;
        sz *= rls;

        // compute u = s x f
        float ux = sy * fz - sz * fy;
        float uy = sz * fx - sx * fz;
        float uz = sx * fy - sy * fx;

        rm[rmOffset + 0] = sx;
        rm[rmOffset + 1] = ux;
        rm[rmOffset + 2] = -fx;
        rm[rmOffset + 3] = 0.0f;

        rm[rmOffset + 4] = sy;
        rm[rmOffset + 5] = uy;
        rm[rmOffset + 6] = -fy;
        rm[rmOffset + 7] = 0.0f;

        rm[rmOffset + 8] = sz;
        rm[rmOffset + 9] = uz;
        rm[rmOffset + 10] = -fz;
        rm[rmOffset + 11] = 0.0f;

        rm[rmOffset + 12] = 0.0f;
        rm[rmOffset + 13] = 0.0f;
        rm[rmOffset + 14] = 0.0f;
        rm[rmOffset + 15] = 1.0f;

        translateM(rm, rmOffset, -eyeX, -eyeY, -eyeZ);
    }
}
