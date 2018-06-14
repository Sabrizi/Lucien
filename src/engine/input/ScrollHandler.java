package engine.input;

import org.lwjgl.glfw.GLFWScrollCallback;


public class ScrollHandler extends GLFWScrollCallback {

    public static double xOffset;
    public static double yOffset;

    public static float delta = 0f;

    @Override
    public void invoke(long window, double xoffset, double yoffset) {
            xOffset = xoffset;
            yOffset = yoffset;

    }
}
