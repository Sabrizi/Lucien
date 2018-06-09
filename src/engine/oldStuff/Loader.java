package engine.oldStuff;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

@Deprecated
public class Loader {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();

    public Model loadToVao(float[] positions){
        int vaoId = createVao();
        storeDataInAttributeList(vaoId, positions);
        unbindVao();
        return new Model(vaoId, positions.length / 3);
    }

    private int createVao(){
        int vaoId = GL30.glGenVertexArrays();
        vaos.add(vaoId);
        glBindVertexArray(vaoId);
        return vaoId;
    }

    private void storeDataInAttributeList(int attributeNumber, float[] data){
        int vboId = glGenBuffers();
        vbos.add(vboId);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void unbindVao(){
        glBindVertexArray(0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void cleanUp(){
        for(int vao: vaos){
            glDeleteVertexArrays(vao);
        }
        for(int vbo: vbos){
            glDeleteBuffers(vbo);
        }
    }
}
