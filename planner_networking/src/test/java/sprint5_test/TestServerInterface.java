package sprint5_test;

import java.rmi.RemoteException;

import software_masters.planner_networking.Server;

public interface TestServerInterface extends Server
{
	public void stop() throws RemoteException;

}
