package application;

import software_masters.model.PlannerModel;

public class Controller {
	private Main application;
	private PlannerModel model;

	/**
	 * Let controller to know view
	 * 
	 * @param application
	 */
	public void setApplication(Main application) {
		this.application = application;
		model = this.application.getModel();
	}

	/**
	 * @return the model
	 */
	public PlannerModel getModel() { return model; }

	/**
	 * @param model the model to set
	 */
	public void setModel(PlannerModel model) { this.model = model; }

	/**
	 * @return the application
	 */
	public Main getApplication() { return application; }

}
