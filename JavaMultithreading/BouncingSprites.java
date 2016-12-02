/** CST 8277 - Enterprise Application Programming
 *  Assignment 1
 *  By Victoria Jurkfitz Kessler Thibes
 *  File: BouncingSprites.java
 *  Date: 27/09/2016
 */

package bouncingsprites;

import javax.swing.JFrame;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/** Description: Sprites bounce around in a window 
 * 
 * @author Victoria Jurkfitz Kessler Thibes
 */
public class BouncingSprites {
	
	/** Main window **/
    private JFrame frame;
    
    /** Panel for the sprites **/
    private SpritePanel panel;
    
    /** Executor that controls multithreading **/
    private ExecutorService threadExecutor;
    
    /** Default constructor **/
    public BouncingSprites()
    {
    	threadExecutor = Executors.newCachedThreadPool();
    	panel = new SpritePanel(threadExecutor);
    	
        frame = new JFrame("Bouncing Sprite");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
        
        panel.createBox(frame.getHeight()-200, frame.getWidth()-200);
    }
    
    /** Starts animation **/
    public void start()
    {
    	panel.animate();
    }
    
    /** Main function **/
    public static void main(String[] args) throws InterruptedException {
        new BouncingSprites().start();
    }
}
