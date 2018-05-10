/* File FishStickServiceImpl.java
 * Author: Stanley Pieda
 * Modified By: 
 * Modifed On: Jan 2018
 * Modified By: Phuong Pham, Shamarokh Arjumand
 * Modifed On: March 27, 2018
 * Description: Remote Object that permits clients to insert
 *              FishStick records into a database, as well as
 *              retrieve them using String based uuid.
 */
package server;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

import datatransfer.FishStick;

import dataaccesslayer.FishStickDao;
import dataaccesslayer.FishStickDaoImpl;

import remoteinterface.FishStickService;
/**
 * This class implements the fishStick service
 * @author Phuong Pham, Shamarokh Arjumand
 */
public class FishStickServiceImpl extends RemoteServer  implements FishStickService  {
	private static final long serialVersionUID = 1L;
	FishStickDao fishStickDao = FishStickDaoImpl.INSTANCE;
	public FishStickServiceImpl() throws RemoteException {}
	
	/**
	 * @author Phuong Pham, Shamarokh Arjumand
	 */
	@Override
	public void insert(FishStick fishStick) throws RemoteException {
		// code to use the FishStickDaoImpl to insert a record
		fishStickDao.insertFishStick(fishStick);
	}

	/**
	 * @author Phuong Pham, Shamarokh Arjumand
	 */
	@Override
	public FishStick findByUUID(String uuid) throws RemoteException {
		// code to use the FishStickDaoImpl to lookup and return a FishStick
		return fishStickDao.findByUUID(uuid);
	}
	
	/**
	 * @author Phuong Pham, Shamarokh Arjumand
	 */
	public void shutDownDao() {
		FishStickDaoImpl.INSTANCE.shutdown();
	}
}
