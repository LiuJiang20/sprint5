package planCompareView;

import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;

import java.awt.Color;
import java.io.Closeable;
import java.nio.charset.StandardCharsets;

import application.Controller;
import application.Main;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import software_masters.planner_networking.Node;
import software_masters.planner_networking.PlanFile;

public class compareMarkViewController extends Controller {
	PlanFile fileA;
	PlanFile fileB;

	@FXML
	public TreeView<String> treeViewA;
	@FXML
	public TreeView<String> treeViewB;
	@FXML
	public Button exitButton;
	@FXML
	public Label labelA;
	@FXML
	public Label labelB;

	public void setMarkedFiles(PlanFile a, PlanFile b) {
		fileA = a;
		fileB = b;
	}

	/**
	 *  Close the window
	 */
	@FXML
	public void close() {
		System.out.println("Closing the stage");
		clearMark(fileA.getPlan().getRoot());
		clearMark(fileB.getPlan().getRoot());
		getApplication().getStage().close();

	}

	/**
	 * Set all the marked values to false
	 * @param root
	 */
	private void clearMark(Node root) {
		root.setMarked(false);
		for (Node child : root.getChildren()) {
			clearMark(child);

		}

	}

	@Override
	public void setApplication(Main application) {
		super.setApplication(application);
		System.out.println("Treeview: " + treeViewA + " " + treeViewB);
		System.out.println(labelA + "  " + labelB);
		setTreeView();
		setFilename();
	}

	/**
	 * Reset the labels that show two file names
	 */
	private void setFilename() {
		labelA.setText(fileA.toString());
		labelB.setText(fileB.toString());
	}

	/**
	 *  Set the two treeView that show the differences between two plans
	 */
	private void setTreeView() {
		treeViewA.setRoot(convertTree(fileA.getPlan().getRoot()));
		treeViewB.setRoot(convertTree(fileB.getPlan().getRoot()));
		expandTreeView(treeViewA.getRoot());
		expandTreeView(treeViewB.getRoot());
	}

	/**
	 * @param root build the treeview start from root node of business plan
	 * @return
	 */
	private TreeItem<String> convertTree(Node root) {
		String text = root.toString();
		if (root.isMarked()) { text += " " + '+' ; }

		TreeItem<String> newRoot = new TreeItem<String>(text);

		for (int i = 0; i < root.getChildren().size(); i++) {
			newRoot.getChildren().add(convertTree(root.getChildren().get(i)));
		}
		return newRoot;
	}

	private void expandTreeView(TreeItem<String> root) {
		root.setExpanded(true);
		for (TreeItem<String> child : root.getChildren()) { expandTreeView(child); }

	}

}
