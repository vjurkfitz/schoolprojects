/** CST 8277 - Enterprise Application Programming
 *  Assignment 3
 *  By Victoria Jurkfitz Kessler Thibes
 *  File: ClientPanel.java
 *  Date: 08/11/2016
 */

import java.awt.Graphics;
import java.rmi.RemoteException;

import javax.swing.JPanel;

// Panel to draw sprites
public class ClientPanel extends JPanel {

	// Remote information
	private SpriteServerInterface server;
	
	public ClientPanel(SpriteServerInterface server)
	{
		super();
		this.server = server;
	}
	
	// Paints the scene
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		try {
			// Gets sprites from server
			if (!server.getSprites().isEmpty())
			{
				for (Sprite s : server.getSprites())
					s.draw(g);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Animate
	public void animate()
	{
	    while (true)
	    {
	        repaint();
	        
	        //sleep while waiting to display the next frame of the animation
	        try
	        {
	            Thread.sleep(40);  // wake up roughly 25 frames per second
	        }
	        catch ( InterruptedException exception )
	        {
	            exception.printStackTrace();
	        }
	    }
	}
}
