package keybinder;

public class BindingRunner {

	public static void main(String[] args){
		new KeybindUtility(new BindingExecutor("keybinder.BoundFunctions").getMethods());
	}
	
}