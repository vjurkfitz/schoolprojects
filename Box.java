/** CST 8277 - Enterprise Application Programming
 *  Assignment 1
 *  By Victoria Jurkfitz Kessler Thibes
 *  File: Box.java
 *  Date: 27/09/2016
 */

package bouncingsprites;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/** Box in the scene. Only two sprites can move inside
 * at a time and it can not be left empty.
 * In the consumer/producer scenario, acts as the synchronized buffer
 */
public class Box {
	
	/** x coordinates **/
	private int x0,x1;
	
	/** y coordinates **/
	private int y0,y1;
	
	/** Whether it has sprites moving inside **/
	private boolean[] occupied;
	
	/** Which sprites are moving inside **/
	private Sprite[] occupants;
	
	/** Color **/
	private Color color = Color.green;
	
	/** Randomizer **/
	public final static Random random = new Random();
	
	/** Constructor **/
	Box(int maxX, int maxY)
	{
		// Upper corner's position (x0, y0)
		x0 = random.nextInt(maxX);
		y0 = random.nextInt(maxY);
		
		// Width
		x1 = x0 + 100;
		
		// Height
		y1 = y0 + 100;
		
		occupied = new boolean[]{false, false};
		occupants = new Sprite[2];
	}
	
	/** Draw box **/
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillRect(x0, y0, x1-x0, y1-y0);
		
		g.setColor(Color.black);
		g.drawRect(x0, y0, x1-x0, y1-y0);
	}
	
	/** Puts a sprite inside the occupants array **/
	public synchronized void blockingPut(Sprite sprite) 
		      throws InterruptedException
	{
		// Both positions occupied -> thread has to wait
		while (occupied[0] && occupied[1])
		{
			color = Color.red;
			this.wait();
		}
		
		// Puts sprite in available position
		for (int i=0; i<occupied.length; i++)
		{
			if (!occupied[i])
			{
				occupied[i] = true;
				occupants[i] = sprite;
				break;
			}
		}
		color = Color.green;
		
		this.notifyAll();
	}
	
	/** Takes a sprite out of occupants array **/
	public synchronized void blockingGet(int i) throws InterruptedException
	{
		occupied[i] = false;
		occupants[i] = null;
		
		while (!occupied[0] && !occupied[1])
		{
			color = Color.yellow;
			wait();
		}
		
		this.notifyAll();
	}
	
	/* GETTERS *************************************************/
	public int getX0()
	{
		return this.x0;
	}
	
	public int getX1()
	{
		return this.x1;
	}
	
	public int getY0()
	{
		return this.y0;
	}
	
	public int getY1()
	{
		return this.y1;
	}
	
	public Sprite[] getOccupants()
	{
		return this.occupants;
	}

}
