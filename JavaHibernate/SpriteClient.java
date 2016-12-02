/** CST 8277 - Enterprise Application Programming
 *  Assignment 3
 *  By Victoria Jurkfitz Kessler Thibes
 *  File: SpriteClient.java
 *  Date: 08/11/2016
 */

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

// Client side
public class SpriteClient {
	
	// Server interface
	private static SpriteServerInterface sprites;
	
	// Panel to draw sprites
	private static ClientPanel panel;
	
	// clientID as will be written on DB
	private static int clientID;
	
	// Main method
	public static void main(String[] args) throws InterruptedException {
		
		SpriteClient client = new SpriteClient();
		
		int port = 8082;
		String serverName = new String("localhost");
		
		try
		{
			// Get SpriteServer
			sprites = (SpriteServerInterface) 
					Naming.lookup("rmi://"+serverName+":"+port+"/Sprites");
			
			clientID = sprites.getClientNo();
			
			// Create panel and frame
			panel = new ClientPanel(sprites);
			panel.addMouseListener(client.new Mouse());
	
			JFrame frame = new JFrame("Bouncing Sprites - RMI");
			frame.setSize(sprites.getPanelWidth(), sprites.getPanelHeight());
			frame.addWindowListener(client.new Window());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	        frame.add(panel);
	        frame.setVisible(true);
	        
	        panel.animate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception: SpriteClient");
		}
		
	}
	
	// Class was created to call updateToDB whenever a window is closed
	private class Window implements WindowListener
	{

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		// Only thing that has code
		@Override
		public void windowClosing(WindowEvent e) {
			try {
				// Updates DB before closing
				System.out.println("Saving positions to DB...");
				sprites.updateDB();
				System.out.println("Done");
				
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	/** Class to deal with a mouse click **/
	private class Mouse extends MouseAdapter
	{
		@Override
	    public void mousePressed( final MouseEvent event )
		{
			try
			{
				// Adds sprite add mouse coordinates
				System.out.println("Creating sprite and saving to DB...");
				sprites.addSprite(event.getX(),event.getY(),clientID);
				System.out.println("Done");
			}
			catch (RemoteException e)
			{
				e.printStackTrace();
				System.out.println("Exception adding a new sprite to server");
			}
	    }
	}
	
}
