package engine.graphics;

import engine.Entity;
import engine.math.Vector3;

public class Collision {

    public Vector3 translationVector;
    public Entity entity1;
    public Entity entity2;

    public Collision(Entity entity1, Entity entity2, Vector3 v){
        this.translationVector = v;
        this.entity1 = entity1;
        this.entity2 = entity2;
    }
}
