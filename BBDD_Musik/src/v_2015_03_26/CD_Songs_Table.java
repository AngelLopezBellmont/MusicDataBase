package v_2015_03_26;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class CD_Songs_Table extends JDialog implements ActionListener,
		ListSelectionListener, KeyListener, MouseListener
{

	private JMenuBar menuBar;
	private JMenu menuDatei, menuBearbeiten;
	private JMenuItem miNeu, miAendern, miLoeschen, miSchliessen;
	private JTable Tabelle;
	private JScrollPane jspTabelle;

	private JLabel jlabel0, jlabel1, jlabel2, jlabel3, jlabel4, jlabel5;
	private JTextField tbID, tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name,
			tbCountry;
	private JButton btnFilter;
	private Integer distBetweenBox, distLeftBorder;

	private StatusBar statusBar;
	private Component owner;

	public CD_Songs_Table()
	{
		initializeComponents();
	}

	private void initializeComponents()
	{

		this.setTitle("CD_Songs");
		this.setSize(985, 585);
		this.setLayout(null);
		this.setBackground(Color.red);
		this.setResizable(false);

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
		Tabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo 1
																		// selecion
		Tabelle.getSelectionModel().addListSelectionListener(this);
		Tabelle.addKeyListener(this);
		Tabelle.addMouseListener(this);

		// Zum Blättern der Tabelle
		jspTabelle = new JScrollPane(Tabelle);
		jspTabelle.setBounds(0, 100, 980, 400);
		// jspTabelle.setSize(this.getWidth()-20, 300);
		// this.add(jspTabelle, BorderLayout.CENTER);
		this.add(jspTabelle);

		// TextBox here:

		distBetweenBox = 15;
		distLeftBorder = 60;

		jlabel0 = new JLabel("Filters");
		
		//Font labelFont = jlabel0.getFont();
		
		jlabel0.setFont(new Font(jlabel0.getFont().getFontName(), Font.BOLD, 18));
		jlabel0.setBounds(437, 1, 100, 25);
		//jlabel0.setSize(100, 25);
		jlabel0.setHorizontalAlignment(JLabel.CENTER);
	
		//this.add(Box.createHorizontalGlue());
		this.add(jlabel0);
		//this.add(Box.createHorizontalGlue());
		
		
		
		
		
		jlabel1 = new JLabel("Typ_Music");
		jlabel1.setBounds(distLeftBorder, 25, 100, 25);
		this.add(jlabel1);

		tbTypMusic = new JTextField();
		tbTypMusic.setBounds(distLeftBorder, 50, 100, 25);
		// tbTypMusic.setBackground(Color.green);
		// this.add(tbTypMusic, BorderLayout.PAGE_START);
		this.add(tbTypMusic);

		jlabel2 = new JLabel("Author");
		jlabel2.setBounds(distLeftBorder + jlabel1.getWidth()
				+ (distBetweenBox) * 1, 25, 150, 25);
		this.add(jlabel2);

		tbAuthor = new JTextField();
		tbAuthor.setBounds(distLeftBorder + jlabel1.getWidth()
				+ (distBetweenBox) * 1, 50, 150, 25);
		// tbAuthor.setBackground(Color.green);
		this.add(tbAuthor);

		jlabel3 = new JLabel("CD_Name");
		jlabel3.setBounds(
				distLeftBorder + jlabel1.getWidth() + jlabel2.getWidth()
						+ (distBetweenBox) * 2, 25, 150, 25);
		this.add(jlabel3);

		tbCD_Name = new JTextField();
		tbCD_Name.setBounds(
				distLeftBorder + jlabel1.getWidth() + jlabel2.getWidth()
						+ (distBetweenBox) * 2, 50, 150, 25);
		// tbCD_Name.setBackground(Color.green);
		this.add(tbCD_Name);

		
		
		jlabel4 = new JLabel("Song_Name");
		jlabel4.setBounds(
		distLeftBorder + jlabel1.getWidth() + jlabel2.getWidth()
						+ jlabel3.getWidth() + (distBetweenBox) * 3, 
						25, 150,25);
		
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
		statusBar.setBounds(0, 500, this.getWidth(), 30);
		// statusBar.setPreferredSize(new Dimension(200, 30));
		// this.add(statusBar, BorderLayout.PAGE_END);
		this.add(statusBar);
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

		Thread t = new Thread(new ShowData());
		t.start();

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

	public void goto_Filter( String filterConditions)
	{
		// 2015.03.26 We send the part of query filterConditions to ShowData
		ShowData showData = new ShowData(filterConditions);
		showData.run();
	}
	
	public boolean isFilterParametersCorrect()
	{
		// 2015.03.25 We get the filter information and then we send it to -->
		// ShowData --> MusikTableModel() and we paint it.
		// My texbox names: tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name,
		// tbCountry ;
		// If the query to filter is ok return true if not return false
		Boolean valueReturn = false;
		String filterConditions;
		String queryTest;

		String sqlPiece_TypMusic = "";
		String sqlPiece_Author = "";
		String sqlPiece_CD_Name = "";
		String sqlPiece_Song_Name = "";
		String sqlPiece_Country = "";

		if (!tbTypMusic.getText().isEmpty())
			sqlPiece_TypMusic = " Typ_Music = "
					+ insertQuaotes(tbTypMusic.getText()) + " AND ";

		if (!tbAuthor.getText().isEmpty())
			sqlPiece_Author = " Author = " + insertQuaotes(tbAuthor.getText())
					+ " AND ";

		if (!tbCD_Name.getText().isEmpty())
			sqlPiece_CD_Name = " CD_Name = "
					+ insertQuaotes(tbCD_Name.getText()) + " AND ";

		if (!tbSong_Name.getText().isEmpty())
			sqlPiece_Song_Name = " Song_Name = "
					+ insertQuaotes(tbSong_Name.getText()) + " AND ";

		if (!tbCountry.getText().isEmpty())
			sqlPiece_Country = " Country = "
					+ insertQuaotes(tbCountry.getText()) + " AND";

		System.out.println();

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

		// We check id the query is gültig or not

		queryTest = "Select count(*) from cd_songs " + filterConditions;

		if (Globals.isValidQuery(queryTest))
		{
			valueReturn = true;
			goto_Filter( filterConditions);
		}
		
		else
		// we send a message teelling taht the query is not valid
			
			JOptionPane.showMessageDialog(this, "<html> SORRY.<br> The filter parameters does not give back any result."
					+ "<br> Please chose other parameters to filter.</html>");
			
		//<html>I'm new to java.<br>I have no background in programming.<br>I could use some help Thanks!</html>"
		
			
			

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
				Tabelle.setModel(new MusikTableModel());
				// System.out.println("filterCondition.equals(null)");
			} else
			{
				Tabelle.setModel(new MusikTableModel(filterCondition));
				// System.out.println("else");
			}

			// Überschriften der Tabelle grösser und fett
			Font font = Tabelle.getTableHeader().getFont()
					.deriveFont(Font.BOLD, 14.0f);
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
		Cd_Song_Form cdsf = new Cd_Song_Form(PrimaryKey);
		cdsf.showDialog(this);

		if (PrimaryKey > -1)
		{
			// Änderungmodus
			updateTableRow(Tabelle.getSelectedRow());
			// Here we make the refresh from the table, after we change
			// something
			System.out.println("(PrimaryKey > -1");

		} else
		{
			// Neuanlage Aber nur, wenn ein Datensatz hinzugefügt wurde
			// (dann ist der Primärschklüssel > - 1).

			if (cdsf.getPrimaryKey() > -1)
			// 2015.03.25 09:20 tengo que mirar para hace esto aqui creo que es
			// para refrescar
			// en caso de new insert recoje la id de Cd_Song_Form con
			// cdsf.getPrimaryKey(). Ahh ya es para posicionarnos en
			// la linea de la nueva entrada con selectRowByValue
			{
				// Aufruf von ShowData() ohne Thread weil hier
				// gewartet werden muss, bis die Daten vollständig
				// gelesen wurden, um anschliessend auf die
				// Zeile positionieren bzw. einen bestimmten Eintrag
				// finden zu können.
				ShowData showData = new ShowData();
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

		int retValue = JOptionPane.showOptionDialog(this, "Datensatz löschen",
				"Löschen", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

		// Nein - nicht löschen
		if (retValue != JOptionPane.YES_OPTION)
			return;

		// Aktuelle zeile merken
		int selectedRow = Tabelle.getSelectedRow();

		DBConnection.executeNonQuery(SQL);

		ShowData showData = new ShowData();
		showData.run();

		// Auf vorherige Zeile positionieren
		setSelectedRow(Tabelle, --selectedRow);

	}

	/*
	 * private class PLZTableModel extends AbstractTableModel {
	 * 
	 * 
	 * private String SQL =
	 * "SELECT PRIMARYKEY, PLZ as Postleitzahl, ORT as Wohnort FROM POSTLEITZAHLEN ORDER BY PLZ, ORT"
	 * ;
	 * 
	 * private int anzahlSpalten, anzahlZeilen; private ArrayList<String>
	 * ColumnNames; private Object[][] data;
	 * 
	 * 
	 * public PLZTableModel() {
	 * 
	 * // Ausführen der SQL-Anweisung zum Lesen aller Datensätze ResultSet rSet
	 * = DBConnection.executeQuery(SQL);
	 * 
	 * // Lesen der Metadaten: Anzahl, Datentypen und Eigenschaften der //
	 * Spalten aus dem ResultSet. ResultSetMetaData rsMetaData =
	 * getMetaData(rSet);
	 * 
	 * // Anzahl der Spalten aus den Metadaten ermitteln anzahlSpalten =
	 * getColumnCount(rsMetaData);
	 * 
	 * // Anzahl der Zeilen aus dem ResultSet ermitteln anzahlZeilen =
	 * getRowCount(rSet);
	 * 
	 * // Überschriften der Spalten aus den Metadaten erstellen
	 * setHeader(rsMetaData);
	 * 
	 * // Liest alle datensätze aus dem ResultSet in das zweidimensionale //
	 * Objekt-Array 'data'. getData(rSet);
	 * 
	 * 
	 * }
	 * 
	 * private ResultSetMetaData getMetaData(ResultSet rSet) {
	 * 
	 * ResultSetMetaData rsMD = null;
	 * 
	 * try { rsMD = rSet.getMetaData(); } catch (Exception ex) {
	 * JOptionPane.showMessageDialog(null, "getMetaData: " + ex.getMessage(),
	 * "Fehler", JOptionPane.ERROR_MESSAGE); }
	 * 
	 * return rsMD;
	 * 
	 * }
	 * 
	 * 
	 * private int getColumnCount(ResultSetMetaData rsMD) { int retValue = 0;
	 * 
	 * try { retValue = rsMD.getColumnCount(); } catch (Exception ex) {
	 * JOptionPane.showMessageDialog(null, "getColumnCount: " + ex.getMessage(),
	 * "Fehler", JOptionPane.ERROR_MESSAGE); }
	 * 
	 * return retValue; }
	 * 
	 * private int getRowCount(ResultSet rSet) { int retValue = 0;
	 * 
	 * try { rSet.last(); retValue = rSet.getRow(); rSet.beforeFirst(); } catch
	 * (Exception ex) { JOptionPane.showMessageDialog(null, "getRowCount: " +
	 * ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE); }
	 * 
	 * return retValue; }
	 * 
	 * 
	 * private void setHeader(ResultSetMetaData rsMD) {
	 * 
	 * ColumnNames = new ArrayList<>();
	 * 
	 * for (int i = 1; i <= anzahlSpalten; i++)
	 * ColumnNames.add(getColumnLabel(rsMD, i));
	 * 
	 * 
	 * }
	 * 
	 * // Spaltenname der SELECT-Anweisung zurückliefern private String
	 * getColumnName(ResultSetMetaData rsMD, int colIndex) {
	 * 
	 * String colName = "";
	 * 
	 * try { colName = rsMD.getColumnName(colIndex); } catch (Exception ex) {
	 * JOptionPane.showMessageDialog(null, "getColumnName: " + ex.getMessage(),
	 * "Fehler", JOptionPane.ERROR_MESSAGE); }
	 * 
	 * 
	 * return colName;
	 * 
	 * }
	 * 
	 * // Den Alias-Namen der SELECT-Anweisung zurückoiefern private String
	 * getColumnLabel(ResultSetMetaData rsMD, int colIndex) {
	 * 
	 * String colName = "";
	 * 
	 * try { colName = rsMD.getColumnLabel(colIndex); } catch (Exception ex) {
	 * JOptionPane.showMessageDialog(null, "getColumnLabel: " + ex.getMessage(),
	 * "Fehler", JOptionPane.ERROR_MESSAGE); }
	 * 
	 * 
	 * return colName;
	 * 
	 * }
	 * 
	 * private void getData(ResultSet rSet) {
	 * 
	 * data = new Object[anzahlZeilen][anzahlSpalten];
	 * 
	 * try {
	 * 
	 * for (int zeile = 1; zeile <= anzahlZeilen; zeile++) { rSet.next(); for
	 * (int spalte = 1; spalte <= anzahlSpalten; spalte++) data[zeile -
	 * 1][spalte - 1] = rSet.getObject(spalte); }
	 * 
	 * } catch (Exception ex) { JOptionPane.showMessageDialog(null, "getData: "
	 * + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE); }
	 * 
	 * }
	 * 
	 * 
	 * public int findEntry(String colName, Object value) {
	 * 
	 * int retValue = -1;
	 * 
	 * int colIndex = getColumnIndex(colName); if (colIndex == -1) return
	 * retValue;
	 * 
	 * 
	 * for (int zeile = 0; zeile < anzahlZeilen; zeile++) { if
	 * (data[zeile][colIndex].toString().equals(value.toString())) { retValue =
	 * zeile; break; } }
	 * 
	 * return retValue; }
	 * 
	 * 
	 * public int getColumnIndex(String colName) {
	 * 
	 * int retValue = -1; int i = 0;
	 * 
	 * for (String s : ColumnNames) { if (colName.equalsIgnoreCase(s)) {
	 * retValue = i; break; }
	 * 
	 * i++; }
	 * 
	 * return retValue;
	 * 
	 * }
	 * 
	 * 
	 * @Override public String getColumnName(int columnIndex) { return
	 * ColumnNames.get(columnIndex); }
	 * 
	 * @Override public int getColumnCount() { return anzahlSpalten; }
	 * 
	 * @Override public int getRowCount() { return anzahlZeilen; }
	 * 
	 * @Override public Object getValueAt(int rowIndex, int colIndex) { return
	 * data[rowIndex][colIndex]; }
	 * 
	 * @Override public void setValueAt(Object value, int rowIndex, int
	 * columnIndex) {
	 * 
	 * data[rowIndex][columnIndex] = value; fireTableCellUpdated(rowIndex,
	 * columnIndex); }
	 * 
	 * }
	 */

	// Damit die Darstellung der JTable angepasst werden kann, muss ein
	// TableCellRenderer eingesetzt werden.
	// Die Klasse DefaultTableCellRenderer implementiert die Schnittstelle
	// TableCellRenderer die nur die Methode getTableCellRendererComponent(..)
	// vorschreibt.
	// Diese Methode muss überschrieben werden, um die gewünschten Anpassungen
	// vorzunehmen.
	private class TableCellRenderer extends DefaultTableCellRenderer
	{
		//private Color defaultColor = Color.WHITE;
		//private Color alternateColor = new Color(217, 230, 253);
		//private Color selectedColor = new Color(67, 134, 224);
		
		private Color defaultColor 	 =  new Color(255,255,240);
		private Color alternateColor =  new Color(245,222,179);
		private Color selectedColor  =  new Color(178,34,34);
		
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
			deleteEntry(Long.parseLong(Tabelle.getValueAt(
					Tabelle.getSelectedRow(), 0).toString()));

		else if (e.getSource() == btnFilter)
			// System.out.println("ich bin here");
			if(isFilterParametersCorrect());

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
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
