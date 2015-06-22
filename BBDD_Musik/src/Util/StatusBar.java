package Util;

import java.awt.BorderLayout;

import javax.swing.*;

public class StatusBar extends JLabel
{
	
	public StatusBar()
	{
		
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new BorderLayout());
		
	}
	
	
	public void setMessage(String message)
	{
		this.setText(" " + message);
	}
	

}
