/** CST 8277 - Enterprise Application Programming
 *  Assignment 3
 *  By Victoria Jurkfitz Kessler Thibes
 *  File: SpriteEntity.java
 *  Date: 08/11/2016
 */

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;


// Entity that will be written to DB - only has basic Sprite information
@Entity
@Table(name="test.sprites")
public class SpriteEntity
{
	private int spriteID;
	private int clientID;
	private int dx;
	private int dy;
	private int maxX;
	private int maxY;
	private int x;
	private int y;
	
	
	@Id
	@GeneratedValue
	public int getSpriteID() {
		return spriteID;
	}
	
	public void setSpriteID(int spriteID) {
		this.spriteID = spriteID;
	}
	
	public int getClientID() {
		return clientID;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
	
	public int getMaxX() {
		return maxX;
	}
	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}
	
	public int getMaxY() {
		return maxY;
	}
	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
