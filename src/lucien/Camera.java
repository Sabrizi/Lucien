package lucien;

import engine.Entity;
import engine.math.Matrix4;
import lucien.entities.Player;

public class Camera {

    private static Player player;
    private static Matrix4 vwMatrix;

    public static void init(Player _player){
        player = _player;
        vwMatrix = createMatrix();
    }

    public static void update(){
        centerOnPlayer();
    }

    public static void centerOnPlayer(){
        vwMatrix = createMatrix();
    }

    private static Matrix4 createMatrix(){
        return Matrix4.translate(player.getPosition().scale(-1f));
    }

    public static Matrix4 getMvMatrix() {
        return vwMatrix;
    }
}
