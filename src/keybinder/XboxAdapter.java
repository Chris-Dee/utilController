package keybinder;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import ch.aplu.xboxcontroller.XboxControllerAdapter;
import ch.aplu.xboxcontroller.XboxControllerListener;

public class XboxAdapter extends XboxControllerAdapter {
	Robot robot;
	KeybindUtility keys; 
	Map<String, Integer> xboxKeyNumbers;

	public XboxAdapter(){
		xboxKeyNumbers = new HashMap<String, Integer>();
		initializeXboxKeyNumbers();
		try {
			robot=new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initializeXboxKeyNumbers() {
		xboxKeyNumbers.put("A", 500);	
	}
	
	public void setKeys(KeybindUtility k) {
		keys=k;
	}

	public void buttonA(boolean pressed)
	{
		if(pressed){
		keys.setBinding(keys.isBoundAlready(xboxKeyNumbers.get("A")), xboxKeyNumbers.get("A"), keys.buttonToCommandMap.get(keys.findFocused()));
		}
	}

	public void buttonB(boolean pressed)
	{
		isPressed(pressed,KeyEvent.VK_ALPHANUMERIC);
	}

	public void buttonX(boolean pressed)
	{
		isPressed(pressed,KeyEvent.VK_ALT_GRAPH);
	}

	public void buttonY(boolean pressed)
	{
		isPressed(pressed,KeyEvent.VK_CODE_INPUT);
	}

	public void back(boolean pressed)
	{
		isPressed(pressed,KeyEvent.VK_COMPOSE);
	}

	public void start(boolean pressed)
	{
		isPressed(pressed,KeyEvent.VK_D);
	}

	public void leftShoulder(boolean pressed)
	{
		isPressed(pressed,KeyEvent.VK_EURO_SIGN);
	}

	public void rightShoulder(boolean pressed)
	{
		isPressed(pressed,KeyEvent.VK_JAPANESE_HIRAGANA);
	}

	public void leftThumb(boolean pressed)
	{
		isPressed(pressed,KeyEvent.VK_A);
	}

	public void rightThumb(boolean pressed)
	{
		//	isPressed(pressed,KeyEvent.VK_);
	}

	public void dpad(int direction, boolean pressed)
	{
		//TODO adjust based on direction values
		isPressed(pressed,direction);
	}

	public void leftTrigger(double value)
	{
		isPressed(value>0.3,15);
	}

	public void rightTrigger(double value)
	{	
		isPressed(value>0.3,16);
	}

	public void leftThumbMagnitude(double magnitude)
	{

	}

	public void leftThumbDirection(double direction)
	{

	}

	public void rightThumbMagnitude(double magnitude)
	{
	}

	public void rightThumbDirection(double direction)
	{

	}

	public void isConnected(boolean connected)
	{

	}
	public void isPressed(boolean b, int i){
		if(b)
			robot.keyRelease(i);
	}

}
