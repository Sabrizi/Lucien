package engine;

import engine.graphics.Collision;
import engine.graphics.Mesh;
import engine.graphics.Shader;
import engine.math.Matrix4;
import engine.math.Vector2;
import engine.math.Vector3;
import lucien.entities.Player;
import lucien.levels.Platform;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glTexParameteri;

public class Collider {

    public Vector3 position;
    float width, height;
    float top, bottom, left, right;
    Vector3[] corners;
    Entity entity;

    Shader shader;
    Mesh mesh;


    //start static
    public static Collision getCollision(Collider c1, Collider c2) {
        float overlap = Float.MAX_VALUE;
        Vector3 smallest = null;

        ArrayList<Vector3> axes1 = getAxes(c1);
        ArrayList<Vector3> axes2 = getAxes(c2);

        for (Vector3 axis : axes1) {
            float min1 = Float.MAX_VALUE;
            float max1 = -Float.MAX_VALUE;

            float min2 = Float.MAX_VALUE;
            float max2 = -Float.MAX_VALUE;

            for (Vector3 c : c1.corners) {
                float p = axis.dot(c);

                min1 = Float.min(min1, p);
                max1 = Float.max(max1, p);
            }

            for (Vector3 c : c2.corners) {
                float p = axis.dot(c);

                min2 = Float.min(min2, p);
                max2 = Float.max(max2, p);
            }

            if (!(max1 >= min2 && max2 >= min1)) { //If not overlapping
                return null;
            } else {
                float o = Float.min(max1 - min2, max2 - min1);
                if (o < overlap) {
                    overlap = o;
                    smallest = axis;
                }
            }
        }

        for (Vector3 axis : axes2) {
            float min1 = Float.MAX_VALUE;
            float max1 = -Float.MAX_VALUE;

            float min2 = Float.MAX_VALUE;
            float max2 = -Float.MAX_VALUE;

            for (Vector3 c : c1.corners) {
                float p = axis.dot(c);

                min1 = Float.min(min1, p);
                max1 = Float.max(max1, p);
            }

            for (Vector3 c : c2.corners) {
                float p = axis.dot(c);

                min2 = Float.min(min2, p);
                max2 = Float.max(max2, p);
            }

            if (!(max1 >= min2 && max2 >= min1)) { //If not overlapping
                return null;
            } else {
                float o = Float.min(max1 - min2, max2 - min1);
                if (o < overlap) {
                    overlap = o;
                    smallest = axis;
                }
            }
        }

        float scale = overlap / smallest.dot(smallest);
        Vector3 mtv = smallest.scale(scale + 1.0E-10f);
        Vector3 dir = c2.position.subtract(c1.position);

        if (dir.dot(mtv) >= 0) {
            mtv = mtv.scale(-1);
        }

        return new Collision(c1.entity, c2.entity, mtv);
    }


    //TODO: Make this work with generic stuff
    public static ArrayList<Collision> getCollisions(Collider player, ArrayList<Entity> level) {
        ArrayList<Collision> collisions = new ArrayList<>();
        for (Entity p : level) {
            collisions.add(getCollision(player, p.collider));
        }
        return collisions;
    }

    private static ArrayList<Vector3> getEdges(Collider collider) {
        ArrayList<Vector3> edges = new ArrayList<>();

        edges.add(collider.corners[0].subtract(collider.corners[1]));
        edges.add(collider.corners[1].subtract(collider.corners[2]));
        edges.add(collider.corners[2].subtract(collider.corners[3]));
        edges.add(collider.corners[3].subtract(collider.corners[0]));

        return edges;
    }

    private static ArrayList<Vector3> getAxes(Collider c1) {
        ArrayList<Vector3> edges = getEdges(c1);

        ArrayList<Vector3> axes = new ArrayList<>();

        for (Vector3 e : edges) {
            axes.add(getOrtho(e));
        }

        return axes;
    }

    private static Vector3 getOrtho(Vector3 vector) {
        return new Vector3(vector.y, -vector.x, 0f);
    }
    //End static


    public Collider(Entity entity, float w, float h) {
        this.entity = entity;
        this.position = entity.position;
        this.width = w;
        this.height = h;

        this.left = -this.width / 2f;
        this.bottom = -this.height / 2f;
        this.right = this.width / 2f;
        this.top = this.height / 2f;

        corners = new Vector3[4];

        corners[0] = new Vector3(left, bottom, entity.position.z);
        corners[1] = new Vector3(left, top, entity.position.z);
        corners[2] = new Vector3(right, top, entity.position.z);
        corners[3] = new Vector3(right, bottom, entity.position.z);

//        corners[0] = new Vector3(-(this.width / 2), -(this.height / 2), 0f);
//        corners[1] = new Vector3(-(this.width / 2), (this.height / 2), 0f);
//        corners[2] = new Vector3((this.width / 2), (this.height / 2), 0f);
//        corners[3] = new Vector3((this.width / 2), -(this.height / 2), 0f);

        float[] vertices = new float[]{
                corners[0].x, corners[0].y, 0.0f,
                corners[1].x, corners[1].y, 0.0f,
                corners[2].x, corners[2].y, 0.0f,
                corners[3].x, corners[3].y, 0.0f,
        };

        corners[0] = corners[0].add(position);
        corners[1] = corners[1].add(position);
        corners[2] = corners[2].add(position);
        corners[3] = corners[3].add(position);

        byte[] indices = new byte[]{
                0, 1, 2,
                2, 3, 0
        };
        shader = Shader.shaders.get("boundsShader");

        Matrix4 pr_matrix = Matrix4.ortho(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f);
        shader.setUniformMat4("pr_matrix", pr_matrix);

        mesh = new Mesh(vertices, indices, null);
    }

    public Collider(Entity entity, float left, float right, float top, float bottom) {
        this.entity = entity;
        this.position = entity.position;
        this.width = right - left;
        this.height = top - bottom;

        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.top = top;

        corners = new Vector3[4];

        corners[0] = new Vector3(left, bottom, 0f);
        corners[1] = new Vector3(left, top, 0f);
        corners[2] = new Vector3(right, top, 0f);
        corners[3] = new Vector3(right, bottom, 0f);

        float[] vertices = new float[]{
                corners[0].x, corners[0].y, 0.0f,
                corners[1].x, corners[1].y, 0.0f,
                corners[2].x, corners[2].y, 0.0f,
                corners[3].x, corners[3].y, 0.0f,
        };

        corners[0] = corners[0].add(position);
        corners[1] = corners[1].add(position);
        corners[2] = corners[2].add(position);
        corners[3] = corners[3].add(position);

        byte[] indices = new byte[]{
                0, 1, 2,
                2, 3, 0
        };
        shader = Shader.shaders.get("boundsShader");

        Matrix4 pr_matrix = Matrix4.ortho(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f);
        shader.setUniformMat4("pr_matrix", pr_matrix);

        mesh = new Mesh(vertices, indices, null);
    }

    public void update() {
        this.position = entity.position;

        corners[0] = new Vector3(left, bottom, 0f).rotate(entity.rot).add(position);
        corners[1] = new Vector3(left, top, 0f).rotate(entity.rot).add(position);
        corners[2] = new Vector3(right, top, 0f).rotate(entity.rot).add(position);
        corners[3] = new Vector3(right, bottom, 0f).rotate(entity.rot).add(position);


//        corners[0] = (new Vector3(-(this.width / 2), -(this.height / 2), 0f).rotate(entity.rot).add(position));
//        corners[1] = (new Vector3(-(this.width / 2), +(this.height / 2), 0f).rotate(entity.rot).add(position));
//        corners[2] = (new Vector3(+(this.width / 2), +(this.height / 2), 0f).rotate(entity.rot).add(position));
//        corners[3] = (new Vector3(+(this.width / 2), -(this.height / 2), 0f).rotate(entity.rot).add(position));
    }

    public void render() {
        shader.enable();
        //Set uniforms
        Matrix4 ml_matrix = Matrix4.translate(this.position);
        ml_matrix = ml_matrix.multiply(Matrix4.rotateZ(entity.rot));
//            ml_matrix = ml_matrix.multiply(Matrix4.scale(scale.x, scale.y, scale.z));

        shader.setUniformMat4("ml_matrix", ml_matrix);
//        shader.setUniformMat4("ml_matrix", Matrix4.translate(position).multiply(Matrix4.rotateZ(rot)).multiply(Matrix4.scale(2.0f, 2.0f, 0.0f)));
//            shader.setUniformMat4("ml_matrix", entity.getModelMatrix());
        mesh.setRenderMethod(GL_LINE_LOOP);
        mesh.render();
        mesh.setRenderMethod(GL_TRIANGLES);
        shader.disable();

    }
}
