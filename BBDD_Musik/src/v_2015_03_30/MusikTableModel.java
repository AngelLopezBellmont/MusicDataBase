package v_2015_03_30;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;


public class MusikTableModel extends AbstractTableModel
{

	//private String SQL = "SELECT PRIMARYKEY, PLZ as Postleitzahl, ORT as Wohnort FROM POSTLEITZAHLEN ORDER BY PLZ, ORT";
	//private String SQL = "SELECT ID, Typ_Music, Author, CD_Name, Song_Name, Country FROM CD_Songs ";
	
	private String SQL = "";
	
	private int anzahlSpalten, anzahlZeilen;
	private ArrayList <String> ColumnNames;
	private Object[][]		  data;
	
	
	public MusikTableModel()
	{
		SQL += "ORDER BY ID";
		// Ausführen der SQL-Anweisung zum Lesen aller Datensätze
		ResultSet rSet = DBConnection.executeQuery(SQL);
		
		// Lesen der Metadaten: Anzahl, Datentypen und Eigenschaften der
		// Spalten aus dem ResultSet.
		ResultSetMetaData rsMetaData = getMetaData(rSet);
		
		// Anzahl der Spalten aus den Metadaten ermitteln
		anzahlSpalten = getColumnCount(rsMetaData);
		
		// Anzahl der Zeilen aus dem ResultSet ermitteln
		anzahlZeilen = getRowCount(rSet);
		
		// Überschriften der Spalten aus den Metadaten erstellen
		setHeader(rsMetaData);
		
		// Liest alle datensätze aus dem ResultSet in das zweidimensionale
		// Objekt-Array 'data'.
		getData(rSet);
		

	}
	
	
	public MusikTableModel(String Filter)
	{
		
		//SQL += " " + Filter + "ORDER BY ID";
		// Ausführen der SQL-Anweisung zum Lesen aller Datensätze
		
		SQL = Filter;
		System.out.println("SQL:" + SQL);
		ResultSet rSet = DBConnection.executeQuery(SQL);
		
		ResultSetMetaData rsMetaData = getMetaData(rSet);
		
		anzahlSpalten = getColumnCount(rsMetaData);
		anzahlZeilen = getRowCount(rSet);
		
		// Überschriften der Spalten aus den Metadaten erstellen
		setHeader(rsMetaData);
		
		// Liest alle datensätze aus dem ResultSet in das zweidimensionale
		// Objekt-Array 'data'.
		getData(rSet);
		

	}
	private ResultSetMetaData getMetaData(ResultSet rSet)
	{
		
		ResultSetMetaData rsMD = null;
		
		try
		{
			rsMD = rSet.getMetaData();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "getMetaData: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		return rsMD;
		
	}
	
	
	private int getColumnCount(ResultSetMetaData rsMD)
	{
		int retValue = 0;
		
		try
		{
			retValue = rsMD.getColumnCount();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "getColumnCount: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		return retValue;
	}
	
	private int getRowCount(ResultSet rSet)
	{
		int retValue = 0;
		
		try
		{
			rSet.last();
			retValue = rSet.getRow();
			rSet.beforeFirst();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "getRowCount: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
					
		return retValue;
	}
	
	
	private void setHeader(ResultSetMetaData rsMD)
	{
		
		ColumnNames = new ArrayList<>();
		
		for (int i = 1; i <= anzahlSpalten; i++)
			ColumnNames.add(getColumnLabel(rsMD, i));
		
		
	}
	
	// Spaltenname der SELECT-Anweisung zurückliefern
	private String getColumnName(ResultSetMetaData rsMD, int colIndex)
	{
		
		String colName = "";
		
		try
		{
			colName = rsMD.getColumnName(colIndex);
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "getColumnName: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		
		return colName;
			
	}
	
	// Den Alias-Namen der SELECT-Anweisung zurückoiefern
	private String getColumnLabel(ResultSetMetaData rsMD, int colIndex)
	{
		
		String colName = "";
		
		try
		{
			colName = rsMD.getColumnLabel(colIndex);
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "getColumnLabel: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		
		return colName;
			
	}
	
	private void getData(ResultSet rSet)
	{
		
		data = new Object[anzahlZeilen][anzahlSpalten];
		
		try
		{
			
			for (int zeile = 1; zeile <= anzahlZeilen; zeile++)
			{
				rSet.next();
				for (int spalte = 1; spalte <= anzahlSpalten; spalte++)
					data[zeile - 1][spalte - 1] = rSet.getObject(spalte);
			}

		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "getData: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	public int findEntry(String colName, Object value)
	{
		
		int retValue = -1;
		
		int colIndex = getColumnIndex(colName);
		if (colIndex == -1)
			return retValue;
		
		
		for (int zeile = 0; zeile < anzahlZeilen; zeile++)
		{
			if (data[zeile][colIndex].toString().equals(value.toString()))
			{
				retValue = zeile;
				break;
			}
		}
		
		return retValue;
	}
	
	
	public int getColumnIndex(String colName)
	{
		
		int retValue = -1;
		int i = 0;
		
		for (String s : ColumnNames)
		{
			if (colName.equalsIgnoreCase(s))
			{
				retValue = i;
				break;
			}
			
			i++;
		}
		
		return retValue;
		
	}
	
	
	@Override
	public String getColumnName(int columnIndex)
	{
		return ColumnNames.get(columnIndex);
	}

	@Override
	public int getColumnCount()
	{
		return anzahlSpalten;
	}

	@Override
	public int getRowCount()
	{
		return anzahlZeilen;
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex)
	{
		return data[rowIndex][colIndex];
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		
		data[rowIndex][columnIndex] = value;
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	

}
