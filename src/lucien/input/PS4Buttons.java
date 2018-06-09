package lucien.input;

import lucien.enums.PlayerAction;

public class PS4Buttons extends GamePadType {
    public PS4Buttons(){
        /*
            We will use the PlayerAction to determine what the player should do.
            The index of the action maps to the glfwButton on the gamePad
         */
        buttons.add(0, PlayerAction.ATTACK);
        buttons.add(1, PlayerAction.JUMP);
        buttons.add(2, PlayerAction.DASH);
        buttons.add(3, PlayerAction.NONE);
        buttons.add(4, PlayerAction.NONE);
        buttons.add(5, PlayerAction.NONE);
        buttons.add(6, PlayerAction.NONE);
        buttons.add(7, PlayerAction.NONE);
        buttons.add(8, PlayerAction.NONE);
        buttons.add(9, PlayerAction.NONE);
        buttons.add(10, PlayerAction.NONE);
        buttons.add(11, PlayerAction.NONE);
        buttons.add(12, PlayerAction.NONE);
        buttons.add(13, PlayerAction.NONE);
        buttons.add(14, PlayerAction.NONE);
        buttons.add(15, PlayerAction.NONE);
        buttons.add(16, PlayerAction.NONE);
        buttons.add(17, PlayerAction.NONE);

        leftStickHorizontalAxis = 0;
        leftStickVerticalAxis = 1;
        rightStickHorizontalAxis = 2;
        rightStickVerticalAxis = 5;

        //axis 3 and 4 are left trigger and right trigger. [-1, 1]
    }
}
