package engine.input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.*;

public class MouseButtonHandler extends GLFWMouseButtonCallback {

    //TODO: make this a better size
    public static boolean[] buttons = new boolean[10];

    @Override
    public void invoke(long window, int button, int action, int mods) {
        buttons[button] = action != GLFW_RELEASE;
    }

    public static boolean isButtonDown(int button){
        return buttons[button];
    }
}
