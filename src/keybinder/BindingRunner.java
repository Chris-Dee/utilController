package keybinder;

public class BindingRunner {

	public static void main(String[] args) throws ClassNotFoundException {
		new KeybindUtility(new BindingExecutor("keybinder.BoundFunctions").getMethods());
	}
	
}