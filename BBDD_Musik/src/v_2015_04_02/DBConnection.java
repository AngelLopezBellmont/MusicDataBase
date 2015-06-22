package v_2015_04_02;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DBConnection
{
	
	private static Connection dbConn = null;
	private static String connectionString = null;
		
	
	public static boolean connectToDatabase(String classForName, String connectionString, String userID, String passWord)
	{
		boolean retValue = false;
		
		try
		{
			// Erstellen und Registrieren der als Zeichenkette 'classForName'
			// übergebenen Klasse für den DriverManager.
			// (statische Initialisierung).
			Class.forName(classForName).newInstance();
			
			DBConnection.dbConn = DriverManager.getConnection(connectionString, userID, passWord);
			DBConnection.connectionString = connectionString;
			
			retValue = true;

			
		}
		catch (Exception ex)
		{
			DBConnection.dbConn = null;
			DBConnection.connectionString = "";
			JOptionPane.showMessageDialog(null, "Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		
		return retValue;
	}
	
	
	public static void closeConnection()
	{
		
		if (DBConnection.dbConn == null)
			return;
		
		try
		{
			DBConnection.dbConn.close();
		}
		catch (Exception ex) {}
		
		DBConnection.dbConn = null;
		DBConnection.connectionString = "";
		
	}
	
	public static Connection getConnection()
	{
		return DBConnection.dbConn;
	}
	

	public static String getConectionString()
	{
		return DBConnection.connectionString;
	}
	
	
	public static String getCatalog()
	{
		
		String retValue = "";
		
		if (DBConnection.dbConn == null)
			return retValue;
		
		
		try
		{
			retValue = DBConnection.dbConn.getCatalog();
		}
		catch (Exception ex) {}
		
		return retValue;
		
	}
	
	
	public static int executeNonQuery(String SQL)
	{
		Statement stmt;
		int retValue = 0;
		
		if (DBConnection.dbConn == null)
			return retValue;
		
		try
		{
			stmt = DBConnection.dbConn.createStatement();
			retValue = stmt.executeUpdate(SQL);
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		return retValue;
	}
	
	
	public static Object executeScalar(String SQL)
	{
		
		Statement stmt;
		Object retValue = null;
		
		if (DBConnection.dbConn == null)
			return retValue;
		
		
		try
		{
			stmt = DBConnection.dbConn.createStatement();
			ResultSet rSet = stmt.executeQuery(SQL);
			rSet.next();
			retValue = rSet.getObject(1);
			rSet.close();
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		
		return retValue;
		
	}
	
	public static ResultSet executeQuery(String SQL)
	{
		
		Statement stmt;
		ResultSet rSet = null;
		
		if (DBConnection.dbConn == null)
			return rSet;
		
		try
		{
			stmt = DBConnection.dbConn.createStatement();
			rSet = stmt.executeQuery(SQL);
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Fehler beim Zugriff auf die Datenbank: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		return rSet;
		
	}
	
	
	
	
	// Vorbereiten einer beliebigen SQL-Anweisung, die anschliessend
	// immer wieder verwendet werden kann.
	public static PreparedStatement prepareStatement(String SQL)
	{
		
		PreparedStatement prepStatement = null;
		
		try
		{
			prepStatement = DBConnection.dbConn.prepareStatement(SQL);
		}
		catch (Exception ex) {}
		
		return prepStatement;
		
	}
	
	
	
}
