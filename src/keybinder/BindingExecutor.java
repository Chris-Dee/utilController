package keybinder;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import jgame.platform.*;

public class BindingExecutor {
	
	Class<?> actionClass;
	Map<Integer, String> myBindings;
	
	
	/**
	 * A class which acts as a back-end for the keybinding util package.
	 * Handles reflective retrieving and calling of methods based
	 * on keys pressed and Bindings.txt.
	 */
	public BindingExecutor(){
		BoundFunctions c = new BoundFunctions();
		actionClass = c.getClass();
		myBindings = new HashMap<Integer, String>();
		
		Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("src/keybinder/Bindings.txt"));
		} catch (IOException e) {
			System.out.println("IOException");
		}
		
		for(String key : properties.stringPropertyNames()) {
			Integer keyInt = Integer.parseInt(key);
			String value = properties.getProperty(key);
			myBindings.put(keyInt, value);
		}
		
	}
	
	/**
	 * Executes the keybound functions.
	 * Call this function once in the "doFrame" method of the jgame JGEngine.
	 * 
	 * @param engine
	 * 		the main engine for the current instance of jgame
	 * 		usually, the parameter should be "this", as in "executeInput(this);"
	 */
	public void executeInput(JGEngine engine){ // this should be called each frame
		
		for(Integer k : myBindings.keySet()){
			if(engine.getKey(k)){
				doReflect(myBindings.get(k));
			}
			
		}

	}
	
	/**
	 * This method is used in executeInput().
	 * It reflectively invokes the method in the BoundFunctions
	 * class with name matching String action.
	 * 
	 * @param action
	 * 		the name of the function to be invoked
	 */
	private void doReflect(String action){
		
		BoundFunctions c = new BoundFunctions();
		
		Object[] args = new Object[0];
		
		Method[] allMethods = actionClass.getDeclaredMethods();
		
		for (Method m : allMethods) {
			String mname = m.getName();
				if (mname.startsWith(action)){
					try {
						m.invoke(c, args);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
		 }
		
	}
	
	/**
	 * Reflectively retrieves the names of the methods in
	 * the BoundFunctions class and returns them as
	 * an ArrayList of Strings.
	 * 
	 * @return
	 */
	/**
	 * @return
	 */
	protected static ArrayList<String> getMethods(){
		
		Class<?> actionClass = new BoundFunctions().getClass();
		
		Method[] allMethods = actionClass.getDeclaredMethods();
		ArrayList<String> methodNames = new ArrayList<String>();
		
		for(Method m : allMethods){
			methodNames.add(m.getName());
		}
		
		return methodNames;
		
	}

}
