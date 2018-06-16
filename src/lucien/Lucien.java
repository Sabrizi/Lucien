package lucien;

import engine.Entity;
import engine.GameManager;
import engine.graphics.Shader;
import engine.graphics.Window;
import engine.gui.GUIElement;
import engine.gui.GUIImageComponent;
import engine.input.ScrollHandler;
import engine.math.Vector3;
import lucien.entities.Player;
import lucien.enums.PlayerAction;
import lucien.enums.PlayerState;
import lucien.input.GamePadType;
import engine.input.KeyBoardHandler;
import lucien.input.PS4Buttons;
import engine.interfaces.IGameLogic;
import lucien.levels.Platform;


import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.*;

public class Lucien implements IGameLogic {


    //GamePad Fields
    public byte[] buttons;
    public byte[] hats;
    public float[] axes;
    public GamePadType gamePad;
    public boolean usingGamePad = false;
    private float joystickDeadZone = 0.3f;

    public Player player;
    public static GameManager manager;

    public ArrayList<GUIElement> guiElements = new ArrayList<>();

    //TODO: Remove this later. It's just for collision testing right now
    public static ArrayList<Entity> level = new ArrayList<>();

    public static Platform platform1;
    public static Platform platform2;

    @Override
    public void init() throws Exception {
        glEnable(GL_DEPTH_TEST);
        initGamePad();
        loadShaders();
        loadUniforms();
        loadEntities();
        loadGUIElements();

        glActiveTexture(GL_TEXTURE1);
        Camera.init(player);
    }

    private void loadGUIElements(){
        GUIElement test1 = new GUIElement(new Vector3(-10f, -7.5f, 0f));
        GUIImageComponent imageComponent = new GUIImageComponent(new Vector3(), 0.0f, "guiImageShader", "src/lucien/res/test.png", 10f, 2f);
        test1.addComponent(imageComponent);
        guiElements.add(test1);

        GUIElement test2 = new GUIElement(new Vector3(12f, 6f, 0f));
        GUIImageComponent imageComponent2 = new GUIImageComponent(new Vector3(), 0.0f, "guiImageShader", "src/lucien/res/test.png", 4f, 4f);
        test2.addComponent(imageComponent2);
        guiElements.add(test2);
    }

    private void loadEntities() {
        player = new Player();
        level.add(new Platform(new Vector3(0f, -4f, 1.0f), 5f, 2f));
        level.add(new Platform(new Vector3(-4f, 0f, 1.0f), 2f, 10f));

//        platform1 = new Platform(new Vector3(0f,-4f,1.0f), 5f, 2f);
//        platform2 = new Platform(new Vector3(-4f,0f,1.0f), 2f, 10f);
    }

    private void loadUniforms() {
//        Matrix4 pr_matrix = Matrix4.ortho(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f);
//        Shader.shaders.get("playerShader").setUniformMat4("pr_matrix", pr_matrix);
    }

    private void loadShaders() {
        Shader.loadShader("playerShader", "src/lucien/shaders/player.vert", "src/lucien/shaders/player.frag");
        Shader.loadShader("basicShader", "src/lucien/shaders/basic.vert", "src/lucien/shaders/basic.frag");
        Shader.loadShader("boundsShader", "src/lucien/shaders/collider.vert", "src/lucien/shaders/collider.frag");
        Shader.loadShader("guiImageShader", "src/lucien/shaders/guiImageShader.vert", "src/lucien/shaders/guiImageShader.frag");
    }

    private void initGamePad() {
        glfwSetJoystickCallback((int jid, int event) -> {
            if (event == GLFW_CONNECTED) {
                usingGamePad = true;
                System.out.println("Gamepad connected");
            }

            if (event == GLFW_DISCONNECTED) {
                usingGamePad = false;
                System.out.println("Gamepad disconnected");
            }
        });

        if (glfwJoystickPresent(GLFW_JOYSTICK_1)) {
            buttons = new byte[glfwGetJoystickButtons(GLFW_JOYSTICK_1).capacity()];
            hats = new byte[glfwGetJoystickHats(GLFW_JOYSTICK_1).capacity()];
            axes = new float[glfwGetJoystickAxes(GLFW_JOYSTICK_1).capacity()];

            if (glfwJoystickPresent(GLFW_JOYSTICK_1)) {
                this.usingGamePad = true;
                if (glfwGetGamepadName(GLFW_JOYSTICK_1).contains("PS4")) {
                    gamePad = new PS4Buttons();
                } else if (false) { //TODO: the other gamepad layouts for xBox etc.

                } else {
                    gamePad = new PS4Buttons(); //This should be null or something
                }
                System.out.println("GamePad: " + glfwGetGamepadName(GLFW_JOYSTICK_1));
            }
        }
    }

    @Override
    public void input(Window window) {
        if (usingGamePad) {
            checkGamePad();
        } else {
            checkKeyBoard(window);
        }
        checkMouse();
        checkScroll();
    }

    private void checkScroll() {
        ScrollHandler.yOffset = 0;
//        System.out.println(ScrollHandler.yOffset);
    }

    private int colliderToggleCounter = 0;
    private void checkKeyBoard(Window window) {
        Vector3 velocity = new Vector3();
        if (KeyBoardHandler.isKeyDown(GLFW_KEY_W)) {
            velocity.y = 1.0f;
        }

        if (KeyBoardHandler.isKeyDown(GLFW_KEY_A)) {
            velocity.x = -1.0f;
        }

        if (KeyBoardHandler.isKeyDown(GLFW_KEY_D)) {
            velocity.x = 1.0f;
        }

        if (KeyBoardHandler.isKeyDown(GLFW_KEY_S)) {
            velocity.y = -1.0f;
        }

        player.setVelocity(velocity.normalize());

        if (KeyBoardHandler.isKeyDown(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(window.getWindow(), true);
        }

        if (KeyBoardHandler.isKeyDown(GLFW_KEY_LEFT_CONTROL)&& KeyBoardHandler.isKeyDown(GLFW_KEY_C)) {
            colliderToggleCounter--;
            if(colliderToggleCounter <= 0) {
                Settings.renderColliders = !Settings.renderColliders;
                colliderToggleCounter = 7;
            }
        }

        if (KeyBoardHandler.isKeyDown(GLFW_KEY_T)) {
            Camera.addTrauma(0.1f);
        }
    }

    private void checkMouse() {
    }

    private void checkGamePad() {
        if (glfwJoystickPresent(GLFW_JOYSTICK_1)) {
            glfwGetJoystickButtons(GLFW_JOYSTICK_1).get(buttons);
            glfwGetJoystickHats(GLFW_JOYSTICK_1).get(hats);
            glfwGetJoystickAxes(GLFW_JOYSTICK_1).get(axes);
            Vector3 velocity = new Vector3();


            //This is how to check for individual buttons
            if (player.getState() == PlayerState.IDLE || player.getState() == PlayerState.RUNNING) {
                if (buttons[gamePad.buttons.indexOf(PlayerAction.ATTACK)] != 0) {
                    player.setState(PlayerState.ATTACKING);
                    player.actionFrames = 4;
                }

                if (buttons[gamePad.buttons.indexOf(PlayerAction.DASH)] != 0) {
                    player.setState(PlayerState.DASHING);
                    player.actionFrames = 4;
                    velocity.x = 1.0f;
                }
            }


            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i] != 0) {
//                    System.out.println("Button: " + gamePad.buttons.get(i).toString());
                }
            }

            if (hats[0] != 0) {
                System.out.println(hats[0]);
            }

            if (Math.abs(axes[gamePad.leftStickHorizontalAxis]) > joystickDeadZone) {
                velocity.x = axes[gamePad.leftStickHorizontalAxis];
//                System.out.println(velocity.x);
            } else {
                velocity.x = 0.f;
            }

            if (Math.abs(axes[gamePad.leftStickVerticalAxis]) > joystickDeadZone) {
//                System.out.println(axes[gamePad.leftStickVerticalAxis]);
//                player.setVelocity(new Vector3(0.0f, -axes[gamePad.leftStickHorizontalAxis], 0.0f));
                velocity.y = -axes[gamePad.leftStickVerticalAxis];
            } else {
                velocity.y = 0.f;
            }

            if (Math.abs(axes[gamePad.rightStickHorizontalAxis]) > joystickDeadZone) {
//                System.out.println(axes[gamePad.rightStickHorizontalAxis]);
            }

            if (Math.abs(axes[gamePad.rightStickVerticalAxis]) > joystickDeadZone) {
//                System.out.println(axes[gamePad.rightStickVerticalAxis]);
            }
            player.setVelocity(velocity);
        }
    }

    @Override
    public void update(float interval) {
        player.update(interval);
        for (Entity e : level) {
            e.update(interval);
        }

        for(GUIElement e: guiElements){
            e.update();
        }
//        platform1.update(interval);
//        platform2.update(interval);
    }

    @Override
    public void render(Window window) {
        player.render();
        for (Entity e : level) {
            e.render();
        }
        for(GUIElement e: guiElements){
            e.render();
        }
//        platform1.render();
//        platform2.render();
    }
}
