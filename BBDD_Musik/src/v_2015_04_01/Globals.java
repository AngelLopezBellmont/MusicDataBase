package v_2015_04_01;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class Globals
{

	// Privater Standardkonstruktor.
	// Alle Methoden dieser Klasse sind statisch. Durch die Deklaration eines
	// eigenen Standardkonstruktors wird verhindert, dass Java einen Standardkonstruktor
	// erstellt.
	// Die Änderung des Zugriffsmodifizierers von 'public' in 'private'
	// verhindert, dass eine Instanz dieser Klasse erstellt werden kann.

	private Globals()
	{
	}
	
	
	public static String quote(String value)
	{
		return "'" + value + "'";
	}
	
	
	public static boolean insertPLZOrt(long PrimaryKey, String PLZ, String Ort)
	{
		
		String SQL = "INSERT INTO POSTLEITZAHLEN ";
		SQL += "(PRIMARYKEY, PLZ, ORT) ";
		SQL += "VALUES(";
		SQL += Long.toString(PrimaryKey) + ", ";
		SQL += quote(PLZ) + ", ";
		SQL += quote(Ort) + ")";
		
		return DBConnection.executeNonQuery(SQL) > 0;
				
	}
	
	public static long getNextKey()
	{
		
		long retValue = 0;
		
		//String SQL = "SELECT MAX(PRIMARYKEY) FROM POSTLEITZAHLEN";
		String SQL = "SELECT MAX(ID) FROM cd_songs";
		Object obj = DBConnection.executeScalar(SQL);
		
		if (obj != null)
			retValue = (long)obj;
	
		return ++retValue;
	}
	
	
	
	public static boolean isValidQuery (String Query)
	//2015.03.25 Alopez We check if the [String Query] of the filter is valid or not.
	{
		boolean retValue = false;
		
		Object obj = DBConnection.executeScalar(Query);
		
		if ((long)obj > 0)
			retValue = true;
		
		return (retValue);
	}
	
	public static boolean istPLZOrtVorhanden(String PLZ, String Ort)
	{
		//boolean retValue = false;
		String SQL = "SELECT COUNT(*) FROM POSTLEITZAHLEN ";
		SQL += "WHERE PLZ = " + quote(PLZ) + " AND ORT = " + quote(Ort);
				
		Object obj = DBConnection.executeScalar(SQL);
		
		return ((long)obj > 0);
	}
	
	
	// Prepared Statements
	// Die SQL-Anweisungen, die mittels execute(), executeQuery() oder
	// executeUpdate() an die Datenbank gesendet werden,
	// haben bis zur Ausführung im Datenbanksystem einige Umwandlungen vor sich.
	// Zuerst müssen sie auf syntaktische Korrektheit getestet werden. Dann
	// werden sie in einen internen Ausführungsplan
	// der Datenbank übersetzt und mit anderen Transaktionen optimal verzahnt.
	// Der Aufwand für jede Anweisung ist messbar. Deutlich besser wäre es
	// jedoch, eine Art Vorübersetzung für SQL-Anweisungen zu nutzen.
	// Diese Vorübersetzung ist eine Eigenschaft, die JDBC unterstützt und die
	// sich Prepared Statements nennt.
	// Vorbereitet (engl. prepared) deshalb, weil die Anweisungen in einem
	// ersten Schritt zur Datenbank geschickt und dort in ein internes
	// Format umgesetzt werden. Später verweist ein Programm auf diese
	// vorübersetzten Anweisungen, und die Datenbank kann sie schnell ausführen,
	// da sie in einem optimalen Format vorliegen. Ein Geschwindigkeitsvorteil
	// macht sich immer dann besonders bemerkbar, wenn Schleifen Änderungen
	// an Tabellenspalten vornehmen. Dies kann durch die vorbereiteten
	// Anweisungen schneller geschehen.
	
//	public static PreparedStatement prepareInsertPLZOrt()
//	{
//		
//		String SQL = "INSERT INTO POSTLEITZAHLEN ";
//		SQL += "(PRIMARYKEY, PLZ, ORT) ";
//		SQL += "VALUES(?, ?, ?)";
//		
//		return DBConnection.prepareStatement(SQL);
//		
//	}

	
//	public static boolean insertPLZOrtPrepared(PreparedStatement prepStatement, long PrimaryKey, String PLZ, String Ort)
//	{
//		
//		boolean retValue = false;
//		
//		try
//		{
//			prepStatement.setLong(1, PrimaryKey);
//			prepStatement.setString(2, PLZ);
//			prepStatement.setString(3, Ort);
//			
//			prepStatement.executeUpdate();
//			retValue = true;
//			
//		}
//		catch (Exception ex)
//		{
//			JOptionPane.showMessageDialog(null,  "Fehler beim Einlesen der Datei: " + ex.getMessage(), "E/A Fehler", JOptionPane.ERROR_MESSAGE);
//		}
//		
//		return retValue;
//		
//	}
	
	
	//2015.03.25 Alopez
	public static PreparedStatement prepareInsert_Author_CD_Song()
	{
		
		String SQL = "INSERT INTO cd_songs ";
		SQL += "(ID, Typ_Music, Author, CD_Name, Song_Name, Country) ";
		SQL += "VALUES(?, ?, ?, ?, ?, ?)";
		
		return DBConnection.prepareStatement(SQL);
		
	}
	
	//2015.03.25 Alopez
	public static boolean inser_Author_CD_Song_Prepared(PreparedStatement prepStatement, long ID, String Typ_Music, String Author, String CD_Name,
														String Song_Name, String Country)
	{
		
		boolean retValue = false;
		
		try
		{
			prepStatement.setLong(1, ID);
			prepStatement.setString(2, Typ_Music);
			prepStatement.setString(3, Author);
			prepStatement.setString(4, CD_Name);
			prepStatement.setString(5, Song_Name);
			prepStatement.setString(6, Country);
			
			prepStatement.executeUpdate();
			retValue = true;	
			
			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,  "Fehler beim Einlesen der Datei inser_Author_CD_Son_Prepared: " + ex.getMessage(), "E/A Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		return retValue;
		
	}
	
/*	public static PreparedStatement prepareIstPLZOrtVorhanden()
	{
		
		String SQL = "SELECT COUNT(*) FROM POSTLEITZAHLEN ";
		SQL += "WHERE Typ_Music = ? AND PLZ = ? AND ORT = ?";
		
		return DBConnection.prepareStatement(SQL);
	}*/
	
	
	public static PreparedStatement prep_IsAuthor_CD_SongAvaliable()
	//2015.04.01 Alopez we control that there is not one value with the same
	// Author, CD_Name, CD_Song
	{
		
		String SQL = "SELECT COUNT(*) FROM cd_songs ";
		SQL += "WHERE  Author = ? AND CD_Name = ? AND Song_Name = ?";
		
		
		return DBConnection.prepareStatement(SQL);
	}
	
	public static boolean ist_Author_CD_Song_AlreadyIN(PreparedStatement prepStatement, String Author, String CD, String Song)
	{
		
		boolean retValue = false;
		
		try
		{
			prepStatement.setString(1, Author);
			prepStatement.setString(2, CD);
			prepStatement.setString(3, Song);
			
			
			ResultSet rSet = prepStatement.executeQuery();
			rSet.next();
			Object obj = rSet.getObject(1);
			rSet.close();
			
			retValue = ((long)obj > 0);
			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,  "Fehler beim Einlesen der Datei: " + ex.getMessage(), "E/A Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		return retValue;
		
	}
	
	public static PreparedStatement prepIsType_Author_CD_Song_ContryAvaliable()
	{
		//	tbID, tbTypMusic, tbAuthor, tbCD_Name, tbSong_Name, tbCountry
		
		String SQL = "SELECT COUNT(*) FROM cd_songs ";
		SQL += "WHERE Typ_Music = ?  AND Author = ? AND CD_Name = ? AND Song_Name = ? AND  Country = ?";
		
		//Typ_Music = ? , Author = ?, CD_Name = ?, Song_Name = ?, Country = ?";
		
		return DBConnection.prepareStatement(SQL);
	}
	
	
	
	public static boolean ist_Type_Author_CD_Song_CountryAlreadyIN(PreparedStatement prepStatement, String Typ_Music, String Author, String CD, String Song, String Country)
	{
		
		boolean retValue = false;
		
		try
		{
			prepStatement.setString(1, Typ_Music);
			prepStatement.setString(2, Author);
			prepStatement.setString(3, CD);
			prepStatement.setString(4, Song);
			prepStatement.setString(5, Country);
			
			ResultSet rSet = prepStatement.executeQuery();
			rSet.next();
			Object obj = rSet.getObject(1);
			rSet.close();
			
			retValue = ((long)obj > 0);
			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,  "Fehler beim Einlesen der Datei: " + ex.getMessage(), "E/A Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		return retValue;
		
	}
	
//	public static PreparedStatement prepareUpdatePLZOrt()
//	{
//		
//		String SQL = "UPDATE POSTLEITZAHLEN ";
//		SQL += "SET PLZ = ?, ORT = ? ";
//		SQL += "WHERE PRIMARYKEY = ?";
//		
//		return DBConnection.prepareStatement(SQL);
//		
//	}
	

	
/*	public static boolean updatePLZOrtPrepared(PreparedStatement prepStatement, long PrimaryKey, String PLZ, String Ort)
	{
		boolean retValue = false;
				
		try
		{
			prepStatement.setString(1, PLZ);
			prepStatement.setString(2, Ort);
			prepStatement.setLong(3, PrimaryKey);
			
			prepStatement.executeUpdate();
			retValue = true;
			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,  "Fehler beim Aktualisieren des Datensatzes: " + ex.getMessage(), "E/A Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		return retValue;
		
	}*/
	
	public static PreparedStatement prepareUpdateAuthor_Cd_Song()
	{
		
		String SQL = "UPDATE cd_Songs ";
		SQL += "SET Typ_Music = ? , Author = ?, CD_Name = ?, Song_Name = ?, Country = ?";
		SQL += "WHERE ID = ?";
		
		return DBConnection.prepareStatement(SQL);
		
	}
	
	public static boolean updateAuthor_Cd_SongPrepared(PreparedStatement prepStatement, long ID, String typ_Music, String Author, String CD, String Song, String Country)
	{
		
		
		boolean retValue = false;
				
		try
		{
			prepStatement.setString(1, typ_Music);
			prepStatement.setString(2, Author);
			prepStatement.setString(3, CD);
			prepStatement.setString(4, Song);
			prepStatement.setString(5, Country);
			
			prepStatement.setLong(6, ID);
			
			prepStatement.executeUpdate();
			retValue = true;
			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,  "Fehler beim Aktualisieren des Datensatzes: " + ex.getMessage(), "E/A Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		return retValue;
		
	}
	
}
