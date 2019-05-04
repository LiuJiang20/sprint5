package software_masters.model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import software_masters.planner_networking.Client;
import software_masters.planner_networking.Node;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.Server;

public class PlannerModel extends Client {

	/**
	 * 
	 */
	public PlannerModel() { this.setServer(new localServer(this)); }

	/**
	 * @param server
	 */
	public PlannerModel(Server server) { super(server); }

	@Override
	public void connectToServer(String ip, int port) throws RemoteException, NotBoundException {
		((localServer) this.getServer()).connectToServer(ip,port);

	}

	public void notifyMe(String message) {
		// TODO finish the method.

	}

	/**
	 * Mark two business plan
	 * 
	 * @param a
	 * @param b
	 */
	public void markPlan(PlanFile a, PlanFile b) {
		Node root1 = a.getPlan().getRoot();
		Node root2 = b.getPlan().getRoot();
		markNode(root1, root2);
	}

	/**
	 * Method that recursively marks nodes
	 * 
	 * @param root1
	 * @param root2
	 */
	private void markNode(Node root1, Node root2) {
		// mark the current node
		if (!root1.getName().equals(root2.getName()) || !root1.getData().equals(root2.getData())) {
			root1.setMarked(true);
			root2.setMarked(true);
		}
		ArrayList<Node> children1 = root1.getChildren();
		ArrayList<Node> children2 = root2.getChildren();

		/*
		 * swap two list if the first list is larger than the second this ensures that
		 * children1 is at most as large as children2
		 */
		if (children1.size() > children2.size()) {
			ArrayList<Node> temp = children1;
			children1 = children2;
			children2 = temp;
		}

		/*
		 * Mark the entire branches that don't exist in the other list
		 */
		if (children2.size() > children1.size()) {

			for (int i = children1.size(); i < children2.size(); i++) {
				markAll(children2.get(i));

			}
		}

		/*
		 * Recursively mark child nodes
		 */
		for (int i = 0; i < children1.size(); i++) { markNode(children1.get(i), children2.get(i)); }

	}

	/**
	 * Helper method to mark entire branch starting from the root
	 * 
	 * @param root
	 */
	private void markAll(Node root) {
		root.setMarked(true);
		for (Node node : root.getChildren()) { markAll(node); }

	}

	/**
	 * 
	 * Restore the business plan to unmarked condition
	 * 
	 * @param planFile the plan file
	 */
	public void clearPlan(PlanFile planFile) {

		clearNode(planFile.getPlan().getRoot());
	}

	/**
	 * Helper method that recursively set node's marked attribute to false
	 * 
	 * @param root
	 */
	private void clearNode(Node root) {
		root.setMarked(false);
		for (Node node : root.getChildren()) {
			clearNode(node);

		}
	}

}
