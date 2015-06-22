package Util;

import java.text.Collator;
import java.util.Locale;

// Sprachabh�ngiges Vergleichen.
// Klasse LocaleString um die Sortierung nach deutschen Sprachregeln durchzuf�hren.
// (Ber�cksichtigung der Umlaute und der s-Ligatur [�]).

// F�r die deutsche Sprache gilt, dass �� zwischen �a� und �b� �quivalent zu �ae� einsortiert wird
// und nicht so, wie Unicode das Zeichen einordnet: hinter dem �z�. �hnliches gilt f�r das �߫. 
// Auch das Spanische hat seine Besonderheiten im Alphabet: Hier gelten das �ch� und das �ll� als einzelner
// Buchstabe, die passend einsortiert werden m�ssen.

// Damit Java f�r alle Landessprachen die String-Vergleiche korrekt durchf�hren kann, bietet die Bibliothek 
// Collator-Klassen. 

public class LocaleString implements Comparable<LocaleString> 
{
	
	private String strValue;
	private static Collator collator;
	private static Locale loc;
	

	public LocaleString(String strValue)
	{
		// Standardm�ssig nutzt getInstance() die aktuelle Einstellung des
		// Systems (Locale.GERMAN).
		this.strValue = strValue;
	}
	
	public LocaleString(String strValue, Locale loc)
	{
		// Auf statische Methoden kann sowohl �ber den Namen als auch �ber die
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
