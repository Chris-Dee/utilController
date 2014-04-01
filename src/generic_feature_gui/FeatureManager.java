package generic_feature_gui;
import java.util.ArrayList;
import java.util.List;

// The FeatureManager is the solution to feature-to-feature communications. Sometimes one feature needs to know about the existence of
// another, and haphazardly passing features into each other leads to ugly dependencies. So, if one feature needs to refer to another,
// it need only call FeatureManager.getFeature(String s), where s is the name of the Feature (HelpPageFeature, etc) it is attempting to get.
// Then it can call any public methods that that feature may have for whatever purposes it needs.

public class FeatureManager {
	public static List<Feature> myFeatures = new ArrayList<Feature>();
	public static void addFeature(Feature f){
		myFeatures.add(f);
	}
	public static Feature getFeature(String s){
		Feature myFeature=null;
		for(Feature f: myFeatures){
			if(f.getClass().getName()==s)
				myFeature = f;
		}
		return myFeature;
	}
}
