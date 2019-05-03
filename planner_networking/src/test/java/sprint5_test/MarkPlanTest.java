package sprint5_test;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.function.IntPredicate;

import org.junit.jupiter.api.Test;

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
		identicalFileTest();
		sectionDifferentTest();
		branchDifferentTest();
	}

	private void identicalFileTest() 
	{
		//Create identical PlanFile
		doubleClickOn("#editYearField");
		write("2009");
		clickOn("#saveButton");
		
		
		clickOn("Compare Plans");
		ListView<Node> listViewA = find(listALabel);
		ListView<Node> listViewB = find(listBLabel);
		selectItem(listViewA, 0);
		selectItem(listViewB, 1);
		
		
		clickOn("Compare");
		
		TreeView<String> treeViewA = find(treeALabel);
		TreeView<String> treeViewB = find(treeBLabel);
		noMark(treeViewA.getRoot());
		noMark(treeViewB.getRoot());
		clickOn("Close");
		
	}
	
	private void noMark(TreeItem<String> item) 
	{
		assert !item.getValue().contains("+");
		for (TreeItem<String> item2 : item.getChildren())
		{
			noMark(item2);
		}
	}

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
		
		modifySection("Mission", "Mission", "Cool!");
		goToMarkPage();
		TreeView<String> treeViewA = find(treeALabel);
		TreeView<String> treeViewB = find(treeBLabel);
		boolean[] values = {true,false,false,false,false};
		checkUnMarkedBut(treeViewA, values);
		checkUnMarkedBut(treeViewB, values);
		clickOn("Close");
		
		modifySection("Mission", "Mission1", "");
		goToMarkPage();
		treeViewA = find(treeALabel);
		treeViewB = find(treeBLabel);
		checkUnMarkedBut(treeViewA, values);
		checkUnMarkedBut(treeViewB, values);
		clickOn("Close");
		
		
		boolean[] values2 = {false,true,false,false,false};
		modifySection("Mission1", "Mission", "");
		modifySection("Goal", "Goal1", "");
		goToMarkPage();
		treeViewA = find(treeALabel);
		treeViewB = find(treeBLabel);
		checkUnMarkedBut(treeViewA, values2);
		checkUnMarkedBut(treeViewB, values2);
		clickOn("Close");
		
		modifySection("Goal1", "Goal", "Cool!");
		goToMarkPage();
		treeViewA = find(treeALabel);
		treeViewB = find(treeBLabel);
		checkUnMarkedBut(treeViewA, values2);
		checkUnMarkedBut(treeViewB, values2);
		clickOn("Close");
		
		modifySection("Goal", "Goal", "");
	}
	
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
		
		clickOn("Save");
	}
	
	private void checkUnMarkedBut(TreeView<String> treeView,boolean[] values) 
	{
		for (int i = 0; i < values.length; i++) 
		{
			checkMarked(treeView, i, values[i]);
		}
	}
	private void checkMarked(TreeView<String> treeView,int index,boolean val) 
	{
		clickOn(treeView);
		if (index == 0) {
			type(KeyCode.DOWN);
			type(KeyCode.UP);
			type(KeyCode.ENTER);
		}
		else 
		{
			for (int i = 0; i < index; i++) 
			{
				type(KeyCode.DOWN);
			}
			type(KeyCode.ENTER);
		}
		
		String value = treeView.getSelectionModel().getSelectedItem().getValue();
		assert value.contains("+") == val;
	}
	
	
	
	private void branchDifferentTest() 
	{
		clickOn("Goal");
		clickOn("#addSectionButton");
		clickOn("Save");
		goToMarkPage();
		sleep(5000);
		boolean[] values = {false,false,false,false,false,true,true,true,true};
		TreeView<String> treeViewA = find(treeALabel);
		TreeView<String> treeViewB = find(treeBLabel);
		checkUnMarkedBut(treeViewA, values);
		checkUnMarkedBut(treeViewB, values);
	}
	
	private void goToPlanEditView() 
	{
		clickOn("Connect");
		clickOn("Log in");
		clickOn("2019");
	}
	

}
