package v_2015_03_31;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.text.NumberFormat;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Util.StatusBar;
import Util.WinUtil;

//Verwenden von mySQL als Datenbank:
//1. 	Herunterladen des mySQL-Datenbank-Treibers (Connector/J) von
//		http://www.mysql.de/downloads/connector/j/ 
//		Plattformunabhängiges ZIP-Archiv (mysql-connector-java-5.1.n.zip)  
//		Nach betätigen des Download-Buttons keine Eingabe von Zugangsdaten
//		sondern Auswahl von [» No thanks, just take me to the downloads!]
//2. 	Nach dem Herunterladen entpacken und das Verzeichnis nach C:\Temp
//	 	kopieren.
//3. 	Innerhalb des Eclipse Workspaces und dem Projekt (Javakurs) ein Verzeichnis 'lib' anlegen und
//		das Java-Archiv mysql-connector-java-5.1.n-bin.jar aus dem heruntergeladenen
//		Verzeichnis in dieses Verzeichnis kopieren.
//4. 	In Eclipse das Menü Project->Properties aufrufen. In den Properties 'Java Build Path'
//		auswählen und dort die Registerkarte 'Libraries'. Über den Button
//		[Add External JARs...] das Java-Archiv im Verzeichnis 'lib' hinzufügen.     
//		Alternativ:
//		Verzeichnis 'lib' im Package Explorer öffnen, mit rechter Maustaste auf den Connector
//		klicken und  anschliessend 'Build Path' -> Add to Build Path' auswählen. 

//Anlegen einer Datenbank/Tabelle mit XAMPP.
//1. 	Download vom XAMPP mit Installer -> http://www.apachefriends.org/de/xampp-windows.html
//2. 	Installation direkt in C:\XAMPP (sicherheitshalber wg. Zugriffsberechtigungen etc.)
//3. 	Starten des Programmes C:\xampp\xampp\xampp-control.exe.
//4. 	Starten des Apache-Servers.
//5. 	Starten der Datenbank. 
//6. 	Aufruf von XAMPP Für Windows über den Browser (Firefox) mit localhost.
//7. 	Auswahl von Tools/phpAdmin auf der linken Seite.  

public class Main_Datenbankzugriffe extends JFrame implements ActionListener, WindowListener
{

	private JMenuBar  	menuBar;
	private JMenu 		menuDatei, menuStammdaten, menuExtras;
	private JMenuItem	miDatenbankOeffnen, miDatenbankSchliessen, miBeenden, miPostleitzahlen, miPostleitzahlenImportieren, miMenuMusik;
	
	private StatusBar 	statusBar;
	private JProgressBar progressBar;
	
	
	private File 		fcFile;
	
	public Main_Datenbankzugriffe()
	{
		inializeComponents();
	}
	
	
	private void inializeComponents()
	{
		
		this.setTitle("Datenbankzugriffe");
		this.setSize(800,  480);
		
		// Das Schließen des Frames wird vom WindowListener überwacht.
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		
		// Standard Layout ist das BorderLayout
		
		menuBar = new JMenuBar();
		
		menuDatei = WinUtil.createMenu(menuBar, "Datei", null, 'D');
		miDatenbankOeffnen = WinUtil.createMenuItem(menuDatei, null, WinUtil.MenuItemType.ITEM_PLAIN, this, "Datenbank öffnen", null, 'ö', null);
		miDatenbankOeffnen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		miDatenbankSchliessen = WinUtil.createMenuItem(menuDatei, null, WinUtil.MenuItemType.ITEM_PLAIN, this, "Datenbank schliessen", null, 's', null);
		miDatenbankSchliessen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));	
		menuDatei.addSeparator();
				
		miBeenden = WinUtil.createMenuItem(menuDatei, null, WinUtil.MenuItemType.ITEM_PLAIN, this, "Beenden", null, 'B', "Programm beenden");
		
		menuStammdaten = WinUtil.createMenu(menuBar, "Data Bases", null, 'D');
		//miPostleitzahlen = WinUtil.createMenuItem(menuStammdaten, null, WinUtil.MenuItemType.ITEM_PLAIN, this, "Postleitzahlen", null, 'P', null);
		miMenuMusik = WinUtil.createMenuItem(menuStammdaten, null, WinUtil.MenuItemType.ITEM_PLAIN, this, "Musik", null, 'M', null);
		
		
		menuExtras = WinUtil.createMenu(menuBar, "Extras", null, 'x');
		//miPostleitzahlenImportieren = WinUtil.createMenuItem(menuExtras, null, WinUtil.MenuItemType.ITEM_PLAIN, this, "Postleitzahlen importieren...", null, 'i', null);
			
		this.setJMenuBar(menuBar);
		
		
		// Statusleiste
		statusBar = new StatusBar();
		statusBar.setPreferredSize(new Dimension(0, 30));
		this.add(statusBar, BorderLayout.PAGE_END);

		
		// Fortschrittsanzeige innerhalb der Statusleiste erzeugen
		progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
		progressBar.setBorderPainted(true);
		progressBar.setPreferredSize(new Dimension(200, 30));
		// Farbe der Fortschrittsanzeige
		progressBar.setForeground(Color.GREEN);
		// Prozentsatz anzeigen
		progressBar.setStringPainted(true);
		// Initial unsichtbar
		progressBar.setVisible(false);
		statusBar.add(progressBar, BorderLayout.LINE_END);
		
	}
	
	
	private void initFrame()
	{
		
		this.setLocationRelativeTo(null);
		openMySQLDatabase();
		
	}
	
	
	private void dbEnabled(boolean enabled)
	{
		
		miDatenbankOeffnen.setEnabled(!enabled);
		miDatenbankSchliessen.setEnabled(enabled);
		menuStammdaten.setEnabled(enabled);
		menuExtras.setEnabled(enabled);
		
		if (!enabled)
			statusBar.setMessage("Datenbank: (keine)");
		else
			statusBar.setMessage("Datenbank: " + DBConnection.getCatalog());
				
	}
	
	
	private void openMySQLDatabase()
	{
		
		String connectionString, classForName;
		
		String server = "localhost";
		//String dataBase = "alfatraining";
		String dataBase = "musik";
		
		classForName = "com.mysql.jdbc.Driver";
		
		connectionString = "jdbc:mysql://" + server + ":3306/";
		connectionString += dataBase;
		
		dbEnabled(DBConnection.connectToDatabase(classForName, connectionString, "root", null));
		
		
	}
	
	
	public void Show()
	{
		initFrame();
		this.setVisible(true);
	}
	
	
	private void openFileDialog()
	{
		
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(fcFile);
		fc.setFileFilter(new FileNameExtensionFilter("Texdateien (*.txt)", "txt"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("CSV-Dateien (*.csv)", "csv"));

		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
		
		// Speichern der zuletzt ausgewählten Datei
		fcFile = fc.getSelectedFile();
		
		readFile(fcFile.toString());
		
		
	}
	
	
	private void readFile(String Dateiname)
	{

		// Benutzerdefinierte Button Texte
		Object[] options = { "Ja", "Nein"};
				
		int retValue = JOptionPane.showOptionDialog(this, "Sollen die Einträge der vorhandenen Tabelle der Postleitzahlen vorher gelöscht werden", "Frage", 
								   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (retValue == JOptionPane.YES_OPTION) 
			deletePLZEntries();
		
		
		// Thread zum Einlesen der Postleitzahlen verwenden.
		// Nur so kann die Statusanzeige sinnvoll aktualisiert werden.
		
		Thread t = new Thread(new ReadFileIntoDatabase(Dateiname));
		t.start();
		
	}
	
	
	public static void main(String[] args)
	{
		Main_Datenbankzugriffe f = new Main_Datenbankzugriffe();
		f.Show();

	}

	private void deletePLZEntries()
	{
		
		//String SQL = "DELETE FROM POSTLEITZAHLEN";
		// oder
		String SQL = "TRUNCATE TABLE POSTLEITZAHLEN";
		DBConnection.executeNonQuery(SQL);
		
	}
	
	
	
	private int getRecordCount(String Dateiname)
	{
		int retValue = 0;
		
		Scanner scanner = null;
		
		try
		{
			
			scanner = new Scanner(new FileInputStream(Dateiname));
			while (scanner.hasNextLine())
			{
				scanner.nextLine();
				retValue++;
			}
			
		}
		catch (Exception ex) {}
		
		
		if (scanner != null) scanner.close();	
		
		return retValue;
	}
	
	
	/*private void showPLZTable()
	{
		//PLZTable dlg = new PLZTable();
		// dlg.showDialog(this);
		CD_Songs_Table  cst = new CD_Songs_Table();
		cst.showDialog(this);
	}*/
	
	private void showCD_Table()
	{
		CD_Table cds = new CD_Table();
		cds.showDialog(this);
	}
	
	private class ReadFileIntoDatabase implements Runnable
	{

		private Scanner scanner = null;
		private String zeile = null;
		private int readCounter = 0;
		private int insertCounter = 0;
		private String tempString;
		
		private String Dateiname;
		private String[] split;
		
		private long lngKey;
		
		private PreparedStatement prepStatementInsert, prepStatementPLZORTExists;
		
		public ReadFileIntoDatabase(String Dateiname)
		{
			
			this.Dateiname = Dateiname;
		}
		
		
		
		@Override
		public void run()
		{
			
			
			//prepStatementInsert = Globals.prepareInsertPLZOrt();
			if (prepStatementInsert == null)
			{
				JOptionPane.showMessageDialog(null, "Fehler beim Vorbereiten der SQL-Anweisung", "Postleitzahlen importieren", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
//			prepStatementPLZORTExists = Globals.prepareIstPLZOrtVorhanden();
//			if (prepStatementPLZORTExists == null)
//			{
//				JOptionPane.showMessageDialog(null, "Fehler beim Vorbereiten der SQL-Anweisung", "Postleitzahlen importieren", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
			
			
			// Verhindern dass während des Imports Benutzermenü-Funktionen
			// aufgerufen werden können.
			for (int i = 0; i < menuBar.getMenuCount(); i++)
				menuBar.getMenu(i).setEnabled(false);
			

			// Inhalt der Statusanzeige sichern
			tempString = statusBar.getText();
			
			// Fortschrittsanzeige vorbereiten und sichtbar machen			
			long fileLength = new File(Dateiname).length();
			if (fileLength <= Integer.MAX_VALUE)
				progressBar.setMaximum((int)fileLength);
			else 
			{
				progressBar.setMaximum(getRecordCount(Dateiname));
				fileLength = -1;
			}
			
			progressBar.setMinimum(0);
			progressBar.setValue(0);
			progressBar.setVisible(true);
			
			lngKey = Globals.getNextKey();
			
			try
			{
				
				scanner = new Scanner(new FileInputStream(Dateiname));
				
				while(scanner.hasNext())
				{
					
					// Lesen des nächsten Datensatzes und evtl. vorkommende Hochkommas durch doppelte Hochkommas ersetzen.
					// Nicht für ein PreparedStatement.
					//zeile = scanner.nextLine().replaceAll("'", "''");
					
					zeile = scanner.nextLine();
					
					readCounter++;
					
					if (fileLength < 0)
						progressBar.setValue(readCounter);
					else
						// Aktueller Wert + Länge des gelesenen Datensatzes + Zeilenschalten (CR/LF)
						progressBar.setValue(progressBar.getValue() + zeile.length() + System.lineSeparator().length());
								
					
					if (readCounter % 10 == 0)
						statusBar.setMessage(String.format("Datensätze werden gelesen ...    [%s]", NumberFormat.getInstance().format(readCounter)));
					
					// Methode 'split' mit maximaler Anzahl zurückzuliefernder Zeichenketten 
					split = zeile.split(";", 2);
					if (split.length == 2)
					{
						
						//if (Globals.istPLZOrtVorhanden(split[0], split[1])) continue;
						if (Globals.istPLZOrtVorhandenPrepared(prepStatementPLZORTExists, split[0], split[1])) continue;
												
						
						//if (Globals.insertPLZOrt(lngKey, split[0], split[1]))
//						if (Globals.insertPLZOrtPrepared(prepStatementInsert, lngKey, split[0], split[1]))
//						{
//							lngKey++;
//							insertCounter++;
//						}
					}
					
				}
				
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(Main_Datenbankzugriffe.this, "Fehler beim Einlesen der Datei " + Dateiname + ": " + ex.getMessage(), "E/A Fehler", JOptionPane.ERROR_MESSAGE);
			}
			
			if (scanner != null) scanner.close();
			
			// Inhalt der Statusanzeige wiederherstellen
			statusBar.setText(tempString);
			
			// Fortschrittsanzeige wieder unsichtbar machen
			progressBar.setVisible(false);
			
			// Benutzermenü wieder aktivieren
			for (int i = 0; i < menuBar.getMenuCount(); i++)
				menuBar.getMenu(i).setEnabled(true);
			
			dbEnabled(true);
			

			JOptionPane.showMessageDialog(Main_Datenbankzugriffe.this, String.format("Es wurden %s Datensätze eingelesen.\nEs wurden %s Datensätze erfolgreich importiert.", 
								NumberFormat.getInstance().format(readCounter), NumberFormat.getInstance().format(insertCounter)), 
								"Importieren Postleitzahlen", JOptionPane.INFORMATION_MESSAGE);
						
		}
		
	}
	
	
	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		
		DBConnection.closeConnection();
		
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		
		if (menuDatei.isEnabled())
			this.dispose();
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == miBeenden)
			windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		else if (e.getSource() == miDatenbankOeffnen)
			openMySQLDatabase();
		else if (e.getSource() == miDatenbankSchliessen)
		{
			DBConnection.closeConnection();
			dbEnabled(false);
		}
		else if (e.getSource() == miPostleitzahlenImportieren)
			openFileDialog();
		//else if (e.getSource() == miPostleitzahlen)
			//showPLZTable();
		
		else if (e.getSource() == miMenuMusik)
			showCD_Table();
		
	
	}

}
