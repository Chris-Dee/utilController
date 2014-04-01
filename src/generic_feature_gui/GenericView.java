package generic_feature_gui;
import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;

// You don't ever need to edit this code to make your GUI! All you need to do is create your features, have them added to
// a set of Features, and then pass them into the viewer--the rest is all done for you.

public class GenericView {
	public static final String MENU_BAR = "menu bar";
	
	private static JFrame myFrame;
	private Map<String, JComponent> myPanels;
	private Set<Feature> myFeatures;
	
	public GenericView(Set<Feature> features){
		myFeatures = features;
		instantiateObjects();
		addAllFeatures();
		setUpFrame();
	}
	private void instantiateObjects() {
        myPanels = new HashMap<String, JComponent>();
		addWorkspaceObjects();
	}
	private void addWorkspaceObjects(){
		myFrame = new JFrame("Frame Title");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myPanels=new HashMap<String, JComponent>();
		myFrame.setJMenuBar((JMenuBar)myPanels.get(GenericView.MENU_BAR));
		instantiatePanels();
	}
	private void instantiatePanels() {
		myPanels.put(BorderLayout.NORTH, new JPanel());
		myPanels.put(BorderLayout.SOUTH, new JPanel());
		myPanels.put(BorderLayout.WEST, new JPanel());
		myPanels.put(BorderLayout.EAST, new JPanel());
		myPanels.put(BorderLayout.CENTER, new JPanel());
		myPanels.put(GenericView.MENU_BAR, new JMenuBar());
	}
	private void addAllFeatures() {
		for(Feature f: myFeatures){
			addFeature(f);
		}
	}
	private void addFeature(Feature f) {
		for(JComponent j: f.getFeature().keySet()){
			myPanels.get(f.getFeature().get(j)).add(j);
		}
	}
	private void setUpFrame() {
		myFrame.setJMenuBar((JMenuBar)myPanels.get(GenericView.MENU_BAR));
		for(String s: myPanels.keySet()){
			if(myPanels.get(s) instanceof JPanel)
				myFrame.add(myPanels.get(s), s);
		}
		myFrame.pack();
		myFrame.setVisible(true);
	}
}
