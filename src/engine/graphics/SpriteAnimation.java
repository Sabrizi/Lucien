package engine.graphics;

import engine.math.Vector2;
import engine.math.Vector4;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class SpriteAnimation {

    private Texture textureAtlas;
    private Shader shader;
    public int currentIndex = 0;
    private int startIndex = 0;
    private int endIndex = 0;

    public SpriteAnimation(int startIndex, int endIndex, Shader shader, Texture texture) {
        this.startIndex = startIndex;
        this.currentIndex = startIndex;
        this.endIndex = endIndex;
        this.shader = shader;
        this.textureAtlas = texture;
        textureAtlas.index = startIndex;
    }

    public void play() {
        textureAtlas.index = currentIndex;
        Vector4 animationData = new Vector4(textureAtlas.getTextureXOffset(), textureAtlas.getTextureYOffset(), textureAtlas.getRows(), textureAtlas.getColumns());
        shader.setUniform4f("animationData", animationData);
        currentIndex++;
        if(currentIndex > endIndex) currentIndex = startIndex;
    }

    public Texture getTextureAtlas() {
        return textureAtlas;
    }
}
