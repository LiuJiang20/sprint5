package software_masters.gui_test;

import static org.testfx.api.FxAssert.verifyThat;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author software masters
 */
class PlanSelectionTest extends GuiTestBase {

	/**
	 * 
	 * Main test which calls other tests in sequential order
	 */
	@Test
	void mainTest() {
		clickOn("Connect");
		clickOn("#loginButton");
		testDefaultValues();
		testLogout();
		testSelectPlanTemplate();
		testSelectEditablePlan();
		testSelectReadOnlyPlan();
	}

	/**
	 * Verifies labels are initialized with intended text.
	 */
	private void testDefaultValues() {

		verifyThat("#selectPlanLabel", (Label label) -> { return label.getText().equals("Select Plan"); });
		verifyThat("#planTemplatesLabel", (Label label) -> { return label.getText().equals("Plan Templates"); });
		verifyThat("#departmentPlansLabel", (Label label) -> { return label.getText().equals("Department Plans"); });
		FxAssert.verifyThat("#logoutButton", LabeledMatchers.hasText("Logout"));
	}

	/**
	 * Verifies that logout button works by clicking on button and checking for the
	 * presence of the username label in the login window.
	 */
	private void testLogout() {
		clickOn("#logoutButton");
		verifyThat("#usernameLabel", (Label label) -> { return label.getText().equals("Username"); });
		clickOn("#loginButton");
	}

	/**
	 * Verifies the user can click on a plan template from the plan templates list
	 * view and view the plan edit window.
	 */
	private void testSelectPlanTemplate() {

		clickOn((Node) find("VMOSA"));
		FxAssert.verifyThat("#saveButton", LabeledMatchers.hasText("Save"));
		clickOn("#backToPlansButton");

	}

	/**
	 * Verifies the user can click on an editable plan template from the plan
	 * templates list view and view the plan edit window.
	 */
	private void testSelectEditablePlan() {

		clickOn((Node) find("2019"));
		FxAssert.verifyThat("#saveButton", LabeledMatchers.hasText("Save"));
		clickOn("#backToPlansButton");

	}

	/**
	 * Verifies the user can click on an editable plan template from the plan
	 * templates list view and view the plan edit window.
	 */
	private void testSelectReadOnlyPlan() {

		clickOn((Node) find("2020 Read Only"));
		FxAssert.verifyThat("#readOnlyLabel", LabeledMatchers.hasText("Local View Only - Cannot Save Changes"));
		clickOn("#backToPlansButton");

	}

}
