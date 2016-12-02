/** CST 8277 - Enterprise Application Programming
 *  Assignment 1
 *  By Victoria Jurkfitz Kessler Thibes
 *  File: SpritePanel.java
 *  Date: 27/09/2016
 */

package bouncingsprites;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import javax.swing.JPanel;

/** Description: Panel that will hold the box and sprites
 * @author Victoria Jurkfitz Kessler Thibes
 */
public class SpritePanel extends JPanel{
	
	/** List with all the active sprites **/
	ArrayList<Sprite> sprites;
	
	/** Box that will be drawn in the scene **/
	Box box;
	
	/** Executor service for multithreading **/
	ExecutorService threadExecutor;
	
	/** Constructor **/
	public SpritePanel(ExecutorService threadExecutor)
	{
		super();
		
		this.threadExecutor = threadExecutor;
		
		addMouseListener(new Mouse());
		
		sprites = new ArrayList<Sprite>();
	}
	
	/** Method for creating a new sprite **/
	private void newSprite (MouseEvent event)
	{
		Sprite sprite = new Sprite(this,box);
		sprites.add(sprite);
		
		threadExecutor.execute(sprite);
	}
	
	/** Draws the scene **/
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
	
	/** Class to deal with a mouse click **/
	private class Mouse extends MouseAdapter
	{
		@Override
	    public void mousePressed( final MouseEvent event )
		{
	        newSprite(event);
	    }
	}

	/** Paints the scene **/
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		box.draw(g);
		
		if (sprites != null)
		{
			for (Sprite s : sprites)
				s.draw(g);
		}
	}
	
	/** Creates new box randomly.
	 * maxX and maxY are the maximum upper left coordinate
	 * that can hold the box (delimited by the frame's size + some space) **/
	public void createBox(int maxX, int maxY)
	{
		box = new Box(maxX,maxY);
	}
}
