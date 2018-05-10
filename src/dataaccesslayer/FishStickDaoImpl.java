/* File: FishStickDaoImpl.java
 * Author: Stanley Pieda
 * Date: Jan 2018
 * Modified By: Phuong Pham, Shamarokh Arjumand
 * Modifed On: March 27, 2018
 * References:
 * Ram N. (2013). Data Access Object Design Pattern or DAO Pattern [blog] Retrieved from
 * http://ramj2ee.blogspot.in/2013/08/data-access-object-design-pattern-or.html
 */
package dataaccesslayer;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.query.NativeQuery;
import datatransfer.FishStick;


/**
 * This enum is a singleton that implements the FishStickDao interface.
 * It is responsible for loading the hibernate config and create sessions.
 * @author Phuong Pham, Shamarokh Arjumand
 */
public enum FishStickDaoImpl implements FishStickDao{

	/** Only use one constant for Singleton Design Pattern */
    INSTANCE;
	private SessionFactory factory=null;
	private final StandardServiceRegistry registry = buildRegistry();
	
	 /**
     * This instantiates the registry and create a session for CRUD operations.
     */
	private FishStickDaoImpl(){
		try {
			// A SessionFactory is set up once for an application!
			MetadataImplementor meta = 
					(MetadataImplementor) new MetadataSources( registry )
					.addAnnotatedClass(FishStick.class)
					.buildMetadata();
			factory = meta.buildSessionFactory();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Problem starting hibernate, attempting to shutdown");
			shutdown();
		}
	}
	private StandardServiceRegistry buildRegistry() {
		StandardServiceRegistry registry = null;
		try {
			// A SessionFactory is set up once for an application!
			registry =  new StandardServiceRegistryBuilder()
					.configure() // configures settings from hibernate.cfg.xml
					.build();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Problem starting hibernate, attempting to shutdown");
			shutdown();
		}
		return registry;
	}

	/**
	 * @author Phuong Pham, Shamarokh Arjumand
	 */
	@Override
	public void insertFishStick(FishStick fishStick){
		// code here to use Hibernate to insert a record.
		 System.out.println("Persisting fishStick:  " + fishStick);
	        Session session = factory.openSession();
	        session.beginTransaction();
	        session.save(fishStick);
	        session.getTransaction().commit();
	        System.out.println("fishStick is persisted");	
	}

	/**
	 * @author Phuong Pham, Shamarokh Arjumand
	 */
	@SuppressWarnings("unchecked")
	@Override
	public FishStick findByUUID(String uuid){
		// Code here to use Hibernate to look up a record based on UUID
		   System.out.println("Retrieving FishSticks from db with uuid " + uuid);
	        final String query = "SELECT * FROM FishStick WHERE UUID = '" + uuid + "'";
	        Session session = factory.getCurrentSession();
	        session.beginTransaction();
	        NativeQuery<FishStick> response = session.createNativeQuery(query, FishStick.class);
	        FishStick fishStick = response.getResultList().get(0);
	        session.getTransaction().commit();
	        return fishStick;		
	}
	/**
	 * @author Phuong Pham, Shamarokh Arjumand
	 */
	public void shutdown() {
		// code here to close the factory, and to destroy the registry
		System.out.println("Closing factory");
		try {
			if(factory != null && factory.isClosed() == false) {
				factory.close();
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Destroying registry");
		try {
			if(registry != null) {
				StandardServiceRegistryBuilder.destroy(registry);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
