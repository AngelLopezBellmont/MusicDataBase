
//package guilabsheets;

import javax.swing.*;

import java.awt.event.*;

public class zzPopUpMenu  implements MouseListener
{
	private JPopupMenu pm;
	private JMenuItem mi1, mi2, mi3;
	
	public zzPopUpMenu()
	{
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		JFrame frame = new JFrame("demo");
		
		 pm = new JPopupMenu();
		 mi1 = new JMenuItem("1_opcion");
		 mi2 = new JMenuItem("2_opcion");
		 mi3 = new JMenuItem("3_opcion");
		
		pm.add(mi1);
		pm.add(mi2);
		pm.add(mi3);
		
		frame.setVisible(true);
		frame.setBounds(300, 300, 300, 300);
		
		frame.addMouseListener(this);
		
		/*frame.addMouseListener(new MouseAdapter()
			{
				public void mouseReleased (MouseEvent me)
				{
					pm.show(me.getComponent(), me.getX(), me.getY());
				}
			}
		);*/
		
	}
	
	@Override
	public void mouseReleased(MouseEvent me)
	{
		pm.show(me.getComponent(), me.getX(), me.getY());
		
		
		
		

	}
	


	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (mi1.isSelected()== true)
			System.out.println("mi1");
		
	}


	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

}
