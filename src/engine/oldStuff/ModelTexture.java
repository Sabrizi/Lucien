package engine.oldStuff;

@Deprecated
public class ModelTexture {
    private int textureId;

    public ModelTexture(int id){
        this.textureId = id;
    }

    public int getTextureId(){
        return textureId;
    }
}
