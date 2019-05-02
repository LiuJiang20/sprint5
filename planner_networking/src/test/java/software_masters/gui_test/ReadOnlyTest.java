package software_masters.gui_test;

import static org.testfx.api.FxAssert.verifyThat;

import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author software masters
 */
class ReadOnlyTest extends GuiTestBase {

	/**
	 * method that coordinates order of gui test.
	 * 
	 * @throws Exception
	 */
	@Test
	void mainTest() {
		clickOn("Connect");
		clickOn("#loginButton");
		clickOn((Node) find("2020 Read Only"));
		testDefaultValues();
		testNavigation();
		testCannotEdit();
		testBackToPlans();
		testLogout();
	}

	/**
	 * Verifies labels, buttons, and textfields are initialized with intended text.
	 */
	private void testDefaultValues() {

		verifyThat("#backToPlansButton", LabeledMatchers.hasText("Back to plans"));
		verifyThat("#readOnlyLabel", LabeledMatchers.hasText("Local View Only - Cannot Save Changes"));
		verifyThat("#logoutButton", LabeledMatchers.hasText("Log Out"));
		verifyThat("#yearLabel", LabeledMatchers.hasText("Year"));
		verifyThat("#yearField", (TextField textfield) -> { return textfield.getText().equals("2020"); });
		verifyThat("#titleField", (TextField textfield) -> { return textfield.getText().equals("Vision"); });
		verifyThat("#contentField",
				(TextField textfield) -> { return textfield.getText().equals("My Vision is to..."); });
	}

	/**
	 * Verifies that title field is repopulated with correct section name when the
	 * user clicks on a new section in the treeview.
	 */
	private void testNavigation() {
		clickOn((Node) find("Vision"));
		clickOn((Node) find("Vision"));
		clickOn((Node) find("Mission"));
		clickOn((Node) find("Mission"));
		verifyThat("#titleField", (TextField textfield) -> { return textfield.getText().equals("Mission"); });
		verifyThat("#contentField",
				(TextField textfield) -> { return textfield.getText().equals("My Mission is to..."); });
		clickOn((Node) find("Objective"));
		clickOn((Node) find("Objective"));
		verifyThat("#titleField", (TextField textfield) -> { return textfield.getText().equals("Objective"); });
		verifyThat("#contentField", (TextField textfield) -> { return textfield.getText().equals(""); });
		clickOn((Node) find("Strategy"));
		clickOn((Node) find("Strategy"));
		verifyThat("#titleField", (TextField textfield) -> { return textfield.getText().equals("Strategy"); });
		verifyThat("#contentField", (TextField textfield) -> { return textfield.getText().equals(""); });
		clickOn((Node) find("Action Plan"));
		clickOn((Node) find("Action Plan"));
		verifyThat("#titleField", (TextField textfield) -> { return textfield.getText().equals("Action Plan"); });
		clickOn((Node) find("Assessment"));
		clickOn((Node) find("Assessment"));
		verifyThat("#titleField", (TextField textfield) -> { return textfield.getText().equals("Assessment"); });
		verifyThat("#contentField", (TextField textfield) -> { return textfield.getText().equals(""); });
		clickOn((Node) find("Mission"));
		clickOn((Node) find("Mission"));
		verifyThat("#titleField", (TextField textfield) -> { return textfield.getText().equals("Mission"); });
		verifyThat("#contentField",
				(TextField textfield) -> { return textfield.getText().equals("My Mission is to..."); });
	}

	/**
	 * Verifies user cannot edit any of the text fields in read only mode.
	 */
	private void testCannotEdit() {
		clickOn("#yearField");
		write("SHOULD NOT BE ABLE TO CHANGE YEAR");
		verifyThat("#yearField", (TextField textfield) -> { return textfield.getText().equals("2020"); });

		clickOn("#titleField");
		write("SHOULD NOT BE ABLE TO CHANGE TITLE");
		verifyThat("#titleField", (TextField textfield) -> { return textfield.getText().equals("Mission"); });

		clickOn("#contentField");
		write("SHOULD NOT BE ABLE TO CHANGE CONTENT");
		verifyThat("#contentField",
				(TextField textfield) -> { return textfield.getText().equals("My Mission is to..."); });
	}

	/**
	 * Verifies the back to plans button works by checking for the presence of the
	 * select plan label in the plan selection window
	 */
	private void testBackToPlans() {

		clickOn("#backToPlansButton");
		verifyThat("#selectPlanLabel", (Label label) -> { return label.getText().equals("Select Plan"); });
		clickOn((Node) find("2020 Read Only"));
	}

	/**
	 * Verifies the logout button works by checking for the presence of the username
	 * label in the login window
	 */
	private void testLogout() {

		clickOn("#logoutButton");
		verifyThat("#usernameLabel", (Label label) -> { return label.getText().equals("Username"); });
	}

}
