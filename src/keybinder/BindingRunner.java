package keybinder;

public class BindingRunner {

	public static void main(String[] args) {
		KeybindUtility newUtility = new KeybindUtility(BindingExecutor.getMethods());
	}
	
}