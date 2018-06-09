package engine.oldStuff;

@Deprecated
public class Model {

    private int vaoId;
    private int vertexCount;
    private ModelTexture texture;

    public Model(int vaoId, int vertexCount){

    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
