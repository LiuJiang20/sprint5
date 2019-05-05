package planCompareView;

import java.rmi.RemoteException;

import application.Controller;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import software_masters.planner_networking.PlanFile;

public class compareSelectionViewController extends Controller {

	@FXML
	ListView<PlanFile> listA;
	@FXML
	ListView<PlanFile> listB;
	@FXML
	Button compareButton;

	@Override
	public void setApplication(Main application) {
		super.setApplication(application);
		setListView();

	}

	/**
	 * Change view to result page
	 */
	@FXML
	public void compareTwoPlans() {
		PlanFile fileA = listA.getSelectionModel().getSelectedItem();
		// make sure exactly two plans are selected
		if (fileA == null) {
			getApplication().sendError("You haven't select file from list A!");
			return;
		}

		PlanFile fileB = listB.getSelectionModel().getSelectedItem();
		if (fileB == null) {
			getApplication().sendError("You haven't select file from list B!");
			return;
		}

		try {
			fileA = getModel().returnPlanFile(fileA.getYear());
			fileB = getModel().returnPlanFile(fileB.getYear());
		} catch (RemoteException e) {
			getApplication().sendError("Fail to retreive business plans from server");
		} catch (IllegalArgumentException e) {
			getApplication().sendError(e.toString());
		}
		getModel().markPlan(fileA, fileB);
		getApplication().showCompareMarkView(fileA, fileB);
	}

	private void setListView() {
		try {
			ObservableList<PlanFile> item = FXCollections.observableArrayList(getModel().listPlans());
			listA.setItems(item);
			listB.setItems(item);
			listA.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			listB.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			getApplication().getStage().close();
			getApplication().sendError("Fail to list the business plans");
		}
	}
}
