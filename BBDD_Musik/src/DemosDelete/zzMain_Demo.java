import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JTextField;


public class zzMain_Demo  
{

	public static void main(String[] args)
	{

		zzFrameDemo  demo = new zzFrameDemo();
		demo.Showme_TheFrame();
		
		//zz_Demo_contextualmenu cm = new zz_Demo_contextualmenu();
		
		//zzPopUpMenu  demo = new zzPopUpMenu();
		
		
	}
	
	public static String insertQuaotes (String Name)
	{
		Name = "\"" + Name + "\"";
		return Name;
	}
}

 
