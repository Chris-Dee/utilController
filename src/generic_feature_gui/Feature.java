package generic_feature_gui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

// To create a feature, create a new class that extends Feature, and add all associated components to the myComponents map that it
// inherits, adding the component as the key with the BorderLayout region you want it to occupy as the value (write MENU_BAR as the value if
// rather than in the window you want the component to appear in the menu bar). You will also want to give these components any listeners
// you want them to have. Any components in the myComponents map will be automatically put in their place in the view as long as your
// new feature is being added to the set of features that is passed into the GenericView constructor.

public class Feature {
	Map<JComponent, String> myComponents = new HashMap<JComponent, String>();
	public Feature(){
		FeatureManager.addFeature(this);
	}
	public Map<JComponent, String> getFeature(){
		return myComponents;
	}
}
