package engine;

import engine.graphics.Window;
import engine.input.*;
import engine.interfaces.IGameLogic;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameManager implements Runnable {

    private final int windowWidth;
    private final int windowHeight;
    private final String windowTitle;
    private final IGameLogic game;

    private Window window;
    private boolean running = true;
    private boolean gamePad = false;


    public GameManager(String windowTitle, int width, int height, IGameLogic game){
        this.game = game;
        this.windowWidth = width;
        this.windowHeight = height;
        this.windowTitle = windowTitle;
    }

    private void init() {
        if(!glfwInit()){
            System.err.println("Failed to initialize GLFW");
            return;
        }
        this.window = new Window(windowWidth, windowHeight, windowTitle, false);

        System.out.println("OpenGL Version: " + glGetString(GL_VERSION));

        glfwSetKeyCallback(window.getWindow(), new KeyBoardHandler());
        glfwSetCursorPosCallback(window.getWindow(), new MousePosHandler());
        glfwSetMouseButtonCallback(window.getWindow(), new MouseButtonHandler());

        try {
            game.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();
        gameLoop();
        glfwDestroyWindow(window.getWindow());
        glfwTerminate();
    }

    private void gameLoop(){
        long lastTime = System.nanoTime();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1.0) {
                handleInput(); //This might be better outside this loop??
                update();
                updates++;
                delta--;
                render();
                frames++;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("Updates: " + updates + " || FPS: " + frames);
                updates = 0;
                frames = 0;
            }

            if (glfwWindowShouldClose(window.getWindow())) {
                running = false;
            }
        }
    }

    private void handleInput(){
        game.input(window);
    }

    private void update(){
        window.update();
        game.update(10);
    }

    private void render(){
        if(window.isResized()){
            glViewport(0,0,window.getScreenWidth(), window.getScreenHeight());
            window.setResized(false);
        }

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        game.render(window);
        glfwSwapBuffers(window.getWindow());
    }
//
//    private void sync(double current){
//        float loopSlot = 1f / 60;
//        double endTime = current + loopSlot;
//        while(System.currentTimeMillis() < endTime){
//            try{
//                Thread.sleep(1);
//            } catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        }
//    }
}
