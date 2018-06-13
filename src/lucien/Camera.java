package lucien;

import engine.math.Matrix4;
import engine.math.Vector3;
import lucien.entities.Player;

public class Camera {

    private static Player player;
    private static Vector3 position;

    public static void init(Player _player){
        player = _player;
        position = player.getPosition();
    }

    public static void update(){
        position = player.getPosition();
    }

    public static Matrix4 getMvMatrix() {
        return Matrix4.translate(new Vector3(-position.x, -position.y, 0f));
    }
}
