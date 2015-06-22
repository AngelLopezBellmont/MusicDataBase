package Util;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;

import javax.swing.text.JTextComponent;

public class MyFocusTraversalPolicy extends FocusTraversalPolicy
{

	// Die Klasse Vector.
	// Ein Vector ist vergleichbar mit einem dynamischen Array.
	// Die Größe kann sich zur Laufzeit ändern.
	// Seit Java 1.5 ist auch der Vector generisch, d.h. man kann
	// für die Elemente des Vectors den Datentyp angeben.
	// Die Klasse Vector ist, im Gegensatz zu ArrayList, thread-sicher.
	// Alle Zugriffe auf die Elemente des Vectors sind synchronisiert.
	// Damit wird sichergestellt, dass zu einer Zeit nur ein Prozess
	// auf die Elemente zugreifen kann.
	// Bei einem Zugriffsversuch durch einen zweiten Thread wird gewartet,
	// bis der synchronisierte Code vom ersten Thread verlassen wird.
	
	
	private Vector<Component> components;
	
	public MyFocusTraversalPolicy(Vector<Component> components)
	{
		this.components = components;
	}
	

	@Override
	public Component getComponentAfter(Container aContainer, Component c)
	{
		
		// Endlosschleife vermeiden, wenn es keine Komponente gibt, die den
		// Eingabefokus erhalten kann. Der Container bekommt den Fokus.
		if (!containsFocusableComponents())
			return aContainer;
		
		int idx = (components.indexOf(c) + 1) % components.size();
		
		if (isFocusable(components.get(idx)))
			// Die nächste Komponente kann den Fokus erhalten
			return components.get(idx);
		else
			// Die nächste Komponente in der Fokus-Reihenfolge kann den 
			// Fokus nicht erhalten.
			// Versuchen, den Fokus auf die nächste Komponente zu setzen.
			return getComponentAfter(aContainer, components.get(idx));
		
	}

	@Override
	public Component getComponentBefore(Container aContainer, Component c)
	{
	
		// Endlosschleife vermeiden, wenn es keine Komponente gibt, die den
		// Eingabefokus erhalten kann. Der Container bekommt den Fokus.
		if (!containsFocusableComponents())
			return aContainer;
		
		
//		int idx = components.indexOf(c) - 1;
//		if (idx < 0)
//			idx = components.size() - 1;
//		
//		return components.get(idx);
		
		// oder
		int idx = (components.indexOf(c) - 1 + components.size()) % components.size();
		
		if (isFocusable(components.get(idx)))
			// Die vorherige Komponente kann den Fokus erhalten
			return components.get(idx);
		else
			// Die vorherige  Komponente in der Fokus-Reihenfolge kann den 
			// Fokus nicht erhalten.
			// Versuchen, den Fokus auf die vorherige Komponente zu setzen.
			return getComponentBefore(aContainer, components.get(idx));
		
		
	}

	@Override
	public Component getDefaultComponent(Container aContainer)
	{
		return getFirstComponent(aContainer);
	}

	@Override
	public Component getFirstComponent(Container aContainer)
	{
		
		if (isFocusable(components.firstElement()))
			// Auf die erste Komponente positionieren, wenn diese den Fokus erhalten darf.
			return components.firstElement();
		else
			// Versuchen, den Fokus auf die nächste Komponente zu setzen.
			return getComponentAfter(aContainer, components.firstElement());
		
	}

	@Override
	public Component getLastComponent(Container aContainer)
	{
		
		if (isFocusable(components.lastElement()))
			// Auf die letzte Komponente positionieren, wenn diese den Fokus erhalten darf.
			return components.lastElement();
		else
			// Versuchen, den Fokus auf die vorherige Komponente zu setzen.
			return getComponentBefore(aContainer, components.lastElement());
	}
	
	private boolean isFocusable(Component c)
	{
	
		
		JTextComponent tc;
		
		if (c instanceof JTextComponent)
		{
			tc = (JTextComponent)c;
			if (!tc.isEditable())
				return false;
		}
		
		return c.isVisible() && c.isEnabled() && c.isFocusable();
		
	}
	

	private boolean containsFocusableComponents()
	{
		
		boolean retValue = false;
		
		
		for (Component c : components)
		{
			
			if (isFocusable(c))
			{
				retValue = true;
				break;
			}
			
		}
		
		return retValue;
	}
	
	
	public void enableAllComponents(boolean b)
	{
		
		for (Component c : components)
			c.setEnabled(b);
		
	}
	
	
}
