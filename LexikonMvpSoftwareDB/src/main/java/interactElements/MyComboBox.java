package interactElements;

import java.awt.Dimension;

import javax.swing.JComboBox;

import eventHandling.ComboBoxEventTransferObject;
import eventHandling.PanelEventTransferObject;
import eventHandling.Refillable;
import eventHandling.Updatable;

public class MyComboBox extends JComboBox implements Refillable, Updatable {

	protected final int GERMAN = 1;
	protected final int SPANISH = 2;
	
	@Override
	public void refillComboBox(ComboBoxEventTransferObject e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {
		// TODO Auto-generated method stub
		
	}
}
