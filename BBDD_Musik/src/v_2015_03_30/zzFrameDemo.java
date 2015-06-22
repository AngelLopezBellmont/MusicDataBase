package v_2015_03_30;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class zzFrameDemo extends JDialog implements WindowListener
{
	private JFrame myFrameDemo;
	private JTextField tb1, tb2, tb3, tb4;
	private JLabel jlabel1, jlabel2, jlabel3, jlabel4;

	public zzFrameDemo()
	{
		initializeComponents();
	}

	public void initializeComponents()
	{
		Color invalidBoton	 = 	new Color(233,150,122);
		Color selectedColor	 = 	new Color(160,82,45);
		Color alternateColor = 	new Color(250,240,230);
		Color defaultColor	 = 	new Color(255,255,240);

		//sdfsd
		
		this.setTitle("Erstes Grafikprogramm");
		this.setBounds(700, 400, 350, 300);
		this.setLayout(null);
		this.getContentPane().setBackground(Color.WHITE);
		
		// Keine Grössenänderung des Frames
		// myFrameDemo.setResizable(false);




		jlabel1 = new JLabel("selectedColor:");
		jlabel1.setBounds(10, 10, 100, 25);
		this.add(jlabel1);

		tb1 = new JTextField();
		tb1.setBounds(110, 10, 100, 25);
		tb1.setBackground(selectedColor);
		this.add(tb1);

		jlabel2 = new JLabel("alternateColor:");
		jlabel2.setBounds(10, 60, 100, 25);
		this.add(jlabel2);

		tb2 = new JTextField();
		tb2.setBounds(110, 60, 100, 25);
		tb2.setBackground(alternateColor);
		this.add(tb2);

		jlabel3 = new JLabel("defaultColor:");
		jlabel3.setBounds(10, 110, 100, 25);
		this.add(jlabel3);

		tb3 = new JTextField();
		tb3.setBounds(110, 110, 100, 25);
		tb3.setBackground(defaultColor);
		this.add(tb3);
		
		jlabel4 = new JLabel("selectedColor:");
		jlabel4.setBounds(10, 160, 100, 25);
		this.add(jlabel4);

		tb4 = new JTextField();
		tb4.setBounds(110, 160, 100, 25);
		tb4.setBackground(invalidBoton);
		this.add(tb4);

	}

	public void Showme_TheFrame()
	{
		this.setVisible(true);
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
	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

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

}
