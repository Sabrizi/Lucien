package engine;

import engine.graphics.Collision;
import engine.graphics.Mesh;
import engine.graphics.Shader;
import engine.graphics.Texture;
import engine.math.Matrix4;
import engine.math.Vector3;
import lucien.Camera;

public abstract class Entity {

    protected Mesh mesh;
    protected Texture texture;
    protected Collider collider;

    protected Matrix4 ml_matrix;

    protected Vector3 position = new Vector3();
    protected Vector3 velocity = new Vector3();
    protected Shader shader;
    protected float rot = 0;
    protected float width;
    protected float height;

    public static boolean renderCollider = true;

    public abstract void render();

    public abstract void update(float interval);

    public Vector3 getPosition() {
        return position;
    }

    public Collider getCollider() {
        return collider;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    public Texture getTexture() {
        return texture;
    }

    public Matrix4 getModelMatrix() {
        return ml_matrix;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }
}
