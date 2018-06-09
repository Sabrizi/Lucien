package engine.math;

public class Vector4 {
    //    private float[] elements = new float[3];
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 0.0f;
    }

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4(Vector4 vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
        this.w = vector.w;
    }

    //TODO: vector math
//    public Vector3 add(Vector3 other){
//        return new Vector3(this.x + other.x,this.y + other.y,this.z + other.z);
//    }

    public Vector4 add(Vector4 other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        this.w += other.w;
        return this;
    }

    public Vector4 normalize() {
        float length = length();
        x /= length;
        y /= length;
        z /= length;
        w /= length;
        return this;
    }

    public Vector4 scale(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    //TODO:
    public float dot(Vector4 other){
//        return x * other.x + y * other.y;
        return 0;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }
}
