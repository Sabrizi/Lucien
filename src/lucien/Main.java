package lucien;

import engine.GameManager;

public class Main {

    private Thread thread;

    private void start(){
        thread = new Thread(new GameManager("Lucien", 1280, 720, new Lucien()), "Lucien");
        thread.start();
    }

    public static void main(String[] args){
        new Main().start();
    }
}
