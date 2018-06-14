package lucien;

import engine.input.ScrollHandler;
import engine.math.Matrix4;
import engine.math.Vector3;
import lucien.entities.Player;

import java.util.Random;

public class Camera {

    private static Player player;
    private static Vector3 position;
    private static Vector3 shakeyCamOffset = new Vector3();
    private static float shakeyCamAngle = 0f;
    private static float trauma = 0.0f;
    private static float maxShakeAngle = 10f;
    private static float maxOffset = 0.5f;
    private static Random random;

    //Camera shake == trauma^2 || trauma^3

    public static void init(Player _player){
        random = new Random();
        player = _player;
        position = player.getPosition();
    }

    public static void update(){
        trauma -= 0.025f;
        if(trauma < 0f) trauma = 0f;
        if(trauma > 1f) trauma = 1f;

        shakeyCamAngle = maxShakeAngle * trauma * trauma * (random.nextFloat() * 2 - 1);
        float offsetX = maxOffset * trauma * trauma * (random.nextFloat() * 2 - 1);
        float offsetY = maxOffset * trauma * trauma * (random.nextFloat() * 2 - 1);

        shakeyCamOffset = new Vector3(offsetX, offsetY, 0f);

        position.x += (player.getPosition().x - position.x) * 0.1f;
        position.y += (player.getPosition().y - position.y) * 0.1f;
        position.z += (float)ScrollHandler.yOffset * 3f;
    }

    public static void addTrauma(float value){
        trauma += value;
    }

    public static Matrix4 getMvMatrix() {
        return Matrix4.translate(new Vector3(-position.x, -position.y, position.z).add(shakeyCamOffset)).multiply(Matrix4.rotateZ(shakeyCamAngle));
    }
}
