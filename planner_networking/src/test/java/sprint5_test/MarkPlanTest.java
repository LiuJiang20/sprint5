package sprint5_test;



import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import software_masters.gui_test.GuiTestBase;
import software_masters.planner_networking.Node;


public class MarkPlanTest extends GuiTestBase {

	String listALabel = "#listA";
	String listBLabel = "#listB";
	String treeALabel = "#treeViewA";
	String treeBLabel = "#treeViewB";

	@Test
	public void mainTest() {
		goToPlanEditView();
		defaultValueTest();
		errorMessageOnSelection();
		identicalFileTest();
		sectionDifferentTest();
		branchDifferentTest();
	}
	private void defaultValueTest() 
	{
		clickOn("Compare Plans");
		String selectLabel = "#compare-selectLabel";
		FxAssert.verifyThat(selectLabel, (Label select)-> 
		{return select.getText().equals("Please select one business plan from each list");});
		String labelA = "#compare-labelA";
		String labelB = "#compare-labelB";
		
		FxAssert.verifyThat(labelA, (Label label)-> 
		{return label.getText().equals("Business plan A");});
		
		FxAssert.verifyThat(labelB, (Label label)-> 
		{return label.getText().equals("Business plan B");});
		
		ListView<Node> listViewA = find(listALabel);
		ListView<Node> listViewB = find(listBLabel);
		selectItem(listViewA, 0);
		selectItem(listViewB, 0);
		clickOn("Compare");
		String markLael = "#mark-label";
		FxAssert.verifyThat(markLael, (Label label)-> 
		{return label.getText().equals("Result of comparison is displayed below.The difference has been marked by plus sign");});
		
		labelA = "#mark-labelA";
		labelB = "#mark-labelB";
		
		FxAssert.verifyThat(labelA, (Label label)-> 
		{return label.getText().equals("2019");});
		
		FxAssert.verifyThat(labelB, (Label label)-> 
		{return label.getText().equals("2019");});
		clickOn("Close");
		
	}
	
	private void errorMessageOnSelection() 
	{
		clickOn("Compare Plans");
		ListView<Node> listViewA = find(listALabel);
		ListView<Node> listViewB = find(listBLabel);
		
		selectItem(listViewA, 0);
		clickOn("Compare");
		clickOn("OK");
		
		listViewA.getSelectionModel().clearSelection();
		selectItem(listViewB, 0);
		clickOn("Compare");
		clickOn("OK");
		
		selectItem(listViewA, 0);
		clickOn("Compare");
		clickOn("Close");
		
	}
	private void identicalFileTest() 
	{
		//Create identical PlanFile
		doubleClickOn("#editYearField");
		write("2009");
		clickOn("#saveButton");
		
		//compare two identical business plans
		clickOn("Compare Plans");
		ListView<Node> listViewA = find(listALabel);
		ListView<Node> listViewB = find(listBLabel);
		selectItem(listViewA, 0);
		selectItem(listViewB, 1);
		
		
		clickOn("Compare");
		
		TreeView<String> treeViewA = find(treeALabel);
		TreeView<String> treeViewB = find(treeBLabel);
		clickOn("Mission");
		noMark(treeViewA.getRoot());
		noMark(treeViewB.getRoot());
		clickOn("Close");
		
	}
	
	/** check if the tree has no mark, which means no difference between two plans
	 * @param item
	 */
	private void noMark(TreeItem<String> item) 
	{
		assert !item.getValue().contains("+");
		for (TreeItem<String> item2 : item.getChildren())
		{
			noMark(item2);
		}
	}

	/**
	 * Helper method to select business plans to compare
	 * @param listView
	 * @param index
	 */
	private void selectItem(ListView<Node> listView, int index) 
	{
		clickOn(listView);
		for(int i=0; i<=index; i++)
		{
			type(KeyCode.DOWN);
		}
		type(KeyCode.ENTER);
	}
	
	
	private void sectionDifferentTest() 
	{
		String[] sequence = {"Mission","Goal","Learning Objective","Assessment Process","Results"};
		expandTree(sequence);
		//change one plan's mission content
		modifySection("Mission", "Mission", "Cool!");
		goToMarkPage();
		TreeView<String> treeViewA = find(treeALabel);
		TreeView<String> treeViewB = find(treeBLabel);
		boolean[] values = {true,false,false,false,false};
		checkUnMarkedBut(treeViewA, values);
		checkUnMarkedBut(treeViewB, values);
		clickOn("Close");
		
		//change one plan's mission title
		modifySection("Mission", "Mission1", "");
		goToMarkPage();
		treeViewA = find(treeALabel);
		treeViewB = find(treeBLabel);
		checkUnMarkedBut(treeViewA, values);
		checkUnMarkedBut(treeViewB, values);
		clickOn("Close");
		
		//change one plan's goal title
		boolean[] values2 = {false,true,false,false,false};
		modifySection("Mission1", "Mission", "");
		modifySection("Goal", "Goal1", "");
		goToMarkPage();
		treeViewA = find(treeALabel);
		treeViewB = find(treeBLabel);
		checkUnMarkedBut(treeViewA, values2);
		checkUnMarkedBut(treeViewB, values2);
		clickOn("Close");
		
		//change one plan's goal content
		modifySection("Goal1", "Goal", "Cool!");
		goToMarkPage();
		treeViewA = find(treeALabel);
		treeViewB = find(treeBLabel);
		checkUnMarkedBut(treeViewA, values2);
		checkUnMarkedBut(treeViewB, values2);
		clickOn("Close");
		
		modifySection("Goal", "Goal", "");
	}
	
	/**
	 * Helper method to go to the window that show the differences between two plans
	 */
	private void goToMarkPage() {
		clickOn("Compare Plans");
		ListView<Node> listViewA = find(listALabel);
		ListView<Node> listViewB = find(listBLabel);
		selectItem(listViewA, 0);
		selectItem(listViewB, 1);
		
		
		clickOn("Compare");
	}
	
	private void expandTree(String[] clickSequence) 
	{
		for (String string : clickSequence) 
		{
			doubleClickOn(string);
		}
	}
	
	
	/**
	 * Helper method to modify the section
	 * @param section
	 * @param title
	 * @param content
	 */
	private void modifySection(String section, String title,String content) 
	{
		clickOn(section);
		if (title != null) 
		{
			doubleClickOn("#nameField");
			write(title);
		}
		
		if (content != null)
		{
			doubleClickOn("#dataField");
			if (content.equals("")) {
				write("q");
				clickOn("#dataField");
				type(KeyCode.DELETE);
				
			}
			else
				write(content);
		}
		
		clickOn("#saveButton");
	}
	 
	/**
	 * Helper method to check the marked state of each item in the tree
	 * Values contains the marked state of the item should be false or true
	 * @param treeView
	 * @param values
	 */
	private void checkUnMarkedBut(TreeView<String> treeView,boolean[] values) 
	{
		for (int i = 0; i < values.length; i++) 
		{
			checkMarked(treeView, i, values[i]);
		}
	}
	
	/**
	 * check if the item at index row has the correct marked state
	 * @param treeView
	 * @param index
	 * @param val
	 */
	private void checkMarked(TreeView<String> treeView,int index,boolean val) 
	{
	
		String value = treeView.getTreeItem(index).getValue();
		assert value.contains("+") == val;
	}
	
	
	
	private void branchDifferentTest() 
	{
		//add an entire branch on one business plan
		clickOn("Goal");
		clickOn("#addSectionButton");
		clickOn("Save");
		goToMarkPage();
		boolean[] values = {false,false,false,false,false,true,true,true,true};
		boolean[] values2 = {false,false,false,false,false};
		TreeView<String> treeViewA = find(treeALabel);
		TreeView<String> treeViewB = find(treeBLabel);
		checkUnMarkedBut(treeViewA, values);
		checkUnMarkedBut(treeViewB, values2);
	}
	
	private void goToPlanEditView() 
	{
		clickOn("Connect");
		clickOn("Log in");
		clickOn("2019");
	}
	
	

}
