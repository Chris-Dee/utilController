package keybinder;

import java.io.*;
import java.lang.reflect.Constructor;
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
	 * @throws ClassNotFoundException 
	 */
	public BindingExecutor(String methodsClassName) throws ClassNotFoundException{
//		BoundFunctions c = new BoundFunctions();
//		actionClass = c.getClass();
		actionClass = Class.forName(methodsClassName);
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
		
//		BoundFunctions c = new BoundFunctions();
		Constructor<?> ctor;
		Object actionObject = null;
		try {
			ctor = actionClass.getConstructor(String.class);
			actionObject = ctor.newInstance();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		
		actionObject = actionClass.cast(actionObject);
		
		Object[] args = new Object[0];
		
		Method[] allMethods = actionClass.getDeclaredMethods();
		
		for (Method m : allMethods) {
			String mname = m.getName();
				if (mname.startsWith(action)){
					try {
						m.invoke(actionObject, args);
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
	protected ArrayList<String> getMethods(){
		
		Method[] allMethods = actionClass.getDeclaredMethods();
		ArrayList<String> methodNames = new ArrayList<String>();
		
		for(Method m : allMethods){
			Bind annos = m.getAnnotation(Bind.class);
            if (annos != null && m.getParameterTypes().length==0) {
            	methodNames.add(m.getName());
            }
		}
		
		if(methodNames.isEmpty()){
			for(Method m : allMethods){
				if(m.getParameterTypes().length==0){
					methodNames.add(m.getName());
				}
			}
        }
		
		return methodNames;
		
	}

}
