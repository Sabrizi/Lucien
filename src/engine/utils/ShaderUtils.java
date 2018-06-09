package engine.utils;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {
    private ShaderUtils() {}

    public static int load(String vertPath, String fragPath) {
        String vert = FileUtils.loadAsString(vertPath);
        String frag = FileUtils.loadAsString(fragPath);
        return create(vert, frag);
    }

    public static int create(String vert, String frag) {
        int program = glCreateProgram();
        int vertId = glCreateShader(GL_VERTEX_SHADER);
        int fragId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertId, vert);
        glShaderSource(fragId, frag);
        glCompileShader(vertId);
        glCompileShader(fragId);

        if (glGetShaderi(vertId, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile vertex shader: " + vert);
            System.err.println(glGetShaderInfoLog(vertId));
            return -1;
        }

        if (glGetShaderi(fragId, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile fragment shader: " + frag);
            System.err.println(glGetShaderInfoLog(fragId));
            return -1;
        }

        glAttachShader(program, vertId);
        glAttachShader(program, fragId);
        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vertId);
        glDeleteShader(fragId);

        return program;
    }
}
