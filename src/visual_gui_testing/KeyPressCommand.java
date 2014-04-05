package visual_gui_testing;

import java.awt.Robot;

public class KeyPressCommand extends UserInputCommand {

	private int myKeyCode;
	
	public KeyPressCommand(int keyCode) {
		myKeyCode = keyCode;
	}
	
	@Override
	public void execute(Robot r) {
		r.keyPress(myKeyCode);
	}

}
