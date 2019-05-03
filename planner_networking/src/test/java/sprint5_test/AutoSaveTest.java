package sprint5_test;



import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.api.FxAssert;
import application.Main;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import software_masters.gui_test.GuiTestBase;
import software_masters.planner_networking.ServerImplementation;

public class AutoSaveTest extends GuiTestBase
{
	static Thread serverThread;
	
	@BeforeAll
	public static void setUpBeforeClass() 
	{
		startServer();
		try {
			ApplicationTest.launch(Main.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception { stage.show(); }

	@Test
	public void mainTest()
	{
		goToPlanEditView();
		autoSaveTest();
	}
	
	
	
	
	
	private void autoSaveTest() 
	{
		clickOn("Mission");
		doubleClickOn("#dataField");
		write("Change has been made");
		endServer();
		try {
			serverThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clickOn("Save");
		clickOn("#logoutButton");
		clickOn("Log in");
		clickOn("2019");
		clickOn("Mission");
		FxAssert.verifyThat("#dataField", (TextField textField) -> 
							{ return textField.getText().equals("Change has been made"); });
		
		
	}
	
	
	private void goToPlanEditView() 
	{
		clickOn("Connect");
		clickOn("Log in");
		clickOn("2019");
	}
	
	private static void startServer() 
	{
		serverThread = new Thread(()-> 
		{
			ServerImplementation.testSpawn();
			
		});
		serverThread.start();
	}
	
	private void endServer() 
	{
		serverThread.interrupt();
	}
	
}