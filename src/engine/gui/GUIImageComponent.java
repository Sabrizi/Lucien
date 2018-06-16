package engine.gui;

import engine.graphics.SpriteAnimation;
import engine.graphics.Texture;
import engine.math.Matrix4;
import engine.math.Vector3;

import static org.lwjgl.opengl.GL11.*;

public class GUIImageComponent extends GUIComponent {

    Texture texture;

    public GUIImageComponent(Vector3 offset, float rotation, String shaderName, String imagePath, float width, float height) {
        super(offset, rotation, shaderName, width, height);
        texture = new Texture(imagePath, 12, 8);

        Matrix4 pr_matrix = Matrix4.ortho(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f);
        shader.setUniformMat4("pr_matrix", pr_matrix);
        shader.setUniform1i("tex", 1);
    }



    @Override
    public void update() {

    }

    @Override
    public void render() {
        glDisable(GL_BLEND);
        shader.enable();
        texture.bind();

        shader.setUniformMat4("ml_matrix", Matrix4.translate(this.parent.getPosition().add(this.offset)));
//        shader.setUniformMat4("ml_matrix", Matrix4.translate(new Vector3()));

        mesh.render();
        texture.unbind();
        shader.disable();
    }
}
