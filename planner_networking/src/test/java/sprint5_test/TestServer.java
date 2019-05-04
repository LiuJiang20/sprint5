package sprint5_test;


import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.rmi.CORBA.Stub;

import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;

public class TestServer extends ServerImplementation implements TestServerInterface {

	public TestServer() throws RemoteException 
	{

	}

	public static void testSpawn() {
		if (server == null) {
			System.out.println("Starting New Server");
			Registry registry = null;
			Server stub = null;
			try {
				server = new TestServer();
				registry = LocateRegistry.createRegistry(1060);
				stub = (Server) UnicastRemoteObject.exportObject(server, 0);
				registry.bind("PlannerServer", stub);
			} catch (RemoteException e) {
				System.out.println("Unable to create and bind to server using rmi.");
				System.exit(0);
			} catch (AlreadyBoundException e) {
				System.out.println("Connecting to Existing Server");
			}
			return;
		}
		
		Registry registry = null;
		Server stub = null;
		try {
			 registry = LocateRegistry.getRegistry(1060);
			 registry.lookup("PlannerServer");
			 
		} catch (NotBoundException e) {
			 try {
				stub = (Server)UnicastRemoteObject.exportObject(server, 0);
				registry.bind("PlannerServer", stub);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (AlreadyBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Connecting to Existing Server");
	}
	
	
	@Override
	public void stop() throws RemoteException
	{ 
		Registry registry = LocateRegistry.getRegistry(1060);
		try {
			registry.unbind("PlannerServer");
			UnicastRemoteObject.unexportObject(server, true);
		} 
		catch (NotBoundException e)
		{
			e.printStackTrace();
		}
	}

	

}
