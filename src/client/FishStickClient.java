/* File FishStickClient.java
 * Author: Stanley Pieda
 * Created On: Jan 2018
 * Modified By: Phuong Pham, Shamarokh Arjumand
 * Modifed On: March 27, 2018
 * Description: Client used to insert FishStick records on server.
 */
package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.hibernate.exception.GenericJDBCException;

import datatransfer.FishStick;
import remoteinterface.FishStickService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.rmi.NotBoundException;


/**
 * This class implements a fishstick client that invokes fishstickService Remote calls.
 * @author Phuong Pham, Shamarokh Arjumand
 * @version 1.0.0 
 */
public class FishStickClient {
	
	private String serverName = "localhost"; 
	private int portNum = 8081;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //BufferedReader for reading user input.

  
	/**
	 * Constructor with a specific server to initialize variable members
	 * @param serverName  the name of the server
     */
	public FishStickClient(String serverName){
		this.serverName = serverName;
	}
	
	/**
	 * Constructor with a specific server and port to initialize variable members
	 * @param serverName  the name of the server
     * @param portNum server/service port on which client connects.
     */
	public FishStickClient(String serverName, int portNum){
		this.serverName = serverName;
		this.portNum = portNum;
	}
	
	 /**
     * This main method is the entry point of the fishStickClient and instantiates the fishStickClient.
     * and run the client. 
     * @param args set at the time of running the client.
     * @author Phuong Pham, Shamarokh Arjumand
     * 
     */
	public static void main(String[] args) {
		switch (args.length){
		case 2:
			(new FishStickClient(args[1],Integer.parseInt(args[2]))).runClient();
			break;
		case 1:
			(new FishStickClient("localhost",Integer.parseInt(args[1]))).runClient();
			break;
		case 0:
		default:
			(new FishStickClient("localhost", 8081)).runClient();
			break;
		}
	}
	
	/**
     * Helper method to run the client and do the lookup for RMI.
     * @author Phuong Pham, Shamarokh Arjumand
     */
	public void runClient(){
		try{
			 String message = null;
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a");
		System.out.println("FishsticksClient by:Phuong Pham, Shamarokh Arjumand run on " + dateTime.format(format));
		  System.out.println("Attempting to connect to rmi://" + serverName + ":" + this.portNum + "/FishStickService");
          FishStickService fishStickService = (FishStickService) Naming.lookup("rmi://" + serverName + ":" + this.portNum + "/FishStickService");

          do {
              try {
                  FishStick fishStick = buildFishStick();
                  fishStickService.insert(fishStick);
                  FishStick dbReturned = fishStickService.findByUUID(fishStick.getUUID());
                  System.out.println("fishStick returned " + dbReturned);

                  System.out.println("Do you want to insert another fishStick? (y/n)");
                  message = br.readLine();
                  if (message.equals("n")) {
                      System.out.println("Shutting down connection to server");
                      System.exit(-1);
                  }
              } catch (GenericJDBCException e) {
            	  System.out.println(e);
              } catch (IOException e) {
                  e.printStackTrace();
                  message = null;
              } catch (Exception e) {
            	  System.out.println("SQL Server is down  !!");
            	  System.out.println(e);
            	   System.exit(-1);             }
          } while (message.equals("y"));
      } catch (MalformedURLException murle) {
          System.out.println();
          System.out.println("MalformedURLException");
          System.out.println(murle);
      } catch (RemoteException re) {
          System.out.println();
          System.out.println("RemoteException");
          System.out.println(re);
      } catch (NotBoundException nbe) {
          System.out.println();
          System.out.println("NotBoundException");
          System.out.println(nbe);
      }
  }

  /**
   * Utility method to input the FishStick information from the server.
   * @return an object of FishStick which needs to be persisted to the database.
   * @throws Exception - May throw Exception.
   * @author Phuong Pham, Shamarokh Arjumand
   */
  private FishStick buildFishStick() throws Exception {
		FishStick newFishStick = new FishStick();
		int recordNumber;
		System.out.println("Please enter data for new FishStick! => ");
		System.out.print("Please enter record number: ");
		recordNumber= Integer.parseInt(br.readLine());
		newFishStick.setRecordNumber(recordNumber); 		
		System.out.print("Please enter Omega: ");
		newFishStick.setOmega(br.readLine().trim());
		System.out.print("Please enter Lambda: ");
		newFishStick.setLambda(br.readLine().trim());
		//generate a UUID for the FishStick
        UUID uuid = UUID.randomUUID();
        newFishStick.setUUID(uuid.toString());
      return newFishStick;
  }
}

