package sprint5_test;

import java.rmi.RemoteException;

import software_masters.planner_networking.Server;

public interface TestServerInterface extends Server
{
	/**
	 * shut down the server
	 * @throws RemoteException
	 */
	public void stop() throws RemoteException;

}
