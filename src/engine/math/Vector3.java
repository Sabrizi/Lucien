package engine.math;

import engine.Collider;

public class Vector3 {
    //    private float[] elements = new float[3];
    public float x;
    public float y;
    public float z;

    public Vector3() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    //TODO: vector math

    //    public Vector3 add(Vector3 other){
    public Vector3 add(Vector3 other) {
        return new Vector3(this.x + other.x, this.y + other.y, this.z+other.z);
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3 rotate(float angle){
        Vector3 result = new Vector3();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);
        result.x = (this.x * cos) - (this.y * sin);
        result.y = (this.x * sin) + (this.y * cos);
        result.z = this.z;

        return result;
    }

    public Vector3 normalize() {
        float length = length();
        if(length == 0) return new Vector3();
        return new Vector3(x / length, y / length, z/length);
    }

    public Vector3 scale(float scalar) {
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    public float dot(Vector3 other){
        return (this.x * other.x) + (this.y * other.y);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
}
