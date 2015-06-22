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
	 * Erstellt einen Menü. <br>
	 * <br>
	 * 
	 * @param menuBar
	 *            - Die Menüleiste, zu dem dieses Menü gehört.
	 * @param menuText
	 *            - Der Text des Menüs.
	 * @param menuName
	 *            - Optionaler Name des Menüs oder <b>null</b>.
	 * @param shortKey
	 *            - Optionales Tastaturkürzel oder <b>0</b>.
	 * @return Menü.
	 */
	public static JMenu createMenu(JMenuBar menuBar, String menuText, String menuName, int shortKey)
	{
		JMenu menu = null;

		menu = new JMenu();
		menu.setText(menuText);
		menu.setName(menuName);

		// Optionales Tastaturkürzel hinzufügen
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
	 * Erstellt einen Menüeintrag.
	 * <br>
	 * <br>
	 * @param menu
	 * 	- Das Menü, zu dem dieser Menüeintrag gehört.
	 * @param miName
	 * - Optionaler Name des Menüeintrags oder <b>nulMenul</b>.
	 * @param miType
	 * 	- Der Typ des Menüeintrags <b>MenuItemType</b>.
	 * @param actionListener
	 *  - Der ActionListener, der verwendet werden soll, wenn der Menüeintrag ausgewählt wurde oder <b>null</b>.
	 * @param sText
	 * 	- Der Text des Menüeintrags.
	 * @param image
	 * 	- Symbol, welches links vor dem Text angezeigt werden soll oder <b>null</b>.
	 * @param shortKey
	 * 	- Optionales Tastaturkürzel oder <b>0</b>.
	 * @param sToolTip
	 * 	- Optionaler Text für den Tooltip oder <b>null</b>.
	 * @return
	 * 	  Menüeintrag.
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
		
		// Name desd Menüeintrags
		menuItem.setName(miName);
		
		// Menu Text hinzufügen
		menuItem.setText(miText);

		// Optionales Image hinzufügen
		menuItem.setIcon(image);
		
		// Optionales Tastaturkürzel hinzufügen
		if (shortKey > 0)
			menuItem.setMnemonic(shortKey);
		
		
		// Optionalen Tooltip hinzufügen
		menuItem.setToolTipText(miToolTip);

		// ActionListener hinzufügen
		menuItem.addActionListener(actionListener);
		
		
		// Menüeintrag zum Menü hinzufügen
		menu.add(menuItem);
		
		// Rückgabe des Menueintrags
		return menuItem;

	}

}
