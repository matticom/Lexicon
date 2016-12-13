package utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class WinUtil
{

	public static enum MenuItemType
	{
		ITEM_PLAIN, ITEM_CHECK, ITEM_RADIO
	}
	
	public static final Color ULTRA_LIGHT_GRAY = createColor(200, 200, 200);
	public static final Color DARKER_GRAY = createColor(80, 80, 80);
	public static final Color ULTRA_DARK_GRAY = createColor(50, 50, 50);
	public static final Color LIGHT_BLACK = createColor(30, 30, 30);
	public static final Color COOL_BLUE = createColor(0, 178, 238);

	// Verhinder, dass eine Instanz dieser Klasse erstellt werden kann.
	// Diese Klasse enth�lt nur �ffentliche und statische Methoden.
	private WinUtil()
	{

	}

	/**
	 * <li><b><i>createMenu</i></b> <br>
	 * <br>
	 * public JMenu createMenu(JMenuBar menuBar, String menuText, String menuName, int shortKey) <br>
	 * <br>
	 * Erstellt einen Men�. <br>
	 * <br>
	 * 
	 * @param menuBar
	 *            - Die Men�leiste, zu dem dieses Men� geh�rt.
	 * @param menuText
	 *            - Der Text des Men�s.
	 * @param menuName
	 *            - Optionaler Name des Men�s oder <b>null</b>.
	 * @param shortKey
	 *            - Optionales Tastaturk�rzel oder <b>0</b>.
	 * @return Men�.
	 */

	public static JMenu createMenu(JMenuBar menuBar, String menuText, String menuName, int shortKey)
	{

		JMenu menu = null;

		// Hinzuf�gen des Men�s
		menu = new JMenu();
		menu.setText(menuText);
		menu.setName(menuName);

		// Optionales Tastaturk�rzen hinzuf�gen
		if (shortKey > 0)
			menu.setMnemonic(shortKey);

		// Men� zur Menubar hin zuf�gen
		menuBar.add(menu);

		// R�ckgabe des Men�s.
		return menu;
	}

	
	/**
	 * <li><b><i>createMenuItem</i></b> <br>
	 * <br>
	 * public JMenuItem createMenuItem(JMenu menu, String miName, MenuItemType miType, ActionListener actionListener,<br>
	 * &nbsp String miText, ImageIcon icon, int shortKey, String miToolTip) <br>
	 * <br>
	 * Erstellt einen Men�eintrag. <br>
	 * <br>
	 * 
	 * @param menu
	 *            - Das Men�, zu dem dieser Men�eintrag geh�rt.
	 * @param miName
	 *            - Optionaler Name des Men�eintrags oder <b>nulMenul</b>.
	 * @param miType
	 *            - Der Typ des Men�eintrags <b>MenuItemType</b>.
	 * @param actionListener
	 *            - Der ActionListener, der verwendet werden soll, wenn der Men�eintrag ausgew�hlt wurde oder <b>null</b>.
	 * @param miText
	 *            - Der Text des Men�eintrags.
	 * @param icon
	 *            - Symbol, welches links vor dem Text angezeigt werden soll oder <b>null</b>.
	 * @param shortKey
	 *            - Optionales Tastaturk�rzel oder <b>0</b>.
	 * @param miToolTip
	 *            - Optionaler Text f�r den Tooltip oder <b>null</b>.
	 * @return Men�eintrag.
	 */
	public static JMenuItem createMenuItem(JMenu menu, String miName, MenuItemType miType, ActionListener actionListener, String miText, ImageIcon icon,
			int shortKey, String miToolTip)
	{

		// Men� Item erstellen
		JMenuItem menuItem = new JMenuItem();

		switch (miType)
		{
		case ITEM_RADIO:
			menuItem = new JRadioButtonMenuItem();
			break;

		case ITEM_CHECK:
			menuItem = new JCheckBoxMenuItem();
			break;
		}

		// Name des Men�eintrags
		menuItem.setName(miName);

		// Men� Text hinzuf�gen
		menuItem.setText(miText);

		// Optionales Image
		menuItem.setIcon(icon);

		// Optionales Tastaturk�rzel �bernehmen
		if (shortKey > 0)
			menuItem.setMnemonic(shortKey);

		// Optionalen Tooltip hinzuf�gen
		menuItem.setToolTipText(miToolTip);

		// ActionListener hinzuf�gen
		menuItem.addActionListener(actionListener);

		// Men�eintrag zum Men� hinzuf�gen
		menu.add(menuItem);

		// R�ckgabe des Menueintrags
		return menuItem;
	}

	/**
	 * <li><b><i>createSubMenu</i></b> <br>
	 * <br>
	 * public JMenu createSubMenu(JMenu mainMenu, String menuText, String menuName, int shortKey) <br>
	 * <br>
	 * Erstellt ein Untermen�. <br>
	 * <br>
	 * 
	 * @param mainMenu
	 *            - Das Men�, zu dem das Untermen� hinzugef�gt werden soll.
	 * @param menuText
	 *            - Der Text des Men�s.
	 * @param menuName
	 *            - Optionaler Name des Untermen�s oder <b>null</b>.
	 * @param shortKey
	 *            - Optionales Tastaturk�rzel oder <b>0</b>.
	 * @return - Untermen�.
	 */
	public static JMenu createSubMenu(JMenu mainMenu, String menuText, String menuName, int shortKey)
	{

		// Hinzuf�gen des Men�s als Untermen� des Hauptmen�s
		JMenu menu = new JMenu();
		menu.setText(menuText);
		menu.setName(menuName);

		// Optionales Tastaturk�rzel hinzuf�gen
		if (shortKey > 0)
			menu.setMnemonic(shortKey);

		mainMenu.add(menu);

		return menu;

	}

	
	/**
	 * <li><b><i>createButton</i></b> <br>
	 * <br>
	 * public JButton createButton(String buttonText, int x, int y, int width, int height, Border border, Color background, <br>
	 * &nbsp ActionListener listener, String btnName, String btnToolTip, boolean setOpaque, boolean setFocusPainted, Color foreground) <br>
	 * <br>
	 * Erstellt einen Button. <br>
	 * <br>
	 * 
	 * @param buttonText
	 *            - Beschriftung des Buttons
	 * @param x
	 *            - Optionale x-Koordinate des Setzpunktes <b>0</b>.
	 * @param y
	 *            - Optionale y-Koordinate des Setzpunktes <b>0</b>.
	 * @param width
	 *            - Optionale Breite des Buttons <b>0</b>.
	 * @param height
	 *            - Optionale H�he des Buttons <b>0</b>.
	 * @param border
	 *            - Optionale Umrahmung des Buttons <b>null</b>.
	 * @param background
	 *            - Optionale Setzt Hintergrundfarbe <b>null</b>.      
	 * @param shortKey
	 *            - Optionale Hintergrundfarbe des Buttons <b>null</b>.
	 * @param actionListener
	 *            - ActionListener
	 * @param btnName
	 *            - Name des Buttons
	 * @param btnToolTip
	 *            - ToolTip des Buttons
	 * @param setOpaque
	 *            - Hat der Button einen Hintergrund
	 * @param setFocusPainted
	 *            - Soll der Focus angezeigt werden
	 * @param foreground
	 *            - Optionale Setzt Schriftfarbe <b>null</b>.     
	 * @return - JButton.
	 */
	public static JButton createButton(String buttonText, int x, int y, int width, int height, Border border, Color background, ActionListener actionListener,
			String btnName, String btnToolTip, boolean setOpaque, boolean setFocusPainted, Color foreground)
	{

		// Weitere Formatierungen
		//
		// String fontFamily = this.getContentPane().getFont().getFamily();
		// int fontStyle = Font.PLAIN;
		// int fontSize = this.getContentPane().getFont().getSize();
		// int newSize = 0;

		// BorderFactory.createLineBorder(Color.BLACK)
		// new EmptyBorder(0, 5, 0, 5)

		JButton btn = new JButton(buttonText);
		
		if(x != 0 && y != 0 && width != 0 && height != 0)
			btn.setBounds(x, y, width, height);
		
		if (border != null)
			btn.setBorder(border);
		
		if (background != null)
			btn.setBackground(background);
		
		btn.addActionListener(actionListener);
		btn.setName(btnName);
		btn.setToolTipText(btnToolTip);
		btn.setOpaque(setOpaque);
		btn.setFocusPainted(setFocusPainted);
		
		if (foreground != null)
			btn.setForeground(foreground);
		
		return btn;

	}

	/**
	 * <li><b><i>createLabel</i></b> <br>
	 * <br>
	 * public JLabel createLabel(String LblText, int x, int y, int width, int height, Border border, Color background,<br>
	 * &nbsp String lblName, String lblToolTip, Color foreground) <br>
	 * <br>
	 * Erstellt einen Button. <br>
	 * <br>
	 * 
	 * @param LblText
	 *            - Beschriftung des Labels
	 * @param x
	 *            - Optionale x-Koordinate des Setzpunktes <b>0</b>.
	 * @param y
	 *            - Optionale y-Koordinate des Setzpunktes <b>0</b>.
	 * @param width
	 *            - Optionale Breite des Labels <b>0</b>.
	 * @param height
	 *            - Optionale H�he des Labels <b>0</b>.
	 * @param border
	 *            - Optionale Umrahmung des Labels <b>null</b>.
	 * @param background
	 *            - Optionale Setzt Hintergrundfarbe <b>null</b>.     
	 * @param shortKey
	 *            - Optionale Hintergrundfarbe des Labels <b>null</b>.
	 * @param lblName
	 *            - Name des Labels
	 * @param lblToolTip
	 *            - ToolTip des Labels
	 * @param foreground
	 *            - Optionale Setzt Schriftfarbe <b>null</b>.
	 * @return - JLabel.
	 */
	public static JLabel createLabel(String LblText, int x, int y, int width, int height, Border border, Color background, 
										String lblName, String lblToolTip, Color foreground)
	{

		JLabel lbl = new JLabel(LblText);
		
		if(x != 0 && y != 0 && width != 0 && height != 0)
		lbl.setBounds(x, y, width, height);
		
		if (border != null)
			lbl.setBorder(border);
		
		if (background != null)
			lbl.setBackground(background);
		
		lbl.setName(lblName);
		lbl.setToolTipText(lblToolTip);
		
		if (foreground != null)
			lbl.setForeground(foreground);
		
		return lbl;

	}
	
	
	/**
	 * <li><b><i>createColor</i></b> <br>
	 * <br>
	 * public static Color createColor(int R, int G, int B) <br>
	 * <br>
	 * Erstellt eine Farbe aus RGB ins Java Color Format (HSB). <br>
	 * <br>
	 * 
	 * @param R
	 *            - Rot-Anteil der Farbe. <b>0-255</b>.
	 * @param G
	 *            - Gelb-Anteil der Farbe. <b>0-255</b>.
	 * @param B
	 *            - Blau-Anteil der Farbe. <b>0-255</b>.
	 * @return Farbe als Color-Objekt.
	 */
	public static Color createColor(int R, int G, int B)
	{
		float[] HSBArray = new float[3];
		
		Color.RGBtoHSB(R,G,B,HSBArray);
		return Color.getHSBColor(HSBArray[0], HSBArray[1], HSBArray[2]);
	}
}
