package interactElements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import utilities.ExtendedListItem;
import utilities.ListItem;


public class ComboBoxCellRenderer implements ListCellRenderer<Object>
{
	
	private DefaultListCellRenderer defaultRenderer;
	
	public ComboBoxCellRenderer()
	{
		defaultRenderer = new DefaultListCellRenderer();
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		
		Color newForeground = list.getForeground();
		String newText = null;
		ListItem listItem;
		ExtendedListItem cboItem;
		
		JLabel itemLabel = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		if (value instanceof ListItem)
		{
			listItem = (ListItem)value;
			
			if (listItem.getDisplayMember() instanceof ExtendedListItem)
			{
				cboItem = (ExtendedListItem)listItem.getDisplayMember();
				
				// Aufbereiten der der Daten aus dem ComboBoxItem 
				itemLabel.setFont(list.getFont().deriveFont(Font.PLAIN, cboItem.getFontsize()));   
				newText = cboItem.getItemText();
			}
		}
		else
		{
			itemLabel.setFont(list.getFont());
			newText = value.toString();
		}
		
		// Setzen der Werte f�r die Anzeige
		
		if (!isSelected)
		{
			itemLabel.setForeground(newForeground);
		}
				
		itemLabel.setText(newText);
				
		// R�ckgabe des modifizierten Eintrags
		return itemLabel;
	}


}
