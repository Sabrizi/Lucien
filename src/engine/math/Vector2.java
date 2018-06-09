package engine.math;

public class Vector2 {
    //    private float[] elements = new float[3];
    public float x;
    public float y;

    public Vector2() {
        x = y = 0.0f;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public Vector2 subtract(Vector2 vector){
        return new Vector2(this.x - vector.x, this.y - vector.y);
    }


    //TODO: vector math
}
