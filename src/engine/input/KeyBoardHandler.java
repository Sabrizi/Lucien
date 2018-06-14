package engine.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class KeyBoardHandler extends GLFWKeyCallback {

    private static int actions[] = new int[65536];
    private static boolean keys[] = new boolean[65536];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods)
    {
        keys[key] = action != GLFW_RELEASE;
        actions[key] = action;
    }

    public static boolean isKeyDown(int keycode){
        return keys[keycode];
    }

    public static int getKeyAction(int keycode){return actions[keycode];}
}
