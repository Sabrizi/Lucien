package engine.math;


import engine.utils.BufferUtils;

import java.nio.FloatBuffer;

public class Matrix4 {
    private static final int SIZE = 4 * 4;
    public float[] elements = new float[SIZE];

    public Matrix4() {
        for (int i = 0; i < SIZE; i++) {
            elements[i] = 0.0f;
        }
    }

    public static Matrix4 identity() {
        Matrix4 result = new Matrix4();
        for (int i = 0; i < SIZE; i++) {
            result.elements[i] = 0;
        }
        result.elements[0 + 0 * 4] = 1.0f;
        result.elements[1 + 1 * 4] = 1.0f;
        result.elements[2 + 2 * 4] = 1.0f;
        result.elements[3 + 3 * 4] = 1.0f;

        return result;
    }

    public static Matrix4 ortho(float left, float right, float bottom, float top, float near, float far) {
        Matrix4 result = identity();

        //row, col
        result.elements[0 + 0 * 4] = 2.0f / (right - left);
        result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
        result.elements[2 + 2 * 4] = 2.0f / (near - far);

        result.elements[0 + 3 * 4] = (left + right) / (left - right);
        result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
        result.elements[2 + 3 * 4] = -(far + near) / (far - near);

        return result;
    }

    public static Matrix4 perspective(float fov, float aspectRatio, float near, float far) {

        float yScale = (float)(1f / Math.tan(Math.toRadians(fov / 2.0f))) * aspectRatio;
        float xScale = yScale / aspectRatio;
        float frustumLength = far - near;

        Matrix4 result = new Matrix4();

        result.elements[0 + 0 * 4] = xScale;
        result.elements[1 + 1 * 4] = yScale;
        result.elements[2 + 2 * 4] = -((far + near) / frustumLength);
        result.elements[3 + 2 * 4] = -1.0f;
        result.elements[2 + 3 * 4] = -((2.0f * near * far) / frustumLength);
        result.elements[3 + 3 * 4] = 0.0f;
        return result;
    }


    public static Matrix4 translate(Vector3 vector) {
        Matrix4 result = identity();
        result.elements[0 + 3 * 4] = vector.x;
        result.elements[1 + 3 * 4] = vector.y;
        result.elements[2 + 3 * 4] = vector.z;
        return result;
    }

    public static Matrix4 rotateX(float angle) {
        Matrix4 result = identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        result.elements[1 + 1 * 4] = cos;
        result.elements[1 + 2 * 4] = -sin;
        result.elements[2 + 1 * 4] = sin;
        result.elements[2 + 2 * 4] = cos;

        return result;
    }

    public static Matrix4 rotateY(float angle) {
        Matrix4 result = identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        result.elements[0 + 0 * 4] = cos;
        result.elements[0 + 2 * 4] = sin;
        result.elements[2 + 0 * 4] = -sin;
        result.elements[2 + 2 * 4] = cos;

        return result;
    }

    public static Matrix4 rotateZ(float angle) {
        Matrix4 result = identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        result.elements[0 + 0 * 4] = cos;
        result.elements[1 + 0 * 4] = sin;
        result.elements[0 + 1 * 4] = -sin;
        result.elements[1 + 1 * 4] = cos;

        return result;
    }

    public static Matrix4 scale(float scalarX, float scalarY, float scalarZ){
        Matrix4 result = identity();

        result.elements[0 + 0 * 4] = scalarX;
        result.elements[1 + 1 * 4] = scalarY;
        result.elements[2 + 2 * 4] = scalarZ;
        result.elements[3 + 3 * 4] = 1.0f;

        return result;
    }

    public Matrix4 multiply(Matrix4 other) {
        Matrix4 result = new Matrix4();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                float sum = 0;
                for (int e = 0; e < 4; e++) {
                    sum += elements[x + e * 4] * other.elements[e + y * 4];
                }
                result.elements[x + y * 4] = sum;
            }
        }
        return result;
    }

    public FloatBuffer toFloatBuffer() {
        return BufferUtils.createFloatBuffer(elements);
    }
}
