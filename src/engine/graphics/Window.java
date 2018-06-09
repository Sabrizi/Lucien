package engine.graphics;

import engine.input.KeyBoardHandler;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Window {

    private long window;
    private int screenWidth;
    private int screenHeight;
    private boolean resized;

    public Window(int width, int height, CharSequence title, boolean vsync){
        this.screenWidth = width;
        this.screenHeight = height;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, 0, 0);
        if(window == 0){
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, vidMode.width() / 4, vidMode.height() / 4);

        glfwSetFramebufferSizeCallback(window, (window, _width, _height)->{
            screenWidth = _width;
            screenHeight = _height;
            setResized(true);
        });

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

        GL.createCapabilities();

        glfwSwapInterval(vsync ? 1 : 0);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void update(){
        glfwPollEvents();
    }

    public void setClearColor(float r, float g, float b, float a){
        glClearColor(r, g, b, a);
    }

    public long getWindow() {
        return window;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public float getAspectRatio(){
        return screenWidth / screenHeight;
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }
}
