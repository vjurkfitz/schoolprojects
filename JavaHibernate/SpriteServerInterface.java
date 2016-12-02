/** CST 8277 - Enterprise Application Programming
 *  Assignment 3
 *  By Victoria Jurkfitz Kessler Thibes
 *  File: SpriteServerInterface.java
 *  Date: 08/11/2016
 */

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

// Interface for a SpriteServer
public interface SpriteServerInterface extends Remote {
		
	public void addSprite (int x, int y, int clientID) throws java.rmi.RemoteException;
	public ArrayList<Sprite> getSprites() throws java.rmi.RemoteException;
	public int getPanelWidth() throws java.rmi.RemoteException;
	public int getPanelHeight() throws java.rmi.RemoteException;
	public ExecutorService getThreadExecutor() throws java.rmi.RemoteException;
	public int getClientNo() throws java.rmi.RemoteException;
	public void updateDB() throws java.rmi.RemoteException;
}
