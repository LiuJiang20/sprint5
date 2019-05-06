package sprint5_test;





import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import application.Main;
import javafx.scene.control.TextField;
import software_masters.gui_test.GuiTestBase;


public class AutoSaveTest extends GuiTestBase
{
	
	
	@BeforeAll
	public static void setUpBeforeClass() 
	{
		try {
			TestServer.testSpawn();
			ApplicationTest.launch(Main.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This closes the window and clears any action event after a test is executed.
	 * 
	 * @throws TimeoutException
	 */
	@AfterAll
	public static void afterAllTest() throws TimeoutException {
		FxToolkit.hideStage();
		FxToolkit.cleanupStages();
		try {
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1060);
			TestServerInterface server = (TestServerInterface) registry.lookup("PlannerServer");
			server.stop();
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void mainTest()
	{
		goToPlanEditView();
		autoSaveTest();
		saveFailTest();
		badFileTest();
		
	}
	
	
	
	


	private void autoSaveTest() 
	{
		//server is successfully shut down and then try to save the business plan
		clickOn("Mission");
		doubleClickOn("#dataField");
		write("Change has been made");
		endServer();
		clickOn("Save");
		

		//Restart the server
		TestServer.testSpawn();
		sleep(3000);
		clickOn("#logoutButton");
		clickOn("Log in");
		clickOn("2019");
		clickOn("Mission");
		FxAssert.verifyThat("#dataField", (TextField textField) -> 
							{ return textField.getText().equals("Change has been made"); });
		
		
	}
	
	private void saveFailTest() 
	{
		//server is successfully shut down and then try to save the business plan
		clickOn("Mission");
		TextField textField = find("#dataField");
		textField.setText("OK");
		endServer();
		clickOn("Save");
		clickOn("#logoutButton");
		sleep(10000);
		clickOn("Log in");
		// since server is not online, re login should fail
		checkPopupMsg("cannot connect to server");
		clickOn("OK");
		
		//restart the server, now we should see the changed saved correctly
		TestServer.testSpawn();
		sleep(2000);
		clickOn("Log in");
		clickOn("2019");
		clickOn("Mission");
		FxAssert.verifyThat("#dataField", (TextField textField1) -> 
		{ return textField1.getText().equals("OK"); });
	}
	
	
	
	private void badFileTest() 
	{ 
		doubleClickOn("#editYearField");
		write("2020");
		endServer();
		clickOn("Save");
		TestServer.testSpawn();
		sleep(5000);
		String message ="Business plan "+"\""+"2020"+"\""+"You saved before failed.\n"
				+"Here is the error message:\n"+ "java.lang.IllegalArgumentException: Not allowed to edit this plan";
		checkPopupMsg(message);
		clickOn("OK");
	}
	
	private void goToPlanEditView() 
	{
		clickOn("Connect");
		clickOn("Log in");
		clickOn("2019");
	}
	

	
	private void endServer() 
	{
		try {
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1060);
			TestServerInterface server = (TestServerInterface) registry.lookup("PlannerServer");
			server.stop();
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
