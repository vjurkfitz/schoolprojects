/** CST 8277 - Enterprise Application Programming
 *  Assignment 3
 *  By Victoria Jurkfitz Kessler Thibes
 *  File: Sprite.java
 *  Date: 08/11/2016
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.lang.Runnable;
import java.io.Serializable;

// Actual sprite -> takes information from SpriteEntity
public class Sprite implements Runnable,Serializable {

	private int spriteID;
	private int clientID;
	private int dx;
	private int dy;
	private int maxX;
	private int maxY;
	private int x;
	private int y;
	
	// Size
	final static int SIZE = 10;
	
	// Speed
	final static int MAX_SPEED = 5;
	
	// Constructor using a SpriteEntity as base
	public Sprite(SpriteEntity base)
	{
		this.clientID = base.getClientID();
		this.dx = base.getDx();
		this.dy = base.getDy();
		this.maxX = base.getMaxX();
		this.maxY = base.getMaxY();
		this.x = base.getX();
		this.y = base.getY();
		this.spriteID = base.getSpriteID();
	}
	
    // Draw sprite
    public void draw(Graphics graph)
    {
    	// Unique color based on clientID
    	// Used prime numbers to avoid color repetition
    	int r = (clientID * 57) % 244;	// RGB goes from 0-244
    	int g = 244 - ((clientID * 244 / 11) % 244);
    	int b = (clientID * 131 / 2) % 244;
    	
    	graph.setColor(new Color(r,g,b));
    	graph.fillOval(x, y, SIZE, SIZE);
    }
    
    // Move
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
        
        
        if (x > maxX && dx > 0){
            //bounce off the right wall
        	x = maxX;
        	dx = - dx;
        }       
        if (y > maxY && dy > 0){
            //bounce off the bottom wall
        	y = maxY;
        	dy = -dy;
        }
        
        //make the ball move
        x += dx;
        y += dy;
    }
	
    // Run
	@Override
	public void run()
	{
		while(true)
		{
			this.move();
			
			try
			{
				Thread.sleep(40);  // wake up roughly 25 frames per second
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Exception at Sprite.run()");
			}
		}
		
	}
	
	// Calculates a random speed - used with SpriteEntity
	public static int getRandomSpeed()
	{
		Random random = new Random();
		
		return random.nextInt((2* MAX_SPEED) - MAX_SPEED);
	}
	
	/** GETTERS **/
	public int getSpriteID() {
		return spriteID;
	}

	public int getClientID() {
		return clientID;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
