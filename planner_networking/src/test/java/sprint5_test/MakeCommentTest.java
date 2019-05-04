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
	String deleteLabel = "#addComment";
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
	
	
	private void addCommentTest() 
	{
//		ListView<Comment> listview = new ListView<Comment>;
//		listview.getItems().get(0).get
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
		
		clickOn("#logoutButton");
		clickOn("Cancel");
		
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
		
		
		
		clickOn(addLabel);
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("")
					&&c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==2;
			});
		
		
		doubleClickOn("Mission");
		clickOn("Goal");
		clickOn(addLabel);
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c = listview1.getItems().get(0);
			return c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==1;
			});
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
		
		
		commentView.getSelectionModel().select(0);
		doubleClickOn(commentView);
		doubleClickOn("#commentField");
		write("Good comment.");
		clickOn("Cancel");
		clickOn("No");
		clickOn("Cancel");
		clickOn("Yes");
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("")
					&&c.getUser().equals("user") && c.getComment().equals("")
					&& listview1.getItems().size()==2;
			});
		
		
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
		clickOn("Mission");
		ListView<Comment> commentView = find(listviewLabel);
		commentView.getSelectionModel().select(0);
		sleep(2000);
		clickOn("#deleteComment");
		clickOn("No");
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			Comment c = listview1.getItems().get(1);
			return c0.getUser().equals("user") && c0.getComment().equals("Good comment.")
					&&c.getUser().equals("user") && c.getComment().equals("Good")
					&& listview1.getItems().size()==2;
			});
		
		commentView.getSelectionModel().select(0);
		clickOn("#deleteComment");
		clickOn("Yes");
		FxAssert.verifyThat(listviewLabel, (ListView<Comment> listview1)->
		{
			Comment c0 = listview1.getItems().get(0);
			return c0.getUser().equals("user") && c0.getComment().equals("Good")
					&& listview1.getItems().size()==1;
			});
		
		
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

