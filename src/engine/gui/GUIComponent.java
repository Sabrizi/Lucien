package engine.gui;

import engine.graphics.Mesh;
import engine.graphics.Shader;
import engine.math.Vector3;
import lucien.Settings;

public abstract class GUIComponent {

    protected Mesh mesh;
    protected Vector3 offset;
    protected float rotation;
    protected GUIElement parent;
    protected Shader shader;
    protected float width;
    protected float height;

    public GUIComponent(Mesh mesh, Vector3 offset, float rotation, String shaderName, float width, float height){
        this.mesh = mesh;
        this.offset = offset;
        this.rotation = rotation;
        this.width = width;
        this.height = height;
        this.shader = Shader.shaders.get(shaderName);
    }

    public GUIComponent(Vector3 offset, float rotation, String shaderName, float width, float height){
        this.width = width;
        this.height = height;
        this.offset = offset;
        this.rotation = rotation;
        this.shader = Shader.shaders.get(shaderName);


        float[] vertices = new float[]{
                -this.width / 2.0f, -this.height / 2.0f, 0.9f,
                -this.width / 2.0f,  this.height / 2.0f, 0.9f,
                 this.width / 2.0f,  this.height / 2.0f, 0.9f,
                 this.width / 2.0f, -this.height / 2.0f, 0.9f,
        };

        byte[] indices = new byte[]{
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[]{
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        mesh = new Mesh(vertices,indices,tcs);
    }

    public abstract void update();

    public abstract void render();

    public GUIElement getParent() {
        return parent;
    }

    public void setParent(GUIElement parent) {
        this.parent = parent;
    }
}
