package software_masters.model;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import software_masters.planner_networking.Account;
import software_masters.planner_networking.Department;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.Server;

/**
 * Proxy server that the model talks to.
 * It connects to the real server by RMI
 * 
 * Most of its methods just call the same method in real server
 * Only savePlan() is overridden.
 * @author liu.jiang
 *
 */
public class localServer implements Server {

	Server server;
	PlannerModel model;
	String ip;
	int port;

	public localServer(PlannerModel model) { this.model = model; }

	public void connectToServer(String ip, int port) throws RemoteException, NotBoundException {

		this.ip = ip;
		this.port = port;
		Registry registry = LocateRegistry.getRegistry(ip, port);
		Server stub = (Server) registry.lookup("PlannerServer");
		this.server = stub;
	}

	@Override
	public String logIn(String username, String password) throws IllegalArgumentException, RemoteException {

		return server.logIn(username, password);
	}

	@Override
	public PlanFile getPlan(String year, String cookie) throws IllegalArgumentException, RemoteException {
		return server.getPlan(year, cookie);
	}

	@Override
	public PlanFile getPlanOutline(String name, String cookie) throws IllegalArgumentException, RemoteException {
		return server.getPlanOutline(name, cookie);
	}

	@Override
	public void savePlan(PlanFile plan, String cookie) throws IllegalArgumentException{

		try {
			server.savePlan(plan, cookie);
		} catch (RemoteException e) {
			new pushPlan(plan, cookie).start();
		}
	}

	@Override
	public void addUser(String username, String password, String departmentName, boolean isAdmin, String cookie)
			throws IllegalArgumentException, RemoteException {
		server.addUser(username, password, departmentName, isAdmin, cookie);
	}

	@Override
	public void flagPlan(String departmentName, String year, boolean canEdit, String cookie)
			throws IllegalArgumentException, RemoteException {
		// TODO Auto-generated method stub
		server.flagPlan(departmentName, year, canEdit, cookie);
	}

	@Override
	public void addDepartment(String departmentName, String cookie) throws IllegalArgumentException, RemoteException {
		// TODO Auto-generated method stub
		server.addDepartment(departmentName, cookie);
	}

	@Override
	public void addPlanTemplate(String name, PlanFile plan) throws RemoteException {
		server.addPlanTemplate(name, plan);
	}

	@Override
	public void save() throws RemoteException { server.save(); }

	@Override
	public ConcurrentHashMap<String, Account> getLoginMap() throws RemoteException { return server.getLoginMap(); }

	@Override
	public void setLoginMap(ConcurrentHashMap<String, Account> loginMap) throws RemoteException {

		server.setLoginMap(loginMap);
	}

	@Override
	public ConcurrentHashMap<String, Account> getCookieMap() throws RemoteException { return server.getCookieMap(); }

	@Override
	public void setCookieMap(ConcurrentHashMap<String, Account> cookieMap) throws RemoteException {
		server.setCookieMap(cookieMap);
	}

	@Override
	public ConcurrentHashMap<String, Department> getDepartmentMap() throws RemoteException {
		return server.getDepartmentMap();
	}

	@Override
	public void setDepartmentMap(ConcurrentHashMap<String, Department> departmentMap) throws RemoteException { 

		server.setDepartmentMap(departmentMap);
	}

	@Override
	public ConcurrentHashMap<String, PlanFile> getPlanTemplateMap() throws RemoteException { 
																								
		return server.getPlanTemplateMap();
	}

	@Override
	public void setPlanTemplateMap(ConcurrentHashMap<String, PlanFile> planTemplateMap) throws RemoteException {
		// TODO Auto-generated method stub
		server.setPlanTemplateMap(planTemplateMap);
	}

	@Override
	public Collection<PlanFile> listPlanTemplates() throws RemoteException {
		return server.listPlanTemplates();
	}

	@Override
	public Collection<PlanFile> listPlans(String cookie) throws RemoteException {
		return server.listPlans(cookie);
	}

	
	
	/**
	 * @author liujiang
	 * Helper class to save the plan periodically
	 */
	private class pushPlan extends Thread {

		Server server;
		PlanFile planFile;
		String cookie;
		localServer local;
		public pushPlan(PlanFile planFile, String cookie) {
			this.planFile = planFile;
			this.cookie = cookie;
		}

		/**
		 * Recursive method to push the plan to the real server
		 * Also update the server in the parent controller
		 */
		private void push() {
			try {
				Thread.sleep(2000);
				connectToServer();
				server.savePlan(planFile, cookie);
			} 
			catch (ConnectException | NotBoundException e) {
				System.out.println(e.toString());
				push();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			} 
			catch (IllegalArgumentException e) {
				local.model.notifyMe(e.toString());
				
			
			}
			catch (RemoteException e) {
				local.model.notifyMe(e.toString());
			}
			
		}

		private void connectToServer() throws RemoteException, NotBoundException 
		{
			Registry registry = LocateRegistry.getRegistry(ip, port);
			server = (Server)registry.lookup("PlannerServer");
			server.savePlan(planFile, cookie);
			localServer.this.server = server;
			
		}
		@Override
		public void run() {
			System.out.println("IP "+ip+" port: "+port);
			push();

		}

	}

}
