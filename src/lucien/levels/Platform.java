package lucien.levels;

import engine.Collider;
import engine.Entity;
import engine.graphics.Mesh;
import engine.graphics.Shader;
import engine.graphics.Texture;
import engine.math.Matrix4;
import engine.math.Vector3;
import lucien.Camera;

public class Platform extends Entity {

    public Platform(Vector3 position, float w, float h){
//        this.position = new Vector3();
        this.position = position;
        width = w;
        height = h;
        this.scale = new Vector3(width, height, 0);

        float[] vertices = new float[]{
                -width / 2.0f, -height / 2.0f, 0.0f,
                -width / 2.0f, height / 2.0f, 0.0f,
                width / 2.0f, height / 2.0f, 0.0f,
                width / 2.0f, -height / 2.0f, 0.0f,
        };

//        float[] vertices = new float[]{
//                -1, -1, 0.0f,
//                -1,  1, 0.0f,
//                 1,  1, 0.0f,
//                 1, -1, 0.0f,
//        };

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

        texture = new Texture("src/lucien/res/player/adventurer-sheet.png", 12, 8);

        shader = Shader.shaders.get("basicShader");

        Matrix4 pr_matrix = Matrix4.ortho(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f);
        shader.setUniformMat4("pr_matrix", pr_matrix);
//        shader.setUniform1i("tex", 1);

        mesh = new Mesh(vertices, indices, tcs);

        collider = new Collider(this, this.width, this.height);
    }

    @Override
    public void render() {
        shader.enable();
        //Set uniforms
        ml_matrix = Matrix4.translate(position);
        ml_matrix = ml_matrix.multiply(Matrix4.rotateZ(rot));
//        ml_matrix = ml_matrix.multiply(Matrix4.scale(scale.x, scale.y, scale.z));

        shader.setUniformMat4("ml_matrix", ml_matrix);
        shader.setUniformMat4("vw_matrix", Camera.getMvMatrix());


//        shader.setUniformMat4("ml_matrix", Matrix4.translate(position).multiply(Matrix4.rotateZ(rot)).multiply(Matrix4.scale(2.0f, 2.0f, 0.0f)));
//        texture.bind();
        mesh.render();
//        texture.unbind();
        shader.disable();
        if(collider != null && renderCollider) {
            collider.render();
        }
    }

    @Override
    public void update(float interval) {
        collider.update();
//        this.collider.center = this.position;
    }
}
