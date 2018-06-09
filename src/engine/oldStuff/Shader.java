package engine.oldStuff;

import engine.math.Matrix4;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

@Deprecated
public abstract class Shader {

    private int programId, fragId, vertId;

    public Shader(String fragPath, String vertPath){
        vertId = loadShader(vertPath, GL_VERTEX_SHADER);
        fragId = loadShader(fragPath, GL_FRAGMENT_SHADER);
        programId = glCreateProgram();
        glAttachShader(programId, vertId);
        glAttachShader(programId, fragId);
        bindAttributes();
        glLinkProgram(programId);
        glValidateProgram(programId);
        getAllUniformLocations();
    }

    protected abstract void bindAttributes();

    protected abstract void getAllUniformLocations();

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programId, attribute, variableName);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadBool(int location, boolean value) {
        float toLoad = value ? 1: 0;
        GL20.glUniform1f(location, toLoad);
    }

    protected void loadMat4(int location, Matrix4 matrix) {
        //This might not work????
//        GL20.glUniformMatrix4fv(location, false, matrix.getElements());
    }

    protected void loadVec3(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programId, uniformName);
    }

    public void start() {
        GL20.glUseProgram(programId);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        stop();
        glDetachShader(programId, vertId);
        glDetachShader(programId, fragId);
        glDeleteShader(vertId);
        glDeleteShader(fragId);
        glDeleteProgram(programId);
    }

    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch(IOException e){
            System.err.println("Could not read file: " + file);
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderId = GL20.glCreateShader(type);
        glShaderSource(shaderId, shaderSource);
        glCompileShader(shaderId);
        if(glGetShaderi(shaderId,GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(glGetShaderInfoLog(shaderId, 500));
            System.err.println("Could not compile shader: " + shaderSource);
            System.exit(-1);
        }

        return shaderId;
    }
}
