package Util;

import java.awt.event.ActionListener;

import javax.swing.*;

public class WinUtil
{

	public static enum MenuItemType
	{
		ITEM_PLAIN, ITEM_CHECK, ITEM_RADIO
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

		menu = new JMenu();
		menu.setText(menuText);
		menu.setName(menuName);

		// Optionales Tastaturk�rzel hinzuf�gen
		if (shortKey > 0)
			menu.setMnemonic(shortKey);

		menuBar.add(menu);

		return menu;

	}

	/**
	 * <li><b><i>createMenuItem</i></b>
	 * <br>
	 * <br>
	 * public JMenuItem createMenuItem(JMenu menu, String miName, MenuItemType miType, ActionListener actionListener,<br>&nbsp String sText, String imageFile, int shortKey, String sToolTip)
	 * <br>
	 * <br>
	 * Erstellt einen Men�eintrag.
	 * <br>
	 * <br>
	 * @param menu
	 * 	- Das Men�, zu dem dieser Men�eintrag geh�rt.
	 * @param miName
	 * - Optionaler Name des Men�eintrags oder <b>nulMenul</b>.
	 * @param miType
	 * 	- Der Typ des Men�eintrags <b>MenuItemType</b>.
	 * @param actionListener
	 *  - Der ActionListener, der verwendet werden soll, wenn der Men�eintrag ausgew�hlt wurde oder <b>null</b>.
	 * @param sText
	 * 	- Der Text des Men�eintrags.
	 * @param image
	 * 	- Symbol, welches links vor dem Text angezeigt werden soll oder <b>null</b>.
	 * @param shortKey
	 * 	- Optionales Tastaturk�rzel oder <b>0</b>.
	 * @param sToolTip
	 * 	- Optionaler Text f�r den Tooltip oder <b>null</b>.
	 * @return
	 * 	  Men�eintrag.
	 */
	public static JMenuItem createMenuItem(JMenu menu, String miName, MenuItemType miType, ActionListener actionListener, String miText, ImageIcon image,
			int shortKey, String miToolTip)
	{
		// Erstellen eines MenuItems MENU_PLAIN
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
		
		// Name desd Men�eintrags
		menuItem.setName(miName);
		
		// Menu Text hinzuf�gen
		menuItem.setText(miText);

		// Optionales Image hinzuf�gen
		menuItem.setIcon(image);
		
		// Optionales Tastaturk�rzel hinzuf�gen
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

}
