package software_masters.gui_test;

import static org.testfx.api.FxAssert.verifyThat;

import org.junit.jupiter.api.Test;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author software masters
 */
class LoginTest extends GuiTestBase {
	/**
	 * Main test which calls other tests in sequential order
	 */
	@Test
	void mainTest() {
		clickOn("Connect");
		testDefaultValues();
		testInvalidUser();
		testInvalidPassword();
		testInvalidUsernameAndPassword();
		testValidLogin();
	}

	/**
	 * Verifies that labels are populated with the intended text
	 */
	private void testDefaultValues() {
		verifyThat("#usernameLabel", (Label label) -> { return label.getText().equals("Username"); });
		verifyThat("#passwordLabel", (Label label) -> { return label.getText().equals("Password"); });
	}

	/**
	 * Verifies that the plan selection window is not displayed when invalid
	 * username is entered, by checking that the username label is still present
	 */
	private void testInvalidUser() {
		clickOn("#usernameField");
		write("INVALID USERNAME");
		clickOn("#loginButton");
		checkPopupMsg("invalid credentials");
		clickOn("OK");
		TextField textfield = (TextField) find("#usernameField");
		textfield.setText("user");

	}

	/**
	 * Verifies that the plan selection window is not displayed when invalid
	 * password is entered, by checking that the username label is still present
	 */
	private void testInvalidPassword() {
		clickOn("#passwordField");
		write("INVALID PASSWORD");
		clickOn("#loginButton");
		checkPopupMsg("invalid credentials");
		clickOn("OK");
		TextField textfield = (TextField) find("#passwordField");
		textfield.setText("user");

	}

	/**
	 * Verifies that the plan selection window is not displayed when invalid
	 * username and password is entered, by checking that the username label is
	 * still present
	 */
	private void testInvalidUsernameAndPassword() {
		clickOn("#passwordField");
		write("INVALID PASSWORD");
		clickOn("#usernameField");
		write("INVALID USERNAME");
		clickOn("#loginButton");
		checkPopupMsg("invalid credentials");
		clickOn("OK");
		TextField textfield = (TextField) find("#passwordField");
		textfield.setText("user");
		textfield = (TextField) find("#usernameField");
		textfield.setText("user");

	}

	/**
	 * Verifies that the plan selection window is displayed when a valid username
	 * and password is entered, by checking that the select plan label is present.
	 */
	private void testValidLogin() {
		clickOn("#loginButton");
		verifyThat("#selectPlanLabel", (Label label) -> { return label.getText().equals("Select Plan"); });

	}

}
