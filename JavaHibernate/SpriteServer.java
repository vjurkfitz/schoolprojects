/** CST 8277 - Enterprise Application Programming
 *  Assignment 3
 *  By Victoria Jurkfitz Kessler Thibes
 *  File: SpriteServer.java
 *  Date: 08/11/2016
 */

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import org.hibernate.Session;	
import org.hibernate.SessionFactory;	
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;	
import org.hibernate.cfg.Configuration;	
import org.hibernate.service.ServiceRegistry;

// Server side
public class SpriteServer implements SpriteServerInterface {
	
	// Used to identify which sprites were created by the same client
	private static int clients = 0;
	
	// Session factory
    private SessionFactory factory = null;
	
	// All active sprites
	private ArrayList<Sprite> sprites;
	
	// Executor service for multithreading
	private ExecutorService threadExecutor;
	
	// Panel height and width
	private final int panelWidth = 400;
	private final int panelHeight = 400;
	
	// Constructor
	public SpriteServer(ExecutorService threadExecutor)
	{
		sprites = new ArrayList<Sprite>();
		this.threadExecutor = threadExecutor;
		
		try
		{
			// Create configuration file
			Configuration config = new Configuration().addAnnotatedClass(SpriteEntity.class).configure();
			
			// Create session factory
			StandardServiceRegistryBuilder sRBuilder = new StandardServiceRegistryBuilder()	
				.applySettings(config.getProperties());	
			ServiceRegistry sR = sRBuilder.build();	
			factory = config.buildSessionFactory(sR);
			
			Session s = factory.getCurrentSession();
				
			// Get sprites from the database
			try
			{
				s.beginTransaction();
				ArrayList<SpriteEntity> rawSprites = (ArrayList<SpriteEntity>) s.createQuery("from SpriteEntity").getResultList();
				s.getTransaction().commit();
				
				for (SpriteEntity rs : rawSprites)
				{
					if (rs.getClientID() > clients)
						clients = rs.getClientID();
					
					// Create a new Sprite from a SpriteEntity
					Sprite toAdd = new Sprite(rs);
					sprites.add(toAdd);
					threadExecutor.execute(toAdd);
				}
			}
			catch (Exception e)
			{
				System.out.println("Problem getting sprites from DB");
				e.printStackTrace();
			}
			factory.close();
			StandardServiceRegistryBuilder.destroy(sR);
		}
		catch(Exception e)
		{
			System.out.println("Problem opening DB");
			e.printStackTrace();
		}
	}
	
	// Creates a new Sprite, which starts to run
	// and adds its information to the database
	public void addSprite (int x, int y, int clientID)
	{
		// Sprite information which will be written to DB
		SpriteEntity toWrite = new SpriteEntity();
		toWrite.setClientID(clientID);
		toWrite.setDx(Sprite.getRandomSpeed());
		toWrite.setDy(Sprite.getRandomSpeed());
		toWrite.setMaxX(panelWidth);
		toWrite.setMaxY(panelHeight);
		toWrite.setX(x);	// Coordinates where mouse was clicked
		toWrite.setY(y);
		
		Configuration config = new Configuration().addAnnotatedClass(SpriteEntity.class).configure();
		
		// Create session factory
		StandardServiceRegistryBuilder sRBuilder = new StandardServiceRegistryBuilder()	
			.applySettings(config.getProperties());	
		ServiceRegistry sR = sRBuilder.build();	
		factory = config.buildSessionFactory(sR);
		
		Session s = factory.getCurrentSession();
		
		// Write sprite information to DB
		s.beginTransaction();
		s.save(toWrite);
		s.getTransaction().commit();
		
		factory.close();
		StandardServiceRegistryBuilder.destroy(sR);
		
		// Create new runnable Sprite and starts it
		Sprite toAdd = new Sprite(toWrite);
		sprites.add(toAdd);
		threadExecutor.execute(toAdd);
	}

	// Updates DB with new Sprite positions
	public void updateDB()
	{
		for (Sprite curr : sprites)
		{
			Configuration config = new Configuration().addAnnotatedClass(SpriteEntity.class).configure();
			
			// Create session factory
			StandardServiceRegistryBuilder sRBuilder = new StandardServiceRegistryBuilder()	
				.applySettings(config.getProperties());	
			ServiceRegistry sR = sRBuilder.build();	
			factory = config.buildSessionFactory(sR);
			
			Session s = factory.getCurrentSession();
			
			s.beginTransaction();
			
			// Looks for Sprite in DB
			SpriteEntity toUpdate = (SpriteEntity) s.createQuery( "from SpriteEntity where spriteID = :id")
    				.setParameter("id",curr.getSpriteID()).getSingleResult();
			
			// Updates position
			toUpdate.setX(curr.getX());
			toUpdate.setY(curr.getY());
			
			s.update(toUpdate);
			s.getTransaction().commit();
			
			factory.close();
			StandardServiceRegistryBuilder.destroy(sR);
		}
		System.out.println("Database was updated with new positions.");
	}
	
	// Disable Hibernate messages -> only fatal ones are left
	private static void disableLogger()
	{
		Enumeration<String> loggers = LogManager.getLogManager().getLoggerNames();
		
		while (loggers.hasMoreElements())
		{
			LogManager.getLogManager().getLogger(loggers.nextElement()).setLevel(Level.SEVERE);
		}
	}
	
	// Main method
	public static void main(String[] args)
	{
		disableLogger();
		
		int portNum = 8082;
		try
		{
			ExecutorService threadExecutor = Executors.newCachedThreadPool();
			
			SpriteServer remoteSprites = new SpriteServer(threadExecutor);
			
			LocateRegistry.createRegistry(portNum);
			System.out.println( "Registry created" );
			UnicastRemoteObject.exportObject(remoteSprites,portNum);
			System.out.println( "Exported" );
			Naming.rebind("//localhost:" + portNum + "/Sprites", remoteSprites);
		}
		catch (Exception e)
		{
			System.out.println("Trouble: " + e);
		}
	} 
	
	/** GETTERS **/
	public ArrayList<Sprite> getSprites()
	{
		return this.sprites;
	}
	
	public int getPanelWidth()
	{
		return this.panelWidth;
	}
	
	public int getPanelHeight()
	{
		return this.panelHeight;
	}
	
	public ExecutorService getThreadExecutor()
	{
		return this.threadExecutor;
	}
	
	public int getClientNo()
	{
		clients++;
		return clients;
	}
}
