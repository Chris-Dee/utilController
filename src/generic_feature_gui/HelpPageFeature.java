package generic_feature_gui;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;


import java.awt.*;
import java.awt.event.*;

import java.net.URL;
import java.io.IOException;

// This is an example of how you would create a feature, from my partner's code for the SLogo project. As you can see, all
// you have to do is create the components you need, create and add any applicable implementation and ActionListeners, and
// add the components to the myComponents map. This makes feature implementation very easy and reduces merge conflicts--
// if two people are working on different features they don't need to worry about each other very much.

public class HelpPageFeature extends Feature{
	private JButton myHelpButton;
	private JFrame frame;
	public HelpPageFeature(){
		myHelpButton = new JButton("Help");
		myComponents.put(myHelpButton, BorderLayout.NORTH);
		myHelpButton.addActionListener(new Action());
	}
	
	private class Action implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frame = new JFrame("HELP PAGE");
    		frame.add(new createHelpPage());
            frame.pack();
            frame.setVisible(true);
		}	
	}
	
	public class createHelpPage extends JPanel implements TreeSelectionListener {
		/**
		 * automatic java generated stuff 
		 */
		private static final long serialVersionUID = 1L;
		private JEditorPane htmlPane;
		private JTree tree;
		private URL helpURL;
		private static final boolean DEBUG = false;
		
		public createHelpPage() {
			
			DefaultMutableTreeNode top = new DefaultMutableTreeNode("Contents of Help Manual");
			createNodes(top);
			tree = new JTree(top);
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

			tree.addTreeSelectionListener(this);
			//Create the scroll pane and add the tree to it. 
			JScrollPane treeView = new JScrollPane(tree);

			//Create the HTML viewing pane.
			htmlPane = new JEditorPane();
			htmlPane.setEditable(false);
			initHelp();
			JScrollPane htmlView = new JScrollPane(htmlPane);

			//Add the scroll panes to a split pane.
			JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			splitPane.setTopComponent(treeView);
			splitPane.setBottomComponent(htmlView);

			Dimension minimumSize = new Dimension(100, 50);
			htmlView.setMinimumSize(minimumSize);
			treeView.setMinimumSize(minimumSize);
			splitPane.setDividerLocation(100); 
			splitPane.setPreferredSize(new Dimension(500, 300));

			add(splitPane);
		}

		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();

			if (node == null) return;

			Object nodeInfo = node.getUserObject();
			if (node.isLeaf()) {
				leafInfo leaf = (leafInfo)nodeInfo;
				displayURL(leaf.leafURL);
				if (DEBUG) {
					System.out.print(leaf.leafURL + ":  \n    ");
				}
			} else {
				displayURL(helpURL); 
			}
			if (DEBUG) {
				System.out.println(nodeInfo.toString());
			}
		}

		private class leafInfo {
			public String leaf;
			public URL leafURL;

			public leafInfo(String l, String filename) {
				leaf = l;
				leafURL = getClass().getResource(filename);
				if (leafURL == null) {
					System.err.println("Couldn't find file: "
                            + filename);
				}
			}

			public String toString() {
				return leaf;
			}
		
		}
		
		private void initHelp() {
			String s = "Welcome.html";
			helpURL = getClass().getResource(s);
			if (helpURL == null) {
				System.err.println("Couldn't open help file: " + s);
			} else if (DEBUG) {
				System.out.println("Help URL is " + helpURL);
			}
			displayURL(helpURL);
		}
		
		private void displayURL(URL url) {
			try {
				if (url != null) {
					htmlPane.setPage(url);
				} else { //null url
					htmlPane.setText("File Not Found");
					if (DEBUG) {
						System.out.println("Attempted to display a null URL.");
					}
				}
			} catch (IOException e) {
				System.err.println("Attempted to read a bad URL: " + url);
			}
		}
		
		private void createNodes(DefaultMutableTreeNode top) {
	        DefaultMutableTreeNode category = null;
	        DefaultMutableTreeNode leaf = null;
	        
	        /* Our GUI Stuff Commented out for now */
//	        category = new DefaultMutableTreeNode("OUR GUI STUFF");
//	        top.add(category);
//	        leaf = new DefaultMutableTreeNode(new leafInfo
//		            ("OUR GUI STUFF",
//		            "BasicSyntax.html"));
//		        category.add(leaf);
		        
		        
	        category = new DefaultMutableTreeNode("BASIC SYNTAX");
	        top.add(category);
	        leaf = new DefaultMutableTreeNode(new leafInfo
		            ("BASIC SYNTAX",
		            "BasicSyntax.html"));
		        category.add(leaf);
		        
		        
	        /* TURTLE COMMANDS*/
	        category = new DefaultMutableTreeNode("TURTLE COMMANDS");
	        top.add(category);

	        
	        leaf = new DefaultMutableTreeNode(new leafInfo
	            ("TURTLE COMMANDS",
	            "TurtleCommands.html"));
	        category.add(leaf);

	        category = new DefaultMutableTreeNode("TURTLE QUERIES");
	        top.add(category);
	       
	        leaf = new DefaultMutableTreeNode(new leafInfo
	            ("TURTLE QUERIES",
	            "TurtleQueries.html"));
	        category.add(leaf);
	        
	        /* math operations*/
	        category = new DefaultMutableTreeNode("MATH OPERATIONS");
	        top.add(category);
	        
	        leaf = new DefaultMutableTreeNode(new leafInfo
		            ("MATH OPERATIONS",
		            "MathOperations.html"));
		    category.add(leaf);
		    
		    category = new DefaultMutableTreeNode("BOOLEAN OPERATIONS");
	        top.add(category);
	        leaf = new DefaultMutableTreeNode(new leafInfo
		            ("BOOLEAN OPERATIONS",
		            "BooleanOperations.html"));
		        category.add(leaf);
		        
	        category = new DefaultMutableTreeNode("CONTROL STRUCTURES");
	        top.add(category);
	        leaf = new DefaultMutableTreeNode(new leafInfo
		            ("CONTROL STRUCTURES",
		            "ControlStructures.html"));
		        category.add(leaf);
	        
		}
	} 
}

	



