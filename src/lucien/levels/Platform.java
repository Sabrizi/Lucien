package lucien.levels;

import engine.Collider;
import engine.Entity;
import engine.graphics.Mesh;
import engine.graphics.Shader;
import engine.graphics.Texture;
import engine.math.Matrix4;
import engine.math.Vector3;
import lucien.Camera;
import lucien.Settings;

public class Platform extends Entity {

    public Platform(Vector3 position, float w, float h){
//        this.position = new Vector3();
        this.position = position;
        width = w;
        height = h;

        float[] vertices = new float[]{
                -width / 2.0f, -height / 2.0f, Settings.gamePlane,
                -width / 2.0f,  height / 2.0f, Settings.gamePlane,
                 width / 2.0f,  height / 2.0f, Settings.gamePlane,
                 width / 2.0f, -height / 2.0f, Settings.gamePlane,
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

        texture = new Texture("src/lucien/res/player/adventurer-sheet.png", 12, 8);

        shader = Shader.shaders.get("basicShader");

//        Matrix4 pr_matrix = Matrix4.ortho(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f);
        Matrix4 pr_matrix = Matrix4.perspective(90.0f, 16.0f / 9.0f, 1.0f, -1000.0f);
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
//        ml_matrix = ml_matrix.multiply(Matrix4.rotateZ(rot));

        shader.setUniformMat4("ml_matrix", ml_matrix);
        shader.setUniformMat4("vw_matrix", Camera.getMvMatrix());


//        texture.bind();
        mesh.render();
//        texture.unbind();
        shader.disable();
        if(collider != null && Settings.renderColliders) {
            collider.render();
        }
    }

    @Override
    public void update(float interval) {
//        this.position = position.subtract(Camera.getOffset());
        collider.update();
//        this.collider.center = this.position;
    }
}
