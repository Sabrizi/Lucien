package engine.graphics;

import engine.math.Vector2;
import engine.math.Vector4;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import engine.math.Matrix4;
import engine.math.Vector3;
import engine.utils.ShaderUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Shader {

    public static final int VERTEX_ATTRIB = 0;
    public static final int TCOORD_ATTRIB = 1;
    public static Map<String, Shader> shaders = new HashMap<>();

    private boolean enabled = false;

    private final int id;
    private Map<String, Integer> locationCache = new HashMap<String, Integer>();

    public Shader(String vertPath, String fragPath){
        id = ShaderUtils.load(vertPath, fragPath);
    }

    public static void loadShader(String shaderName, String vertPath, String fragPath){
        shaders.put(shaderName, new Shader(vertPath, fragPath));
    }

    public int getUniform(String name){
        if(locationCache.containsKey(name)){
            return locationCache.get(name);
        }
        int result = glGetUniformLocation(id, name);
        if(result < 0){
            System.err.println("Could not find uniform with name '" + name + "'");
        } else{
            locationCache.put(name, result);
        }

        return result;
    }

    public void setUniform1i(String name, int value){
        if(!enabled) enable();
        glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value){
        if(!enabled) enable();
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, Vector2 vector){
        if(!enabled) enable();
        glUniform2f(getUniform(name), vector.x, vector.y);
    }

    public void setUniform3f(String name, Vector3 vector){
        if(!enabled) enable();
        glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
    }

    public void setUniform4f(String name, Vector4 vector){
        if(!enabled) enable();
        glUniform4f(getUniform(name), vector.x, vector.y, vector.z, vector.w);
    }

    public void setUniformMat4(String name, Matrix4 matrix){
        if(!enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }

    public void enable(){
        //Error checking??
        glUseProgram(id);
        enabled = true;
    }

    public void disable()
    {
        glUseProgram(0);
        enabled = false;
    }
}
