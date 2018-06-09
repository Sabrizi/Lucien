package engine.interfaces;

import engine.graphics.Window;

public interface IGameLogic {

    void init() throws Exception;

    void input(Window window);

    void update(float interval);

    void render(Window window);
}
