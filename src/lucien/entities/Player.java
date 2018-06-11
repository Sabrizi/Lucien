package lucien.entities;

import engine.Collider;
import engine.Entity;
import engine.graphics.*;
import engine.math.Matrix4;
import engine.math.Vector3;
import lucien.Lucien;
import lucien.enums.PlayerAction;
import lucien.enums.PlayerState;
import lucien.levels.Platform;

import java.util.ArrayList;

public class Player extends Entity {

    private float speed = 0.3f;
    private float flip = 1.0f;
    private int updateStep = 0;

    private SpriteAnimation idleAnimation;
    private SpriteAnimation runAnimation;
    private SpriteAnimation attackAnimation;
    private SpriteAnimation dashAnimation;

    private PlayerAction action = PlayerAction.NONE;
    private PlayerState state = PlayerState.IDLE;

    private float gravity = 0.5f;

    public int actionFrames = 0;

    public Player() {
        position = new Vector3(0.0f, 3.0f, 0.9f);
        width = 5.0f;
        height = 5.0f;
//        scale = new Vector3(width, height, 0);

        float[] vertices = new float[]{
                -width / 2.0f, -height / 2.0f, 0.0f,
                -width / 2.0f,  height / 2.0f, 0.0f,
                 width / 2.0f,  height / 2.0f, 0.0f,
                 width / 2.0f, -height / 2.0f, 0.0f,
        };

        byte[] indices = new byte[]{
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[]{
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        texture = new Texture("src/lucien/res/player/adventurer-sheet.png", 12, 8);

        shader = Shader.shaders.get("playerShader");

        idleAnimation = new SpriteAnimation(0, 3, shader, texture);
        runAnimation = new SpriteAnimation(8, 13, shader, texture);
        attackAnimation = new SpriteAnimation(48, 51, shader, texture);
        dashAnimation = new SpriteAnimation(24, 28, shader, texture);

        Matrix4 pr_matrix = Matrix4.ortho(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f);
        shader.setUniformMat4("pr_matrix", pr_matrix);
        shader.setUniform1i("tex", 1);

        mesh = new Mesh(vertices, indices, tcs);

        collider = new Collider(this, this.width - 2f, this.height - .25f);
//        collider = new Collider(this, -1f, 5f, 2f, -3f);
        this.rot = 10;
    }


    @Override
    public void update(float interval) {
        //Player movement
//        velocity.y -= gravity;

        if (actionFrames == 0) {
            if (velocity.x < 0) {
                state = PlayerState.RUNNING;
                flip = -1.0f;
            } else if (velocity.x > 0) {
                state = PlayerState.RUNNING;
                flip = 1.0f;
            } else {
                state = PlayerState.IDLE;
            }

            position = position.add(velocity.scale(speed));
//            position = position.rotate(rot);
            collider.update();

            for(Entity e : Lucien.level){
                Collision c = Collider.getCollision(this.collider, e.getCollider());
                if (c != null) {
                    this.position = this.position.add(c.translationVector);
                    collider.update();
                }
            }

//            collider.update();
//
//            ArrayList<Collision> collisions = Collider.getCollisions(this.collider, Lucien.level);
//
//            /*TODO: This causes issues when colliding with two objects that return mtvs
//                that point in the same direction
//             */
//            for(Collision c : collisions){
//                if (c != null) {
//                    this.position = this.position.add(c.translationVector);
//                    collider.update();
//                }
//            }

        }

        //Player animation updates
        updateStep++;
        if (updateStep == interval) {
            updateStep = 0;
            if (actionFrames > 0) actionFrames--;

            switch (state) {
                case IDLE:
                    idleAnimation.play();
                    break;
                case RUNNING:
                    runAnimation.play();
                    break;
                case ATTACKING:
                    attackAnimation.play();
                    velocity.x = 0;
                    break;
                case DASHING:
                    dashAnimation.play();
                    break;
            }
        }

    }

    @Override
    public void render() {
        shader.enable();
        //Set uniforms
        ml_matrix = Matrix4.translate(position);
//        ml_matrix = ml_matrix.multiply(Matrix4.rotateZ(rot));
        ml_matrix = ml_matrix.multiply(Matrix4.scale(flip, 1f, 1f));

        shader.setUniformMat4("ml_matrix", ml_matrix);
//        shader.setUniformMat4("ml_matrix", Matrix4.translate(position).multiply(Matrix4.rotateZ(rot)).multiply(Matrix4.scale(2.0f, 2.0f, 0.0f)));
        texture.bind();
        mesh.render();
        texture.unbind();
        shader.disable();

        collider.render();
    }

    public PlayerAction getAction() {
        return action;
    }

    public void setAction(PlayerAction action) {
        this.action = action;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }
}
