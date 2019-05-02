package software_masters.gui_test;

import org.junit.jupiter.api.Test;

import static org.testfx.api.FxAssert.verifyThat;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * @author software masters
 */
class ConnectServerTest extends GuiTestBase {

	String IPLABEL_ID = "#ipLabel";
	String PORTLABEL_ID = "#portLabel";
	String IPFIELD_ID = "#ipField";
	String PORTFIELD_ID = "#portField";
	String CONNECTBUTTON_ID = "#connectButton";

	/**
	 * Main test running all subtests, ensures they run sequentially
	 */
	@Test
	public void mainTest() {
		defaultValueTest();
		invalidPortTest();
		invalidIPTest();
		invalidIpAndPortTest();
		validConnectTest();

	}

	/**
	 * Ensures all labels and text fields have the intended text values
	 */
	public void defaultValueTest() {
		verifyThat(IPFIELD_ID, (TextField field) -> { return field.getText().equals("127.0.0.1"); });
		verifyThat(PORTFIELD_ID, (TextField field) -> { return field.getText().equals("1060"); });
		verify(IPLABEL_ID, "IP Address:");
		verify(PORTLABEL_ID, "Port:");

	}

	String userNameLabel = "#usernameLabel";

	/**
	 * Tests that login window displayed when user enters valid IP address and port,
	 * by checking that the login wondow's username label is present
	 * 
	 * @throws Exception
	 */
	public void validConnectTest() {
		clickOn(CONNECTBUTTON_ID);
		verifyThat(userNameLabel, (Label label) -> { return label.getText().equals("Username"); });

	}

	/**
	 * Verifies that login window is not displayed when user enters invalid IP, by
	 * checking that the connection window's IP address label is still present.
	 */
	public void invalidIPTest() {
		clickOn(IPFIELD_ID);
		write("INVALID IP");
		clickOn(CONNECTBUTTON_ID);
		checkPopupMsg("cannot connect to server");
		clickOn("OK");
		TextField textfield = (TextField) find(IPFIELD_ID);
		textfield.setText("127.0.0.1");

	}

	/**
	 * Verifies that login window is not displayed when user enters invalid port, by
	 * checking that the connection window's IP address label is still present.
	 */
	public void invalidPortTest() {
		clickOn(PORTFIELD_ID);
		write("INVALID PORT");
		clickOn(CONNECTBUTTON_ID);
		checkPopupMsg("cannot connect to server");
		clickOn("OK");
		TextField textfield = (TextField) find(PORTFIELD_ID);
		textfield.setText("1060");

	}

	/**
	 * Verifies that login window is not displayed when user enters an invalid port
	 * and IP, by checking that the connection window's IP address label is still
	 * present.
	 */
	public void invalidIpAndPortTest() {
		clickOn(PORTFIELD_ID);
		write("INVALID PORT");
		clickOn(IPFIELD_ID);
		write("INVALID IP");
		clickOn(CONNECTBUTTON_ID);
		checkPopupMsg("cannot connect to server");
		clickOn("OK");
		TextField textfield = (TextField) find(PORTFIELD_ID);
		textfield.setText("1060");
		textfield = (TextField) find(IPFIELD_ID);
		textfield.setText("127.0.0.1");

	}

}
