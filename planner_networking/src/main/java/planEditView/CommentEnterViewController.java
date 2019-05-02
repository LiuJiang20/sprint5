package planEditView;

import java.util.Optional;

import application.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import software_masters.planner_networking.Comment;

public class CommentEnterViewController extends Controller {

	@FXML Label username;
	@FXML TextField commentField;
	Comment comment;
	
	/**
	 * This method helps to set the username and comment 
	 * based on the comment that the user clicks
	 * @param comment
	 */
	public void initializeView(Comment comment) {
		this.comment = comment;
		this.username.setText("Username: " + comment.getUser());
		this.commentField.setText(comment.getComment());
	}
	
	@FXML
	public void save()
	{
		String commentString = commentField.getText();
		if (commentString == null) {
			commentString = "";
		}
		comment.setComment(commentString);
		getApplication().getCommentStage().close();
		
		
	}
	
	@FXML 
	public void cancel()
	{
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		String message = "Do you really want to leave the comment unsaved?";
		confirmation.setContentText(message);
		ButtonType okButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("No");
		confirmation.getButtonTypes().setAll(okButton,noButton);
		Optional<ButtonType> result = confirmation.showAndWait();
		
		
		if (result.get() == okButton) {
			getApplication().getCommentStage().close();
		}
	}
	
}
