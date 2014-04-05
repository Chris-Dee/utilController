package ColorChooser;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * @author Cody Lieu
 * ColorChooser menu where the user can select a color by clicking on a swatch in the palette.
 */
public class ColorChooser extends JPanel implements ChangeListener {

	protected JColorChooser tcc;

	/**
	 * Creates a Color Chooser JPanel and adds a Change Listener
	 * that can be implemented differently depending on the game.
	 * The commented code is an example of the implementation
	 * for an ActionListener method (in another class)
	 * to call a pop up ColorChooser menu.
	 */
	
	/*JFrame frame = new JFrame("Color Chooser");
	ColorChooser colorChooser = new ColorChooser();
	JComponent newContentPane = colorChooser;
	newContentPane.setOpaque(true);
	frame.setContentPane(newContentPane);
	frame.pack();
	frame.setVisible(true);*/
	
	public ColorChooser() {
		super(new BorderLayout());

		tcc = new JColorChooser(Color.black);
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Choose Text Color"));
		
		add(tcc, BorderLayout.PAGE_END);
	}

	public void stateChanged(ChangeEvent e) {
		Color newColor = tcc.getColor();
		/*Do something with the newColor by calling an updateColor(newColor) method*/
	}
}