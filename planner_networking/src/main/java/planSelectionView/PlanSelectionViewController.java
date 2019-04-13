package planSelectionView;

import java.rmi.RemoteException;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import software_masters.model.PlannerModel;
import software_masters.planner_networking.PlanFile;

/**
 * @author lee.kendall
 *
 *MVC Controller for the plan selection view
 *
 */
public class PlanSelectionViewController
{

    @FXML
    private ListView<PlanFile> planTemplateList;//=genTemplateList();

    @FXML
    private ListView<PlanFile> departmentPlanList;//=genPlansList();
    
    private Main app;

    /**
     * Resets client's cookie and planFile, displays the login window
     * 
     * @param event Action
     */
    @FXML
    public void Logout(ActionEvent event) 
    {
    	app.getModel().setCookie(null);
    	app.getModel().setCurrNode(null);
    	app.getModel().setCurrPlanFile(null);
    	app.showLoginView();
    }
    
    /**
     * Allows controller to access showPlanEditView, showPlanReadOnlyView,
     *  and the showLoginView methods in the main application
     * @param app main application
     */
    public void setApplication(Main app)
    {
    	this.app = app;
    }
    
    /**
     * This method generates a list view of developer templates
     * @return
     */
    public void genTemplateList(){
    	ObservableList<PlanFile> items=null;
		try {
			items = FXCollections.observableArrayList(this.app.getModel().listPlanTemplates());
		} catch (RemoteException e) {
			this.app.showConnectToServer();
		}
    	planTemplateList.setItems(items);
    }
    
    /**
     * This method generates a list view of plans associated with client department
     * @return
     */
    public void genPlansList(){
    	ObservableList<PlanFile> items=null;
		try {
			items = FXCollections.observableArrayList(this.app.getModel().listPlans());
		} catch (RemoteException e) {
			this.app.showConnectToServer();
		}
    	departmentPlanList.setItems(items);
    	
    }

}