package sprint5_test;

import org.junit.jupiter.api.Test;

import software_masters.gui_test.GuiTestBase;
import software_masters.planner_networking.Comment;

import org.testfx.api.FxAssert;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
public class MakeCommentTest extends GuiTestBase
{
	String addLabel = "#addComment";
	String deleteLabel = "#deleteComment";
	String listviewLabel  = "#commentList";
	@Test
	public void mainTest()
	{
		goToPlanEditView();
		addCommentTest();
		enterCommentViewDefualtTest();
		editCommentTest();
		deleteCommentTest();
		
	}
	
	private void addCommentTest() {

		//add a comment on mission
		clickOn("Mission");
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview)->
		{return listview.getItems().size() == 0;});
		clickOn(addLabel);
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c = listview1.getItems().get(0);
			return c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==1;
					
			});
		
		//test if the isPushed flag is set to false
		clickOn("#logoutButton");
		clickOn("Cancel");
		
		//make user the comment is actually added
		clickOn("#saveButton");
		clickOn("#backToPlansButton");
		clickOn("2019");
		clickOn("Mission");
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c = listview1.getItems().get(0);
			return c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==1;
					
			});
		
		
		//add another comment on mission
		clickOn(addLabel);
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("")
					&&c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==2;
			});
		
		//add a comment on goal
		doubleClickOn("Mission");
		clickOn("Goal");
		clickOn(addLabel);
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c = listview1.getItems().get(0);
			return c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==1;
			});
		//add another comment on goal
		clickOn(addLabel);
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("")
					&&c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==2;
			});
		
		
	}
	
	private void enterCommentViewDefualtTest() 
	{		
		//check the window that allows user to edit comment is correct
		ListView<Comment> commentView = find(listviewLabel);
		commentView.getSelectionModel().select(0);
		doubleClickOn(commentView);		
		FxAssert.verifyThat("#usernameLabel", (Label label)->{return label.getText().equals("Username: user");});
		FxAssert.verifyThat("#commentLabel", (Label label)->{return label.getText().equals("Comment:");});
		FxAssert.verifyThat("#commentField", (TextField label)->{return label.getText().equals("");});
		clickOn("Cancel");
		clickOn("Yes");
	}
	
	private void editCommentTest() 
	{
		//make sure the comments are empty first
		clickOn("Mission");
		ListView<Comment> commentView = find(listviewLabel);
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("")
					&&c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==2;
			});
		
		// edit the first comment on mission
		commentView.getSelectionModel().select(0);
		doubleClickOn(commentView);
		doubleClickOn("#commentField");
		write("Good comment.");
		//test cancel button on enter window
		clickOn("Cancel");
		clickOn("No");
		clickOn("Cancel");
		clickOn("Yes");
		// the enter is canceled, check there is no change
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("")
					&&c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==2;
			});
		
		// edit same comment again
		commentView.getSelectionModel().select(0);
		doubleClickOn(commentView);
		doubleClickOn("#commentField");
		write("Good comment.");
		//save the comment edited
		clickOn("#commentSave");
		//check if the comment has the correct content
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("Good comment.")
					&&c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==2;
			});
		
		//test if the change has actually happened
		clickOn("#logoutButton");
		clickOn("Cancel");
		
		clickOn("#saveButton");
		clickOn("#backToPlansButton");
		clickOn("2019");
		clickOn("Mission");
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("Good comment.")
					&&c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==2;
			});
		
		//edit the second comment on mission
		commentView = find(listviewLabel);
		commentView.getSelectionModel().select(1);
		doubleClickOn(commentView);
		doubleClickOn("#commentField");
		write("Good");
		clickOn("#commentSave");
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("Good comment.")
					&&c.getUser().equals("user") && c.getComment().equals("Good")
					&& listview1.getItems().size()==2;
			});
		
		//edit first comments on goal
		doubleClickOn("Mission");
		clickOn("Goal");
		commentView.getSelectionModel().select(0);
		doubleClickOn(commentView);
		doubleClickOn("#commentField");
		write("Good comment.");
		clickOn("#commentSave");
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("Good comment.")
					&&c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==2;
			});
		
		
	}
	
	
	private void deleteCommentTest() 
	{
		//delete first comment on mission
		clickOn("Mission");
		ListView<Comment> commentView = find(listviewLabel);
		commentView.getSelectionModel().select(0);
		clickOn("#deleteComment");
		//cancel the operation
		clickOn("No");
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("Good comment.")
					&&c.getUser().equals("user") && c.getComment().equals("Good")
					&& listview1.getItems().size()==2;
			});
		//try to delete again
		commentView.getSelectionModel().select(0);
		clickOn("#deleteComment");
		//actually delete it
		clickOn("Yes");
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			return c0.getUser().equals("user") && c0.getComment().equals("Good")
					&& listview1.getItems().size()==1;
			});
		
		//test if the change has actually happened
		clickOn("#logoutButton");
		clickOn("Cancel");
		clickOn("#saveButton");
		clickOn("#backToPlansButton");
		clickOn("2019");
		clickOn("Mission");
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			return c0.getUser().equals("user") && c0.getComment().equals("Good")
					&& listview1.getItems().size()==1;
			});
		
		
		//try to delete the second comment
		clickOn("Mission");
		commentView = find(listviewLabel);
		commentView.getSelectionModel().select(0);
		clickOn("#deleteComment");
		clickOn("Yes");
		sleep(3000);
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			System.out.println(listview1.getItems().size());
			return listview1.getItems().size()==0;
			});
		//try to delete when there is no comment
		// the error message should pop up
		clickOn("#deleteComment");
		checkPopupMsg("No comment to remove!");
		clickOn("OK");
	}
	
	
	
	
	private void goToPlanEditView() 
	{
		clickOn("Connect");
		clickOn("Log in");
		clickOn("2019");
	}
	
	
}

