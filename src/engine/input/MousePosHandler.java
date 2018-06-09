package engine.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MousePosHandler extends GLFWCursorPosCallback {

    public static float x;
    public static float y;

    @Override
    public void invoke(long window, double xpos, double ypos) {
        x = (float)xpos;
        y = (float)ypos;
    }
}
