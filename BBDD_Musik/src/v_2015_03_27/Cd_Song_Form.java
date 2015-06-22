package v_2015_03_27;


import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Cd_Song_Form extends JDialog implements ActionListener, WindowListener, DocumentListener, FocusListener, KeyListener
{

	private JLabel	           jlabel1, jlabel2, jlabel3, jlabel4, jlabel5, jlabel6;
	//private JTextField       tfPLZ, tfOrt;
	private JTextField         tbID, tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name, tbCountry ;
	private JButton	           btnOK, btnAbbrechen;
	
	private long 			   mKey = -1;
	private boolean	           hasChanged;
	private Component 		   owner;
	
	private Color defaultColor 	 =  new Color(255,255,240);
	private Color alternateColor =  new Color(245,222,179);
	private Color selectedColor  =  new Color(178,34,34);
	private Color invalidBoton	 = 	new Color(233,150,122);
	
	private KeyboardFocusManager kbFocusManager;
	
	public Cd_Song_Form()
	{
		initializeComponents();
		
	}
	
	
	public Cd_Song_Form(long Key)
	{
		this();
		this.mKey = Key;
		
	}
	
	private void initializeComponents()
	{

		
		this.setTitle("Change/ New Song");
		
		// Layout Manager ausschalten
		this.setLayout(null);
		
		this.setSize(620, 160);
		this.getContentPane().setBackground(selectedColor);
		
		// Der WindowListener überwacht das Schließen des Dialogs
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		
		jlabel1 = new JLabel("ID");
		jlabel1.setBounds(20, 10, 80, 25);
		jlabel1.setForeground(Color.white);
		this.add(jlabel1);
		
		tbID = new JTextField();
		tbID.setBounds(10, 35, 80, 25);
		tbID.setBackground(invalidBoton);
		tbID.setForeground(Color.red);
		tbID.addFocusListener(this);
		tbID.addKeyListener(this);
		tbID.getDocument().addDocumentListener(this);
		this.add(tbID);
		tbID.setEditable(false);
		
		jlabel2 = new JLabel("Type Music");
		jlabel2.setBounds(110, 10, 90, 25);
		jlabel2.setForeground(Color.white);
		//jlabel2.setBackground(Color.red);
		this.add(jlabel2);
		
		tbTypMusic = new JTextField();
		tbTypMusic.setBounds(100, 35, 90, 25);
		tbTypMusic.setBackground(alternateColor);
		tbTypMusic.addFocusListener(this);
		tbTypMusic.addKeyListener(this);
		tbTypMusic.getDocument().addDocumentListener(this);
		this.add(tbTypMusic);
		
		jlabel3 = new JLabel("Autor");
		jlabel3.setForeground(Color.white);
		jlabel3.setBounds(210, 10, 90, 25);
		this.add(jlabel3);
		
		tbAuthor = new JTextField();
		tbAuthor.setBounds(200, 35, 90, 25);
		tbAuthor.setBackground(alternateColor);
		tbAuthor.addFocusListener(this);
		tbAuthor.addKeyListener(this);
		tbAuthor.getDocument().addDocumentListener(this);
		this.add(tbAuthor);
		
		
		jlabel4 = new JLabel("CD Name");
		jlabel4.setForeground(Color.white);
		jlabel4.setBounds(310, 10, 90, 25);
		this.add(jlabel4);
		
		tbCD_Name = new JTextField();
		tbCD_Name.setBounds(300, 35, 90, 25);
		tbCD_Name.setBackground(alternateColor);
		tbCD_Name.addFocusListener(this);
		tbCD_Name.addKeyListener(this);
		tbCD_Name.getDocument().addDocumentListener(this);
		this.add(tbCD_Name);
		
		jlabel5 = new JLabel("Song");
		jlabel5.setForeground(Color.white);
		jlabel5.setBounds(410, 10, 90, 25);
		this.add(jlabel5);
		
		tbSong_Name = new JTextField();
		tbSong_Name.setBounds(400, 35, 90, 25);
		tbSong_Name.setBackground(alternateColor);
		tbSong_Name.addFocusListener(this);
		tbSong_Name.addKeyListener(this);
		tbSong_Name.getDocument().addDocumentListener(this);
		this.add(tbSong_Name);
		
		jlabel6 = new JLabel("Country");
		jlabel6.setForeground(Color.white);
		jlabel6.setBounds(510, 10, 90, 25);
		this.add(jlabel6);
		
		tbCountry = new JTextField();
		tbCountry.setBounds(500, 35, 90, 25);
		tbCountry.setBackground(alternateColor);
		tbCountry.addFocusListener(this);
		tbCountry.addKeyListener(this);
		tbCountry.getDocument().addDocumentListener(this);
		this.add(tbCountry);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(100, 80, 120, 25);
		btnOK.addActionListener(this);
		this.add(btnOK);

		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.setBounds(240, 80, 120, 25);
		btnAbbrechen.addActionListener(this);
		this.add(btnAbbrechen);
		
	}
	
	
	private void initDialog()
	{
		
		this.setModal(true);
		this.setLocationRelativeTo(owner);
		kbFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		
		if (this.mKey > -1)
			readEntry(this.mKey);
		
		
	}
	
	public void showDialog()
	{
		initDialog();
		this.setBackground(alternateColor);
		this.setVisible(true);
	}
	
	public void showDialog(Component owner)
	{
		this.owner = owner;
		showDialog();
	}
	
	
	private void readEntry(long Key)
	{
		//String SQL = "SELECT ID, Typ_Music, Author FROM cd_songs ";
		String SQL = "SELECT * FROM cd_songs ";
		SQL += "WHERE ID = " + Long.toString(Key);
		
		ResultSet rSet = DBConnection.executeQuery(SQL);
		
		if (rSet == null) return;
		
		try
		{
			
			if (rSet.next())
			{
				
				// The index of one ResultSet starts in 1  NOT IN 0 (DO NOT FORGET)
				// System.out.println(rSet.getString("Typ_Music"));     // Si funciona
				// System.out.println(rSet.getString(1));      		    // Si funciona
				// System.out.println(rSet.getNString(1));  			// No funciona
				/* System.out.println(rSet.getString(2));     
				   System.out.println(rSet.getString(3));
				   System.out.println(rSet.getString(4));
				   System.out.println(rSet.getString(5));
				   System.out.println(rSet.getString(6)); */
				
				//tbID, tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name, tbCountry
				
				 tbID.setText(rSet.getString("ID"));   			 //funciona de ambas formas "ID" y con getInt(1).
				 //tbID.setText(toString(rSet.getInt(1)));
				 //tbID.setText(String.valueOf(rSet.getInt(0)));
				
				 tbTypMusic.setText(rSet.getString(2));
				 //tbTypMusic.setText(rSet.getString("Typ_Music"));   //funciona
				
				 tbAuthor.setText(rSet.getString(3));
				 tbCD_Name.setText(rSet.getString(4));
				 tbSong_Name.setText(rSet.getString(5));
				 tbCountry.setText(rSet.getString(6));
				
				rSet.close();
			}
			else
			{
				JOptionPane.showMessageDialog(this,  "Der Datensatz konnte nicht gefunden werden", "Datensatz lesen", JOptionPane.ERROR_MESSAGE);
				this.mKey = -1;
			}
			
			
		}
		catch (Exception ex) {}
		
		//hasChanged = true;
		
	}
	



	private boolean saveEntry()
	{
		
		boolean retValue = false;
		//PreparedStatement prepStatementPLZOrtExists;
		PreparedStatement prepStatementAuthor_CD_Song_Exists;
		
		if (!eingabeOK()) return retValue;
		
		//prepStatementPLZOrtExists = Globals.prepareIstPLZOrtVorhanden();
		prepStatementAuthor_CD_Song_Exists = Globals.prepareIstAuthorSongCdAvailable();
		
		//if (prepStatementPLZOrtExists == null)
		if (prepStatementAuthor_CD_Song_Exists == null)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Vorbereiten der SQL-Anweisung", "Datensatz speichern", JOptionPane.ERROR_MESSAGE);
			return retValue;
		}
		
		//if (Globals.istPLZOrtVorhandenPrepared(prepStatementPLZOrtExists, tfPLZ.getText(), tfOrt.getText()))
		if (Globals.ist_Author_CD_Song_VorhandenPrepared(prepStatementAuthor_CD_Song_Exists, tbTypMusic.getText(), tbAuthor.getText(), tbCD_Name.getText(), 
														tbSong_Name.getText(), tbCountry.getText()))
		{
		
			JOptionPane.showMessageDialog(this, "Ein value mit dieser Autor, CD, Song existiert schon", "Fehler", JOptionPane.ERROR_MESSAGE);
			
			return false;
		}
		
		
		if (this.mKey > -1)
		{ 
			retValue = updateEntry();
			System.out.println("llego aqui saveEntry");
		}
		else
			retValue = insertEntry();
		
		hasChanged = !retValue;
		
		return retValue;
		
	}
	
	
	private boolean eingabeOK()
	{
		
		boolean retValue = false;
		
		if (tbSong_Name.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(this, "Eingabe fehlt", "Fehler", JOptionPane.ERROR_MESSAGE);
			tbSong_Name.requestFocusInWindow();
		}
		
		/*else if (tfPLZ.getText().length() != 5)
		{
			JOptionPane.showMessageDialog(this, "Eingabe ungültig", "Fehler", JOptionPane.ERROR_MESSAGE);
			tfPLZ.requestFocusInWindow();
		}*/
		
		else if (tbAuthor.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(this, "Eingabe fehlt", "Fehler", JOptionPane.ERROR_MESSAGE);
			tbAuthor.requestFocusInWindow();
		}
		
		else if (tbCD_Name.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(this, "Eingabe fehlt", "Fehler", JOptionPane.ERROR_MESSAGE);
			tbCD_Name.requestFocusInWindow();
		}
		else
			retValue = true;
		
		
		return retValue;
		
	}
	
	
	private boolean updateEntry()
	{
		
		PreparedStatement prepStatementUpdate;
		
		//prepStatementUpdate = Globals.prepareUpdatePLZOrt();
		prepStatementUpdate = Globals.prepareUpdateAuthor_Cd_Song();
		if (prepStatementUpdate == null)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Vorbereiten der SQL-Anweisung", "Datensatz aktualisieren", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		//System.out.println("llego aqui bien");
		
		// tbID, tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name, tbCountry ;
		//return Globals.updateAuthor_Cd_SongPrepared(prepStatementUpdate, this.mKey, tfPLZ.getText(), tfOrt.getText());
		return Globals.updateAuthor_Cd_SongPrepared(prepStatementUpdate, this.mKey, tbTypMusic.getText(), tbAuthor.getText(), tbCD_Name.getText(),
													tbSong_Name.getText(), tbCountry.getText());
		
		
		
	}
	
	
	private boolean insertEntry()
	{
		
		PreparedStatement prepStatementInsert;
		
		//prepStatementInsert = Globals.prepareInsertPLZOrt();
		prepStatementInsert = Globals.prepareInsert_Author_CD_Song();
		//inser_Author_CD_Son_Prepared
		
		if (prepStatementInsert == null)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Vorbereiten der SQL-Anweisung insertEntry", "Datensatz hinzufügen", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		this.mKey = Globals.getNextKey();
		
		
		//return Globals.insertPLZOrtPrepared(prepStatementInsert, this.mKey, tfPLZ.getText(), tfOrt.getText());
		
		// tbID, tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name, tbCountry ;
		return Globals.inser_Author_CD_Song_Prepared(prepStatementInsert, this.mKey, tbTypMusic.getText(), tbAuthor.getText(), tbCD_Name.getText(), tbSong_Name.getText(), tbCountry.getText());
		
		
		
	}
	
	
	public long getPrimaryKey()
	{
		
		return this.mKey;
		
	}
	
	// Gibt 'true' zurück, wenn der Dialog geschlossen werden soll.
	private boolean queryExit()
	{
		
		// Benutzerdefinierten Button Text
		Object[] options = { "Ja", "Nein", "Abbrechen" };
				
		if (!hasChanged) return true;
		
		int retValue = JOptionPane.showOptionDialog(this, "Daten wurden geändert.\nÄnderungen speichern", 
				                                     "Frage", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, 
				                                     null, options, options[2]);
		
		// Nein - nicht speichern ?
		if (retValue == JOptionPane.NO_OPTION) return true;

		// Abbruch
		if (retValue != JOptionPane.YES_OPTION) return false;
		

		// Ja - Speichern
		return saveEntry();
				
	}
	
	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		// Überprüfen, ob Änderungen noch gespeichert werden müssen
		
		System.out.println("llego aqui primero??");
		if (queryExit())
			this.dispose();
		else
			tbTypMusic.requestFocusInWindow();
		
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
		
		if (e.getSource() == btnAbbrechen)
			windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		else if (e.getSource() == btnOK)
		{
			if (hasChanged && saveEntry()) 
				windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		
		
	}


	@Override
	public void focusGained(FocusEvent e)
	{
		// if (e.getSource() == tfPLZ) 
		// 		tfPLZ.selectAll();
		
		if (e.getSource() == tbTypMusic) 
			tbTypMusic.selectAll();
		
		
	}


	@Override
	public void focusLost(FocusEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void changedUpdate(DocumentEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void insertUpdate(DocumentEvent e)
	{
		//hasChanged = true;
		
	}


	@Override
	public void removeUpdate(DocumentEvent e)
	{
		//hasChanged = true;
		
	}


	@Override
	public void keyPressed(KeyEvent e)
	{
    	// if (e.getSource() == tfPLZ && e.getKeyCode() == KeyEvent.VK_ENTER && tfPLZ.getText().length() == 5)
		//	kbFocusManager.focusNextComponent();
		//	else if (e.getSource() == tfOrt && e.getKeyCode() == KeyEvent.VK_ENTER && tfOrt.getText().length() > 0) 
		//	kbFocusManager.focusNextComponent();
		
		if (e.getSource() == tbTypMusic && e.getKeyCode() == KeyEvent.VK_ENTER && tbTypMusic.getText().length() > 0) 
			kbFocusManager.focusNextComponent();
		else if (e.getSource() == tbAuthor && e.getKeyCode() == KeyEvent.VK_ENTER && tbAuthor.getText().length() > 0) 
			kbFocusManager.focusNextComponent();
		else if (e.getSource() == tbCD_Name && e.getKeyCode() == KeyEvent.VK_ENTER && tbCD_Name.getText().length() > 0) 
			kbFocusManager.focusNextComponent();
		else if (e.getSource() == tbSong_Name && e.getKeyCode() == KeyEvent.VK_ENTER && tbSong_Name.getText().length() > 0) 
			kbFocusManager.focusNextComponent();
		else if (e.getSource() == tbCountry && e.getKeyCode() == KeyEvent.VK_ENTER && tbCountry.getText().length() > 0) 
			kbFocusManager.focusNextComponent();
		
	}


	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e)
	{
		if (e.getSource() == tbID)
		{
			// Die Enter-Taste löst auch das keyTyped-Ereignis aus.
			// Da die Taste nicht durch die Überprüfung von isDigit()
			// den Rückgabewert 'false' zurückliefert wird vorher
			// die Methode isISOControl() verwendet, die alle Steuer- und 
			// Kontroll-Tasten erkennt. Dadurch wird der Signalton, der durch 
			// die Return-Taste ausgelöst werden würde, unterdrückt.
			if (Character.isISOControl(e.getKeyChar())) 
				return;
				
			if (!Character.isDigit(e.getKeyChar()))
			{
				Toolkit.getDefaultToolkit().beep();
				e.consume();
				return;
			}
			
			// Zuerst die markierten Zeichen löschen.
			((JTextField) e.getSource()).replaceSelection("");
				
			if (((JTextField) e.getSource()).getText().length() >= 5)
			{
				Toolkit.getDefaultToolkit().beep();
				e.consume();
			}
			
			
		}
		
		// tbID, tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name, tbCountry ;
		if (e.getSource() == tbTypMusic || e.getSource() == tbAuthor ||  e.getSource() == tbCD_Name || e.getSource() == tbSong_Name || e.getSource() == tbCountry)
		{
				hasChanged = true;
		}
	}
	
	
	

}
