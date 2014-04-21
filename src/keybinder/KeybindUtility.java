package keybinder;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.aplu.xboxcontroller.XboxController;
import ch.aplu.xboxcontroller.XboxControllerAdapter;

@SuppressWarnings("serial")
public class KeybindUtility extends JFrame {
	private static final String WAIT_STATUS = "    Click Set to Bind, Click Clear to Delete Binding    ";
	private static final String CHANGE_STATUS = "    Press the Key You Wish to Bind to Action    ";
	private XboxController xc=new XboxController();
	private JLabel statusLabel = new JLabel(WAIT_STATUS);
	private List<String> commandList;
	//private Map<JButton,String> commandMap=new HashMap<JButton,String>();
	public Map<Integer, String> keyBindings = new HashMap<Integer, String>();
	public Map<JButton, String> buttonToCommandMap = new HashMap<JButton, String>();
	private Map<JButton, JLabel> buttonMap = new HashMap<JButton, JLabel>();
	/**
	 * A utility class to easily swap meaningful key inputs for the editor as
	 * well as for the game being created.
	 */
	public KeybindUtility(List<String> commands) {
		commandList = commands;
		initialize();
	}

	/**
	 * Creates the GUI for the Keybind Utility to let the user easily map the
	 * keyboard inputs
	 */
	private void initialize() {
		XboxAdapter adapt=new XboxAdapter();
		adapt.setKeys(this);
		xc.addXboxControllerListener(adapt);
		if(!xc.isConnected())
			System.out.println("controller not connected");
		else System.out.println("connected");
			

				
		//}
		setTitle("Define User Input");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFocusable(true);
		JPanel shell = new JPanel();
		shell.setLayout(new BoxLayout(shell, BoxLayout.Y_AXIS));
		createBindingPanels(shell);
		add(shell);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);

	}
	public boolean isBoundAlready(int keyCode) {
		boolean alreadyBound = false;

		for (Integer event : keyBindings.keySet()) {
			if (event == keyCode) {
				alreadyBound = true;
			}
		}
		return alreadyBound;
	}
	/**
	 * Generates a constantly present key listener that will update a KeyEvent
	 * buffer variable with the most recently released key.
	 */
	private void generateButtonListener(final JLabel label,
			final String command, final JButton button) {

		button.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// Do nothing - must be present for interface
			}

			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				setBinding(isBoundAlready(keyCode),e.getKeyCode(),command);
				

			}

			

			public void keyTyped(KeyEvent e) {
				// Do nothing - must be present for interface
			}

		});
		//button.
	}

	/**
	 * Generates the GUI panels for the key-binding interface. This GUI is
	 * composed of individual panels that all hold the labels for the associated
	 * command, keybinding, and set and clear buttons to set a new binding or
	 * clear an existing one.
	 * 
	 * @param framePanel
	 *            The panel that will hold all of the individual component
	 *            panels
	 */
	private void createBindingPanels(final JPanel framePanel) {
		for (final String command : commandList) {
			JPanel componentPanel = new JPanel();
			componentPanel.setLayout(new BoxLayout(componentPanel,
					BoxLayout.X_AXIS));
			JLabel commandLabel = new JLabel(command + "    ");
			JLabel binding = new JLabel("<>      ");
			JButton setButton = new JButton("Set");
			buttonMap.put(setButton, binding);
			buttonToCommandMap.put(setButton, command);
			createSetListener(setButton, binding);
			JButton clearButton = new JButton("Clear");
			buttonToCommandMap.put(clearButton, command);
			createClearListener(clearButton, binding);
			componentPanel.add(commandLabel);
			componentPanel.add(binding);
			componentPanel.add(setButton);
			componentPanel.add(clearButton);
			framePanel.add(componentPanel);

		}
		statusLabel.setAlignmentX(CENTER_ALIGNMENT);
		framePanel.add(statusLabel);
		JButton saveButton = new JButton("Save");
		saveButton.setAlignmentX(CENTER_ALIGNMENT);
		createSaveListener(saveButton);
		framePanel.add(saveButton);

	}

	/**
	 * Creates a listener for a particular panel's CLEAR button
	 * 
	 * @param clearButton
	 * @param binding
	 *            The label that describes the bound key event
	 */
	private void createClearListener(final JButton clearButton, final JLabel binding) {
		clearButton.setFocusable(false);
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// System.out.println("Clear Current Binding");
				binding.setText("<>      ");
//				try {
//					(new Robot()).keyRelease(KeyEvent.VK_A);
//				} catch (AWTException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				for(Integer key : keyBindings.keySet()) {
					System.out.println(buttonToCommandMap.get(clearButton));
					if(keyBindings.get(key).equals(buttonToCommandMap.get(clearButton))) {
						keyBindings.remove(key);
						System.out.println(isBoundAlready(key));
					}
				}
				
				statusLabel.setText(WAIT_STATUS);

			}

		});

	}

	/**
	 * Creates a listener for a particular panel's CLEAR button
	 * 
	 * @param setButton
	 * @param binding
	 *            The label that describes the bound key event
	 * @param command
	 *            The label that describes the method the panel controls
	 */
	public void setBinding(boolean alreadyBound, int keyCode,String command){
		JLabel label=buttonMap.get(findFocused());
		if (alreadyBound) {
			statusLabel.setText("Key Already Bound");
			//button.setFocusable(false);
		} else {
			keyBindings.put(keyCode, command);
			label.setText(keyCode
					+ "        ");
			statusLabel.setText(WAIT_STATUS);
			
		}

	}
	private void createSetListener(final JButton setButton,
			final JLabel binding) {
		setButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				statusLabel.setText(CHANGE_STATUS);
				generateButtonListener(binding, buttonToCommandMap.get(setButton), setButton);

			}

		});
	}
	
	
	private void createSaveListener(JButton saveButton) {
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveMap();
				
			}
			
		});
	}

	/**
	 * This method allows the back-end to access the binding map created by the
	 * user
	 * 
	 * @return The keybinding map
	 */
	private void saveMap() {
		System.out.println("saving");
		
		Properties properties = new Properties();
		FileOutputStream outstream;
		try {
			outstream = new FileOutputStream("src/keybinder/Bindings.txt");
			for (Map.Entry<Integer, String> entry : keyBindings.entrySet()) {
				properties.put(entry.getKey().toString(), entry.getValue());
			}
			properties.store(outstream, "Key Bindings");
		} catch (IOException e) {
			System.out.println("IOException");
		}
		
		System.out.println(properties);
		
	}
	JButton findFocused(){
		for(JButton b:buttonMap.keySet()){
			if(b.isFocusOwner())
				return b;
		}
		return null;
		}
	}
