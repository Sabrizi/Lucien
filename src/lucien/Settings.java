package lucien;

import engine.math.Matrix4;

public final class Settings {

    public static float foreGroundPlane = -19.9f;
    public static float gamePlane = -20.0f;
    public static float backGroundPlane = -20.1f;
    public static Matrix4 perspectiveMatrix = engine.math.Matrix4.perspective(90.0f, 16.0f / 9.0f, 0.1f, -1000.0f);
    public static boolean renderColliders = false;
}
