package lucien.input;

import lucien.enums.PlayerAction;

import java.util.ArrayList;
import java.util.List;

public class GamePadType {
    public List<PlayerAction> buttons = new ArrayList<>();
    public int leftStickHorizontalAxis;
    public int leftStickVerticalAxis;
    public int rightStickHorizontalAxis;
    public int rightStickVerticalAxis;
}

