/* File FishStickServer.java
 * Author: Todd Kelley
 * Modified By: Stanley Pieda
 * Modifed On: Jan 2018
 * Modified By: Phuong Pham, Shamarokh Arjumand
 * Modifed On: March 27, 2018
 * Description: RMI Server startup.
 */
package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import remoteinterface.FishStickService;
/*
 * Referenced works on shutting down RMI within the application:
 * https://coderanch.com/t/210114/java/Shut-RMI-Registry
 * https://community.oracle.com/thread/1180058?start=0&tstart=0
 */

/**
 * This class implements the server which creates the registry and export the
 * service.
 * 
 * @author  Phuong Pham, Shamarokh Arjumand
 *
 */
public class FishStickServer {
	public static void main(String[] args) {
		try {
			int portNum = 8081;
			if(args.length > 0){
				portNum = Integer.parseInt(args[0]);
			}
			//create the remote service
			FishStickServiceImpl service = new FishStickServiceImpl();
			//create RMI registry, saving reference to it
			java.rmi.registry.Registry registry = LocateRegistry.createRegistry(portNum);
				
			// message to users (this is done already on, next line)
			System.out.println( "FishStick Server  developed by Phuong Pham, Shamarokh Arjumand" );
			System.out.println( "FishStick Service Registry created" );
			// export the remote service
			UnicastRemoteObject.exportObject(service, 0);
			
			// message to users (this is done already on next line)			
			System.out.println( "Service is exported" );
			
			// rebind using portNum with service name /FishStickService
			Naming.rebind("//localhost:" + portNum + "/FishStickService", service);
			
			System.out.println("Press any key to shutdown remote object and end program");
			Scanner input = new Scanner(System.in);
			input.nextLine(); // pause, let server-side admin close down connections
			
			service.shutDownDao(); // close down Hibernate resources
			
			System.out.println("un-exporting remote object");
			UnicastRemoteObject.unexportObject(service, true); // remove remote object
			
			//next lines not needed in this case, port 1099 is free on JVM exit according to TCPView
			//see: https://docs.microsoft.com/en-us/sysinternals/downloads/tcpview
			System.out.println("Shutting down registry"); 
			UnicastRemoteObject.unexportObject(registry, true);
		} catch (Exception e) {
			System.out.println("Trouble: " + e);
			e.printStackTrace();
		}
	}
}
