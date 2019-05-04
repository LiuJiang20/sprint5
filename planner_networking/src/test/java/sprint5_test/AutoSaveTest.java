package sprint5_test;





import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.testfx.api.FxAssert;
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
	@Test
	public void mainTest()
	{
		goToPlanEditView();
		autoSaveTest();
		saveFailTest();

		
	}
	
	
	
	
	
	private void autoSaveTest() 
	{
		//server is successfully shut down
		clickOn("Mission");
		doubleClickOn("#dataField");
		write("Change has been made");
		endServer();
		clickOn("Save");
		

		//Restart the server
		TestServer.testSpawn();
		sleep(10000);
		clickOn("#logoutButton");
		clickOn("Log in");
		clickOn("2019");
		clickOn("Mission");
		FxAssert.verifyThat("#dataField", (TextField textField) -> 
							{ return textField.getText().equals("Change has been made"); });
		
		
	}
	
	private void saveFailTest() 
	{
		clickOn("Mission");
		TextField textField = find("#dataField");
		textField.setText("OK");
		endServer();
		clickOn("Save");
		clickOn("#logoutButton");
		sleep(10000);
		clickOn("Log in");
		checkPopupMsg("cannot connect to server");
		clickOn("OK");
		
		TestServer.testSpawn();
		sleep(5000);
		clickOn("Log in");
		clickOn("2019");
		clickOn("Mission");
		FxAssert.verifyThat("#dataField", (TextField textField1) -> 
		{ return textField1.getText().equals("OK"); });
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
