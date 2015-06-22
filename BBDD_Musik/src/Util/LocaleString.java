package Util;

import java.text.Collator;
import java.util.Locale;

// Sprachabhängiges Vergleichen.
// Klasse LocaleString um die Sortierung nach deutschen Sprachregeln durchzuführen.
// (Berücksichtigung der Umlaute und der s-Ligatur [ß]).

// Für die deutsche Sprache gilt, dass »ä« zwischen »a« und »b« äquivalent zu »ae« einsortiert wird
// und nicht so, wie Unicode das Zeichen einordnet: hinter dem »z«. Ähnliches gilt für das »ß«. 
// Auch das Spanische hat seine Besonderheiten im Alphabet: Hier gelten das »ch« und das »ll« als einzelner
// Buchstabe, die passend einsortiert werden müssen.

// Damit Java für alle Landessprachen die String-Vergleiche korrekt durchführen kann, bietet die Bibliothek 
// Collator-Klassen. 

public class LocaleString implements Comparable<LocaleString> 
{
	
	private String strValue;
	private static Collator collator;
	private static Locale loc;
	

	public LocaleString(String strValue)
	{
		// Standardmässig nutzt getInstance() die aktuelle Einstellung des
		// Systems (Locale.GERMAN).
		this.strValue = strValue;
	}
	
	public LocaleString(String strValue, Locale loc)
	{
		// Auf statische Methoden kann sowohl über den Namen als auch über die
		// Referenz derr Klasse zugegriffen werden.
		setLocale(loc);
		this.strValue = strValue;

	}
	
	
	@Override
	public String toString()
	{
		
		return strValue;
	}
	
	public static void setLocale(Locale loc)
	{
		LocaleString.loc = loc;
		collator = Collator.getInstance(loc);
	}
	
	public static Locale getLocale()
	{
		return LocaleString.loc;
	}
	
	
	@Override
	public int compareTo(LocaleString that)
	{
		
		if (collator == null)
		{
			loc = Locale.getDefault();
			setLocale(loc);
		}

		return collator.compare(this.toString(), that.toString());
		
		
	}

}
