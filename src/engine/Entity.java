package engine;

import engine.graphics.Collision;
import engine.graphics.Mesh;
import engine.graphics.Shader;
import engine.graphics.Texture;
import engine.math.Matrix4;
import engine.math.Vector3;

public abstract class Entity {

    protected Mesh mesh;
    protected Texture texture;
    protected Collider collider;

    protected Matrix4 ml_matrix;

    protected Vector3 position = new Vector3();
    protected Vector3 velocity = new Vector3();
    protected Shader shader;
    protected float rot = 0;
    protected Vector3 scale = new Vector3(1, 1, 1);
    protected float width;
    protected float height;

    public abstract void render();
//    {
//        shader.enable();
//
//        //Set uniforms
//        shader.setUniformMat4("ml_matrix", Matrix4.translate(position).multiply(Matrix4.rotateZ(rot)));
//
//        //TODO: binding here everytime is kinda bad
////        texture.bind();
//        mesh.render();
////        texture.unbind();
//        shader.disable();
//    }

    public abstract void update(float interval);

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getScale() {
        return scale;
    }

    public void setScale(Vector3 scale) {
        this.scale = scale;
    }

    public Collider getCollider() {
        return collider;
    }

    @Deprecated
//    public Collision getCollision(Entity other) {
//        //Make a Collision class with the rect side and the entity.
//
//        Collision result = null;
//        float dx = collider.center.x + velocity.x - other.collider.center.x + other.velocity.x;
//        float dy = collider.center.y + velocity.y - other.collider.center.y + other.velocity.y;
//
//        float thisWidth = collider.right - collider.left;
//        float thisHeight = collider.top - collider.bottom;
//
//        float otherWidth = other.collider.right - other.collider.left;
//        float otherHeight = other.collider.top - other.collider.bottom;
//
//        float w = 0.5f * (thisWidth + otherWidth);
//        float h = 0.5f * (thisHeight + otherHeight);
//
//        if (Math.abs(dx) <= w && Math.abs(dy) <= h) {
//            //Collision!
//
//            float thisBottom = collider.bottom + collider.center.y + velocity.y;
//            float thisTop = collider.top + collider.center.y + velocity.y;
//            float thisLeft = collider.left + collider.center.x + velocity.x;
//            float thisRight = collider.right + collider.center.x + velocity.x;
//
//            float otherBottom = other.collider.bottom + other.collider.center.y + other.velocity.y;
//            float otherTop = other.collider.top + other.collider.center.y + other.velocity.y;
//            float otherLeft = other.collider.left + other.collider.center.x + other.velocity.x;
//            float otherRight = other.collider.right + other.collider.center.x + other.velocity.x;
//
//
//            if(thisBottom >= otherBottom && thisBottom <= otherTop && thisLeft <= otherRight && thisRight >= otherLeft){
//                result = new Collision(other, Math.abs((other.collider.center.y + other.collider.top) - (collider.center.y + collider.bottom)), new Vector3(0, 1, 0));
//            }
////            if(){
////                result = new Collision(other, Math.abs((other.collider.center.y + other.collider.top) - (collider.center.y + collider.bottom)), new Vector3(0, 1, 0));
////            }
//
////            if (thisBottom <= otherTop && thisLeft <= otherRight && thisRight >= otherLeft) {
////                result = new Collision(other, Math.abs((other.collider.center.y + other.collider.top) - (collider.center.y + collider.bottom)), new Vector3(0, 1, 0));
////            }
////            if(thisLeft >= otherLeft &&
////                    thisLeft <= otherRight){
////                result = new Collision(other, Math.abs((other.collider.center.x + other.collider.left) - (collider.center.x + collider.right)), new Vector3(-1, 0, 0));
////            }
//
//
////            if (collider.bottom + velocity.y <= other.collider.top + other.velocity.y) {
////                //Top
////                result = new Collision(other, Math.abs((other.collider.center.y + other.collider.top) - (collider.center.y + collider.bottom)), new Vector3(0, 1, 0));
////            }
////            if(collider.right + velocity.x >= other.collider.left + other.velocity.x){
////                result = new Collision(other, Math.abs((other.collider.center.x + other.collider.left) - (collider.center.x + collider.right)), new Vector3(-1, 0, 0));
////            }
//        }
//        return result;
//
////        Collision result = null;
////        float dx = collider.center.x + velocity.x - other.collider.center.x + other.velocity.x;
////        float dy = collider.center.y + velocity.y - other.collider.center.y + other.velocity.y;
////
////        float thisWidth = collider.right - collider.left;
////        float thisHeight = collider.top - collider.bottom;
////
////        float otherWidth = other.collider.right - other.collider.left;
////        float otherHeight = other.collider.top - other.collider.bottom;
////
////        float w = 0.5f * (thisWidth + otherWidth);
////        float h = 0.5f * (thisHeight + otherHeight);
////
////        if (Math.abs(dx) <= w && Math.abs(dy) <= h) {
////            //Collision!
////            float wy = w * dy;
////            float hx = h * dx;
////
////            if (wy > hx) {
////                if (wy > -hx) {
////                    //Top
////                    result = new Collision(other, Math.abs((other.collider.center.y + other.collider.top) - (collider.center.y + collider.bottom)), new Vector3(0,1,0));
////                } else {
////                    //Left
////                    result = new Collision(other, Math.abs((other.collider.center.x + other.collider.left) - (collider.center.x + collider.right)), new Vector3(-1,0,0));
////                }
////            } else {
////                if (wy > -hx) {
////                    //Right
////                    result = new Collision(other, Math.abs((other.collider.center.x + other.collider.right) - (collider.center.x + collider.left)), new Vector3(1,0,0));
////                } else {
////                    //Bottom
////                    result = new Collision(other, Math.abs((other.collider.center.y + other.collider.bottom) - (collider.center.y + collider.top)), new Vector3(0,-1,0));
////                }
////            }
////        }
////        return result;
//    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    public Texture getTexture() {
        return texture;
    }

    public Matrix4 getModelMatrix() {
        return ml_matrix;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }
}
