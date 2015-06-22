import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import Util.StatusBar;
import Util.WinUtil;

public class Song_Table extends JDialog implements ActionListener,
		ListSelectionListener, KeyListener, MouseListener, ItemListener
{

	private JMenuBar menuBar;
	private JMenu menuDatei, menuBearbeiten;
	private JMenuItem miNeu, miAendern, miLoeschen, miSchliessen, mi1, mi2;

	private JTable Tabelle;
	private JScrollPane jspTabelle;

	private JLabel jlabel0, jlabel1, jlabel2, jlabel3, jlabel4, jlabel5;
	private JTextField tbID, tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name, tbCountry;
	private JButton btnFilter;
	private Integer distBetweenBox, distLeftBorder;

	private JPopupMenu pm;

	private StatusBar statusBar;
	private Component owner;

	private String cdAuthor, cdName;
	private String global_lastPart_filterConditions = "";
	
	private boolean showLineOutOfFilter = true;
	
	private static JComboBox<Object> cb_TypeMusic_2;
	
	//public Song_Table()
	//{
		//initializeComponents();
	// }
	
	public Song_Table(String cdAuthor, String cd_Name)
	{
		this.cdAuthor = cdAuthor;
		this.cdName = cd_Name;
		initializeComponents();
	}

	private void initializeComponents()
	{
		

		this.setLayout(null);
//		Color defaultColor = 	new Color(255, 255, 240);
//		this.setBackground(defaultColor);
		
		this.setTitle("List all Songs from CD: " + cdName);
		
		this.setSize(998, 540);
		//this.setBounds(400, 600, 990, 540);
		
		
		this.setBackground(Color.red);
		//this.setResizable(false);

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		menuBar = new JMenuBar();

		menuDatei = WinUtil.createMenu(menuBar, "Datei", null, 'D');
		miSchliessen = WinUtil.createMenuItem(menuDatei, null,
				WinUtil.MenuItemType.ITEM_PLAIN, this, "Schließen", null, 'S',
				null);

		menuBearbeiten = WinUtil.createMenu(menuBar, "Bearbeiten", null, 'B');
		miNeu = WinUtil.createMenuItem(menuBearbeiten, null,
				WinUtil.MenuItemType.ITEM_PLAIN, this, "Neu", null, 'N', null);
		miAendern = WinUtil.createMenuItem(menuBearbeiten, null,
				WinUtil.MenuItemType.ITEM_PLAIN, this, "Ändern", null, 'Ä',
				null);
		miLoeschen = WinUtil.createMenuItem(menuBearbeiten, null,
				WinUtil.MenuItemType.ITEM_PLAIN, this, "Löschen", null, 'L',
				null);

		this.setJMenuBar(menuBar);

		// Tabelle zur Anzeige der Datensätze
		Tabelle = new JTable();
		// keine mehrfachauswahl zulassen
		Tabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Solo 1 selecion
																		
		Tabelle.getSelectionModel().addListSelectionListener(this);
		Tabelle.addKeyListener(this);
		Tabelle.addMouseListener(this);

		// Zum Blättern der Tabelle
		jspTabelle = new JScrollPane(Tabelle);
		jspTabelle.setBounds(0, 100, 980, 350);
		// jspTabelle.setSize(this.getWidth()-20, 300);
		// this.add(jspTabelle, BorderLayout.CENTER);
		this.add(jspTabelle);

		// TextBox here:

		distBetweenBox = 15;
		distLeftBorder = 60;

		jlabel0 = new JLabel("CD: "+ cdName +"   Author: " +  cdAuthor);

		// Font labelFont = jlabel0.getFont();

		jlabel0.setFont(new Font(jlabel0.getFont().getFontName(), Font.BOLD, 18));
		jlabel0.setBounds(325, 1, 350, 25);
		// jlabel0.setSize(100, 25);
		jlabel0.setHorizontalAlignment(JLabel.CENTER);

		// this.add(Box.createHorizontalGlue());
		this.add(jlabel0);
		// this.add(Box.createHorizontalGlue());

		jlabel1 = new JLabel("Typ_Music");
		jlabel1.setBounds(distLeftBorder, 25, 100, 25);
		this.add(jlabel1);

//		tbTypMusic = new JTextField();
//		tbTypMusic.setBounds(distLeftBorder, 50, 100, 25);
//		this.add(tbTypMusic);

		cb_TypeMusic_2 = new JComboBox<>();
		cb_TypeMusic_2.setBounds(distLeftBorder, 50, 100, 25);
		cb_TypeMusic_2.addItemListener(this);
		this.add(cb_TypeMusic_2);
		
		populateAllComboBox();
		
		// Author
		jlabel2 = new JLabel("Author");
		jlabel2.setBounds(distLeftBorder + jlabel1.getWidth()
				+ (distBetweenBox) * 1, 25, 150, 25);
		this.add(jlabel2);
		
		
		tbAuthor = new JTextField();
		tbAuthor.setBounds(distLeftBorder + jlabel1.getWidth()
				+ (distBetweenBox) * 1, 50, 150, 25);
		//tbAuthor.setEditable(false);
		//tbAuthor.setEnabled(false);
		tbAuthor.setText(cdAuthor);
		this.add(tbAuthor);
		
		
		
		// CD Name
		jlabel3 = new JLabel("CD_Name");
		jlabel3.setBounds(
				distLeftBorder + jlabel1.getWidth() + jlabel2.getWidth()
						+ (distBetweenBox) * 2, 25, 150, 25);
		this.add(jlabel3);
		
		tbCD_Name = new JTextField();
		tbCD_Name.setBounds(
				distLeftBorder + jlabel1.getWidth() + jlabel2.getWidth()
						+ (distBetweenBox) * 2, 50, 150, 25);
		//tbCD_Name.setEnabled(false);
		tbCD_Name.setText(cdName);
		this.add(tbCD_Name);

	
		
		// Song Name
		jlabel4 = new JLabel("Song_Name");
		jlabel4.setBounds(
				distLeftBorder + jlabel1.getWidth() + jlabel2.getWidth()
						+ jlabel3.getWidth() + (distBetweenBox) * 3, 25, 150,
				25);

		jlabel4.setHorizontalAlignment(jlabel4.RIGHT);
		this.add(jlabel4);

		tbSong_Name = new JTextField();
		tbSong_Name.setBounds(
				distLeftBorder + jlabel1.getWidth() + jlabel2.getWidth()
						+ jlabel3.getWidth() + (distBetweenBox) * 3, 50, 150,
				25);
		// tbSong_Name.setBackground(Color.green);
		this.add(tbSong_Name);

		jlabel5 = new JLabel("Country");
		jlabel5.setBounds(
				distLeftBorder + jlabel1.getWidth() + jlabel2.getWidth()
						+ jlabel3.getWidth() + jlabel4.getWidth()
						+ (distBetweenBox) * 4, 25, 150, 25);
		jlabel5.setHorizontalAlignment(jlabel4.RIGHT);
		this.add(jlabel5);

		tbCountry = new JTextField();
		tbCountry.setBounds(
				distLeftBorder + jlabel1.getWidth() + jlabel2.getWidth()
						+ jlabel3.getWidth() + jlabel4.getWidth()
						+ (distBetweenBox) * 4, 50, 150, 25);
		this.add(tbCountry);

		btnFilter = new JButton("Filter");
		btnFilter
				.setBounds(
						distLeftBorder + jlabel1.getWidth()
								+ jlabel2.getWidth() + jlabel3.getWidth()
								+ jlabel4.getWidth() + jlabel5.getWidth()
								+ (distBetweenBox) * 5, 50, 80, 25);
		btnFilter.addActionListener(this);
		this.add(btnFilter);

		// Statusleiste
		statusBar = new StatusBar();
		statusBar.setHorizontalAlignment(JLabel.CENTER);
		statusBar.setBounds(0, 445, this.getWidth(), 30);
		// statusBar.setPreferredSize(new Dimension(200, 30));
		// this.add(statusBar, BorderLayout.PAGE_END);
		this.add(statusBar);
		
		// 2015.03.27 Alopez We create the PopUpMenu
		pm = new JPopupMenu();
		
		mi1 = new JMenuItem("Update Dataline");
		mi1.addActionListener(this);

		//mi2 = new JMenuItem("Show all the songs of the CD");
		//mi2.addActionListener(this);

		pm.add(mi1);
		//pm.add(mi2);
		
		
	}

	public static void populateAllComboBox()
	{

		//2015.03.31 Alopez we full here the ComboBox
		//SELECT DISTINCT(Typ_Music) FROM `cd_songs`
		
		cb_TypeMusic_2.removeAllItems();
		
		cb_TypeMusic_2.addItem("");
		
		
		int number_rows = 0;
		
		String fullComboQuery = "SELECT DISTINCT(Typ_Music) FROM  cd_songs order by Typ_Music";
		//String fullComboQuery = "SELECT UNIQUE(Typ_Music) FROM  cd_songs";
		
		ResultSet rSet = DBConnection.executeQuery(fullComboQuery);
		
		
		try
		{
			rSet.last();
			number_rows =  rSet.getRow();
			rSet.beforeFirst();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Geting number_rows ComboBox: " + ex.getMessage(), "Mistake", JOptionPane.ERROR_MESSAGE);
		} 
		
		System.out.println("number_rows" + number_rows);
		
		try
		{
			
			for (int row = 1; row <= number_rows; row++)
			{
				rSet.next();
				//cb_TypeMusic_2.addItem(rSet);
				cb_TypeMusic_2.addItem(rSet.getObject(1));
			  
				
			}
			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Mistake fulling ComboBox getData: " + ex.getMessage(), "Mistake", JOptionPane.ERROR_MESSAGE);
		} 
	
	}
	private void initDialog()
	{
		this.setLocationRelativeTo(owner);
		this.setModal(true);

		statusBar.setText("Datensätze werden gelesen ...");

		showDataThread();

	}

	private void showDataThread()
	{

		// Thread zum Einlesen der Postleitzahlen verwenden.
		// Der Dialog wird sofort sichtbar und das Lesen der
		// Datensätze erfolgt im Hintergrund.
		
		//2015.03.30 Alopez instead of going direct to new ShowData())
		// i send here to isFilterParametersCorrect que tambien me envia a Showfdaa
		//Thread t = new Thread(new ShowData());
		// t.start();
		isFilterParametersCorrect();

	}

	public void showDialog()
	{
		initDialog();
		this.setVisible(true);
	}

	public void showDialog(Component owner)
	{
		this.owner = owner;
		showDialog();

	}

	public String insertQuaotes(String Name)
	{
		Name = "\"" + Name + "\"";
		return Name;
	}

	public void myFilter(String filterConditions)
	{
		// 2015.03.26 We send the part of query filterConditions to ShowData
		System.out.println(filterConditions);
		ShowData showData = new ShowData(filterConditions);
		showData.run();
	}

	public void resetLabelName()
	{
		//2015.03.30 Alopez we control here the name of the Frame and the lable0
		//tbAuthor, tbCD_Name
		
		//this.setTitle("List of Songs CD  " + cdName);
		// jlabel0 = new JLabel(cdName +" / " +  cdAuthor);
		
		
//		if((tbAuthor.getText().equals("")) || (tbCD_Name.getText().equals("")))
//		{
//			this.setTitle("List of Songs  " + tbCD_Name.getText() +" " +  tbAuthor.getText());
//			jlabel0.setText(tbCD_Name.getText() +" " +  tbAuthor.getText());
//		}
			
		
		//2015.03.31 Alopez in case all the fields are empty
		// tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name, tbCountry;
		if( cb_TypeMusic_2.getSelectedItem().equals("") && tbAuthor.getText().equals("") &&
				tbCD_Name.getText().equals("") && tbSong_Name.getText().equals("") && tbSong_Name.getText().equals(""))
		{
			this.setTitle("List all Songs in the Data Base");
			jlabel0.setText("List of Songs");	
		}
		
		
		//2015.03.31 Alopez if any of the filters is no empty we show that in the name of the label 
		// tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name, tbCountry;
		if( !cb_TypeMusic_2.getSelectedItem().equals("") || !tbAuthor.getText().equals("") ||
			!tbCD_Name.getText().equals("")  || !tbSong_Name.getText().equals("") || !tbCountry.getText().equals(""))
		{
			this.setTitle("All Songs: " + cb_TypeMusic_2.getSelectedItem().toString() + " " + tbAuthor.getText()
					+" " + tbCD_Name.getText() + " "+ tbSong_Name.getText() + "" 
				       + tbCountry.getText() );
			
			jlabel0.setText(cb_TypeMusic_2.getSelectedItem().toString() + " " + tbAuthor.getText()+" "
					       + tbCD_Name.getText() + " "+ tbSong_Name.getText() + "  " 
					       + tbCountry.getText());	
		}
		
		
		
		SwingUtilities.updateComponentTreeUI(this);
		
		
		
	}
	
	
	public boolean isFilterParametersCorrect()
	{
		// 2015.03.25 Alopez. 
		//+We get the filter information and then we send it to -->
		// ShowData --> MusikTableModel() and we paint it.
		// My texbox names: tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name,
		// tbCountry ;
		// If the query to filter is ok return true if not return false
		boolean valueReturn = false;
		String filterConditions;
		String queryTest;

		String sqlPiece_TypMusic = 	"";
		String sqlPiece_Author = 	"";
		String sqlPiece_CD_Name = 	"";
		String sqlPiece_Song_Name = "";
		String sqlPiece_Country = 	"";

//		if (!tbTypMusic.getText().isEmpty())
//			sqlPiece_TypMusic = " Typ_Music = " + insertQuaotes(tbTypMusic.getText()) + " AND ";
		
		//2015.04.01 Alopez we use the Combo to filter cb_TypeMusic_2. 
		if (!cb_TypeMusic_2.getSelectedItem().equals(""))
			sqlPiece_TypMusic = " Typ_Music = "+ insertQuaotes(cb_TypeMusic_2.getSelectedItem().toString()) + " AND ";

		if (!tbAuthor.getText().isEmpty())
			sqlPiece_Author = " Author = " + insertQuaotes(tbAuthor.getText()) + " AND ";

		if (!tbCD_Name.getText().isEmpty())
			sqlPiece_CD_Name = " CD_Name = " + insertQuaotes(tbCD_Name.getText()) + " AND ";

		if (!tbSong_Name.getText().isEmpty())
			sqlPiece_Song_Name = " Song_Name = " + insertQuaotes(tbSong_Name.getText()) + " AND ";

		if (!tbCountry.getText().isEmpty())
			sqlPiece_Country = " Country = " + insertQuaotes(tbCountry.getText()) + " AND";

		//System.out.println();

		// 2015.03.26 We check if all the textbox are empty, we take out the
		// where in the query
		if (sqlPiece_TypMusic == "" && sqlPiece_Author == ""
				&& sqlPiece_CD_Name == "" && sqlPiece_Song_Name == ""
				&& sqlPiece_Country == "")
			
			filterConditions = "";

		else
		{
			filterConditions = "where" + sqlPiece_TypMusic + sqlPiece_Author
					+ sqlPiece_CD_Name + sqlPiece_Song_Name + sqlPiece_Country;
			
			filterConditions = takeOutLastWordInString(filterConditions, "AND");
		}

		// System.out.println(filterConditions);

		// 2015.03.25 Alopez. We check here if the query with the filterConditions 
		// is gültig or not
		queryTest = "Select count(*) from cd_songs " + filterConditions;

		if (Globals.isValidQuery(queryTest))
		{
			valueReturn = true;
			global_lastPart_filterConditions = filterConditions;
			myFilter(filterConditions);
		}

		else
			// we send a message teelling taht the query is not valid

			JOptionPane
					.showMessageDialog(
							this,
							"<html> SORRY.<br> The filter parameters does not give back any result."
									+ "<br> Please chose other parameters to filter.</html>");

		// <html>I'm new to java.<br>I have no background in programming.<br>I
		// could use some help Thanks!</html>"

		
		return valueReturn;

	}

	
	public String takeOutLastWordInString(String SQL, String wordToDelete)
	{
		// 2015.03.26 This function take out last word inside a string

		String returnStringValue;
		returnStringValue = SQL.substring(0, SQL.lastIndexOf(wordToDelete));

		// System.out.println(returnStringValue);

		return returnStringValue;
	}

	private class ShowData implements Runnable
	{
		// 2015.03.25 We create a nre constructor to pass the filter conditions
		// first we create the generic empty and then we create a new one with
		// parameters

		private String filterCondition = "";

		public ShowData()
		{
			run();
		}

		public ShowData(String Filter)
		{
			this.filterCondition = Filter;
			run();
		}

		@Override
		public void run()
		{

			// Tabelle.setModel(new PLZTableModel());

			if (filterCondition.equals(""))
			{
				
				//String SQL = "SELECT * FROM CD_Songs where Author ="+ insertQuaotes(cdAuthor)+" AND CD_Name ="+ insertQuaotes(cdName)  +" ORDER BY ID";
				//String SQL = "SELECT * FROM CD_Songs where Author ="+ insertQuaotes(tbAuthor.getText())+" AND CD_Name ="+ insertQuaotes(tbCD_Name.getText())  +" ORDER BY ID";
				String SQL = "SELECT * FROM CD_Songs " + filterCondition + "  ORDER BY Author";
			
				System.out.println(SQL);
				
				Tabelle.setModel(new MusikTableModel(SQL));
				
			} else
			{
				System.out.println("filterCondition");
				
				//String SQL = "SELECT * FROM CD_Songs " + filterCondition + " AND Author ="+ insertQuaotes(cdAuthor)+" AND CD_Name ="+ insertQuaotes(cdName) +"  ORDER BY ID";
				String SQL = "SELECT * FROM CD_Songs " + filterCondition + "  ORDER BY ID";
				
				Tabelle.setModel(new MusikTableModel(SQL));
			}

			// Überschriften der Tabelle grösser und fett
			Font font = Tabelle.getTableHeader().getFont().deriveFont(Font.BOLD, 16.0f);
			Tabelle.getTableHeader().setFont(font);

			// Erste Spalte (PRIMARYKEY) unsichtbar machen
			setTableColumnInvisible(Tabelle, 0);

			// Breite der Spalte 'Postleitzahl' setzen
			setColumnWidth(Tabelle, 1, 100);

			// Weitere Zellformatierungen
			Tabelle.setRowHeight(21);
			Tabelle.setIntercellSpacing(new Dimension(5, 2));

			// Alle Spaltenüberschriften linksbündig ausrichten
			DefaultTableCellRenderer tableHeaderRenderer = (DefaultTableCellRenderer) Tabelle
					.getTableHeader().getDefaultRenderer();
			tableHeaderRenderer.setHorizontalAlignment(SwingConstants.LEFT);
			Tabelle.getTableHeader().setDefaultRenderer(tableHeaderRenderer);

			// CellRenderer zur optischen Aufwertung der Tabelle verwenden
			Tabelle.setDefaultRenderer(Object.class, new TableCellRenderer());

			Tabelle.setEnabled(Tabelle.getRowCount() > 0);

			if (Tabelle.getRowCount() > 0)
				setSelectedRow(Tabelle, 0);
			else
				statusBar.setText(null);

			miAendern.setEnabled(Tabelle.isEnabled());
			miLoeschen.setEnabled(Tabelle.isEnabled());

		}

	}

	private void setColumnWidth(JTable jTable, int colIndex, int colWidth)
	{

		// Wichtig ist die Reihenfolge der Aufrufe
		jTable.getColumnModel().getColumn(colIndex).setPreferredWidth(colWidth);

		// setMaxWidth() ist erforderlich um die maximale Spaltenbreite zu
		// setzen.
		// Durch den Multiplikator wird aber die Möglichkeit gegeben
		// die Spalte nachträglich um das n-fache zu vergrössern.
		jTable.getColumnModel().getColumn(colIndex).setMaxWidth(colWidth * 3);
	}

	private void setTableColumnInvisible(JTable jTable, int colIndex)
	{
		// Wichtig ist die Reihenfolge der Aufrufe
		jTable.getColumnModel().getColumn(colIndex).setWidth(0);
		jTable.getColumnModel().getColumn(colIndex).setMaxWidth(0);
		jTable.getColumnModel().getColumn(colIndex).setMinWidth(0);
		jTable.getColumnModel().getColumn(colIndex).setPreferredWidth(0);
		jTable.getColumnModel().getColumn(colIndex).setResizable(false);
	}

	private void setSelectedRow(JTable jTable, int rowIndex)
	{

		jTable.changeSelection(rowIndex, 0, false, true);

	}

	private void detailFormat(long PrimaryKey)
	{

		// System.out.println("PRIMARYKEY: " + PrimaryKey);
		// PLZForm dlg = new PLZForm(PrimaryKey);
		
		//2015.03.30 Alopez si PrimaryKey = -1 es un nuevo elemento si > -1  es un elemento de la tabla y por lo tanto un update
		Cd_Song_Form cdsf = new Cd_Song_Form(PrimaryKey, cdAuthor, cdName);
		cdsf.showDialog(this);

	
		
		// 2015.03.30 Alopez Todo esto es para posicionarnos despues de hacer el update o el nuevo insert
		if (PrimaryKey > -1)
		{
			// Änderungmodus
			updateTableRow(Tabelle.getSelectedRow());
			// Here we make the refresh from the table, after we change
			// something
			if (!showLineOutOfFilter == true)
			{
			   myFilter(global_lastPart_filterConditions);
			}

		} else
		{
			// Neuanlage Aber nur, wenn ein Datensatz hinzugefügt wurde
			// (dann ist der Primärschklüssel > - 1).

			if (cdsf.getPrimaryKey() > -1)
			// 2015.03.25 09:20 Alopez tengo que mirar para hace esto aqui creo que es
			// para refrescar
			// en caso de new insert recoje la id de Cd_Song_Form con
			// cdsf.getPrimaryKey(). Ahh ya es para posicionarnos en la linea de la nueva entrada con selectRowByValue
			{
			
				System.out.println("global_lastPart_filterConditions: "+ global_lastPart_filterConditions);
				//2015.03.31 Alopez. After one insert we show data keeping the filter
				ShowData showData = new ShowData(global_lastPart_filterConditions);
				//ShowData showData = new ShowData();
				showData.run();

				// selectRowByValue("PRIMARYKEY", cdsf.getPrimaryKey());
				selectRowByValue("ID", cdsf.getPrimaryKey());

			}

		}
	}

	private void updateTableRow(int rowIndex)
	{

		// String plz, ort;

		// String SQL = "SELECT PLZ, ORT FROM POSTLEITZAHLEN ";
		// SQL += "WHERE PRIMARYKEY = " + Tabelle.getValueAt(rowIndex,
		// 0).toString();

		String SQL = "SELECT * FROM  CD_SONGS ";
		SQL += "WHERE ID = " + Tabelle.getValueAt(rowIndex, 0).toString();

		// String typ_Music, Author, CD, Song, Country;

		ResultSet rSet = DBConnection.executeQuery(SQL);
		if (rSet == null)
			return;

		try
		{

			if (rSet.next())
			{
				// plz = rSet.getString("PLZ");
				// ort = rSet.getString("ORT");
				// rSet.close();

				// Tabelle.setValueAt(plz, rowIndex, 1);
				// Tabelle.setValueAt(ort, rowIndex, 2);

				Tabelle.setValueAt(rSet.getString(2), rowIndex, 1);
				// Cuidado con rowIndex que empieza en 0 y rSet empieza en 1
				Tabelle.setValueAt(rSet.getString(3), rowIndex, 2);
				Tabelle.setValueAt(rSet.getString(4), rowIndex, 3);
				Tabelle.setValueAt(rSet.getString(5), rowIndex, 4);
				Tabelle.setValueAt(rSet.getString(6), rowIndex, 5);

				rSet.close();
			}
		} catch (Exception ex)
		{
		}

	}

	private void selectRowByValue(String colName, Object value)
	{

		// int rowIndex = ((PLZTableModel)Tabelle.getModel()).findEntry(colName,
		// value);
		int rowIndex = ((MusikTableModel) Tabelle.getModel()).findEntry(
				colName, value);
		setSelectedRow(Tabelle, rowIndex);

	}

	private void deleteEntry(long PrimaryKey)
	{

		Object[] options =
		{ "Ja", "Nein" };

		// String SQL = "DELETE FROM POSTLEITZAHLEN";
		// SQL += " WHERE PRIMARYKEY = " + Long.toString(PrimaryKey);

		String SQL = "DELETE FROM cd_songs";
		SQL += " WHERE ID = " + Long.toString(PrimaryKey);

		int retValue = JOptionPane.showOptionDialog (this, "Datensatz löschen",
				"Löschen", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

		// Nein - nicht löschen
		if (retValue != JOptionPane.YES_OPTION)
			return;

		// Aktuelle zeile merken
		int selectedRow = Tabelle.getSelectedRow();

		DBConnection.executeNonQuery(SQL);

		//ShowData showData = new ShowData();
		// 2013.03.31 Alopez to keep the filter in case delete
		System.out.println("estoy en delete: " + global_lastPart_filterConditions);
		ShowData showData = new ShowData(global_lastPart_filterConditions);
		showData.run();

		// Auf vorherige Zeile positionieren
		// 2015.03.31 We let the function setSelectedRow to select the position before 
		// but with the filter does not make too much sense.
		System.out.println(selectedRow);
		setSelectedRow(Tabelle, --selectedRow);

	}


	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == miSchliessen)
			this.dispose();
		else if (e.getSource() == miAendern)
			detailFormat(Long.parseLong(Tabelle.getValueAt(
					Tabelle.getSelectedRow(), 0).toString()));
		else if (e.getSource() == miNeu)
			detailFormat(-1);
		else if (e.getSource() == miLoeschen)
			deleteEntry(Long.parseLong(Tabelle.getValueAt(Tabelle.getSelectedRow(), 0).toString()));

		else if (e.getSource() == btnFilter)
		{
			System.out.println("ich bin here btnFilter");
			if (isFilterParametersCorrect());
			resetLabelName();
		}

		else if (e.getSource() == mi1)
		{
			//System.out.println("mi1  estoy dentro del Menu Item mi");
			 detailFormat(Long.parseLong(Tabelle.getValueAt(Tabelle.getSelectedRow(),
			 0).toString()));
		}
		
		else if (e.getSource() == mi2)
		{
			System.out.println("mi2  estoy dentro del Menu Item mi");
			//showAllSongsfromCD();
			 
		}

		
		
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{

		statusBar.setText(String.format("Datensatz %s von %s", NumberFormat
				.getInstance().format(Tabelle.getSelectedRow() + 1),
				NumberFormat.getInstance().format(Tabelle.getRowCount())));

	}

	@Override
	public void keyPressed(KeyEvent e)
	{

		if (e.getKeyCode() == KeyEvent.VK_HOME)
			setSelectedRow(Tabelle, 0);
		else if (e.getKeyCode() == KeyEvent.VK_END)
			setSelectedRow(Tabelle, Tabelle.getRowCount() - 1);
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			e.consume();
			detailFormat(Long.parseLong(Tabelle.getValueAt(
					Tabelle.getSelectedRow(), 0).toString()));
		} else if (e.getKeyCode() == KeyEvent.VK_DELETE)
			deleteEntry(Long.parseLong(Tabelle.getValueAt(
					Tabelle.getSelectedRow(), 0).toString()));

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

		// Doppelklick erkennen
		if (e.getClickCount() == 2)
			detailFormat(Long.parseLong(Tabelle.getValueAt(
					Tabelle.getSelectedRow(), 0).toString()));
		
		else if(SwingUtilities.isRightMouseButton(e))
		{
			System.out.println("Tabelle.getX()");
			pm.show(e.getComponent(), e.getX(), e.getY());
			System.out.println(e.getX()+ " - " + e.getY());
			
			Point p = e.getPoint();
			
			try
			{
				int rowNumber = Tabelle.rowAtPoint( p);
				//int rowNumber = Tabelle.getX();
				System.out.println(rowNumber);

				//setSelectedRow(Tabelle, Tabelle.getRowCount() - 1);
				setSelectedRow(Tabelle, rowNumber);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,"Error Mouse right click " + JOptionPane.ERROR_MESSAGE);
			}
		
			
			
		}
			
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// 2015.03.27 Alopez efecto click boton derecho

/*		if(SwingUtilities.isRightMouseButton(e))
		{
			System.out.println("e.isPopupTrigger()");
			pm.show(e.getComponent(), e.getX(), e.getY());
		}*/

	}

	@Override
	public void mouseReleased(MouseEvent me)
	{
		

	}
	
	
	private class TableCellRenderer extends DefaultTableCellRenderer
	{

		private Color defaultColor = 	new Color(255, 255, 240);
		private Color alternateColor = 	new Color(245, 222, 179);
		private Color selectedColor = 	new Color(178, 34, 34);

		// 2015.03.31 Alopez I change the color of this Table
		// I dont like the changes i stay with the original ones
		//  private Color defaultColor = 	new Color(204,255,204);
		//	private Color alternateColor = 	new Color(0,204,102);
		//	private Color selectedColor = 	new Color(51,102,0);
		
		private Color backColor, foreColor;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column)
		{

			// Aufruf der Methode der Basisklasse
			// Hinter jeder Spalte in der Tabelle verbirgt sich ein Label
			JLabel itemLabel = (JLabel) super.getTableCellRendererComponent(
					table, value, isSelected, hasFocus, row, column);

			// Alternierende Hintergrundfarbe für jede 2. Zeile setzen bzw.
			// Selectionsfarbe
			if (!isSelected)
				backColor = (row % 2 == 0 ? alternateColor : defaultColor);
			else
				backColor = selectedColor;

			itemLabel.setBackground(backColor);

			if (isSelected)
				foreColor = Color.WHITE;
			else
				foreColor = Color.BLACK;

			itemLabel.setForeground(foreColor);

			return itemLabel;

		}
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		// TODO Auto-generated method stub
		
	}


}
