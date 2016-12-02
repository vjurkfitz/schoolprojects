/** CST 8277 - Enterprise Application Programming
 *  Assignment 1
 *  By Victoria Jurkfitz Kessler Thibes
 *  File: Sprite.java
 *  Date: 27/09/2016
 */

package bouncingsprites;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/** Sprite that bounces around the scene **/
public class Sprite implements java.lang.Runnable
{

	/** Panel where the sprites are running **/
	private SpritePanel panel;
	
	/** Box - box inside the panel where only 2 sprites can move at a time **/
	private Box box;
	
	/** Flag to know if is already in the box's limits or not **/
	private boolean inBox;
	
	/** Randomizer **/
	public final static Random random = new Random();
	
	/** Size **/
	final static int SIZE = 10;
	
	/** Speed **/
	final static int MAX_SPEED = 5;
	
	/** Coordinates for position (x and y) **/
	private int x;
	private int y;
	
	/** Speed in coordinates (dx, dy) **/
	private int dx;
	private int dy;
	
	/** Sprite color **/
	private Color color = Color.BLUE;

	/** Constructor **/
    public Sprite (SpritePanel panel, Box box)
    {
    	this.panel = panel;
    	this.box = box;
    	this.inBox = false;
    	
    	// Initial position
        x = random.nextInt(panel.getWidth());
        y = random.nextInt(panel.getHeight());
        
        // Fixed speed for x and y
        dx = random.nextInt(2*MAX_SPEED) - MAX_SPEED;
        dy = random.nextInt(2*MAX_SPEED) - MAX_SPEED;
    }

    /** Main method **/
    public void run()
    {
    	while (true)
    	{
    		this.move();
	    
	        try
	        {
	            Thread.sleep(40);  // wake up roughly 25 frames per second
	            
	            // Within box's limits for the first time
	            if (enteredBox() && !inBox)
	            {
	    			box.blockingPut(this);
	    			inBox = true;
	            }
	            
	            // Just left box's limits 
	            else if (!enteredBox() && inBox)
	            {
	            	for (int i=0; i<box.getOccupants().length; i++)
	            	{
	            		if (box.getOccupants()[i] != null){
	            			if (box.getOccupants()[i] == this)
	            			{
	            				box.blockingGet(i);
	            				inBox = false;
	            			}
	            		}
	            	}
	            }
	        }
	        catch ( InterruptedException exception ) {
	            exception.printStackTrace();
	        }
	    }
    }
    
    /** Draw sprite **/
    public void draw(Graphics g)
    {
        g.setColor(color);
	    g.fillOval(x, y, SIZE, SIZE);
    }
    
    /** Move **/
    public void move()
    {
	    // check for bounce and make the ball bounce if necessary
        if (x < 0 && dx < 0){
            //bounce off the left wall 
            x = 0;
            dx = -dx;
        }
        if (y < 0 && dy < 0){
            //bounce off the top wall
            y = 0;
            dy = -dy;
        }
        if (x > panel.getWidth() - SIZE && dx > 0){
            //bounce off the right wall
        	x = panel.getWidth() - SIZE;
        	dx = - dx;
        }       
        if (y > panel.getHeight() - SIZE && dy > 0){
            //bounce off the bottom wall
        	y = panel.getHeight() - SIZE;
        	dy = -dy;
        }

        //make the ball move
        x += dx;
        y += dy;
    }
    
    /** Position is within box's limits **/
    private boolean enteredBox()
    {
    	if (x > box.getX0() && x< box.getX1()-SIZE &&
    		y > box.getY0() && y< box.getY1()-SIZE)
    		return true;
    	
    	else return false;
    }
    
}
