package serverConnectionView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerConnectionViewController {
	Main application;
	@FXML
	TextField ipAddress;
	@FXML
	TextField port;

	@FXML
	public void connect() {
		try {
			application.getModel().connectToServer(ipAddress.getText(), Integer.parseInt(port.getText()));
			application.showLoginView();
		} catch (NumberFormatException e) {
			application.sendError("cannot connect to server");
		} catch (RemoteException e) {
			application.sendError("cannot connect to server");
		} catch (NotBoundException e) {
			application.sendError("cannot connect to server");
		}
	}

	public void setApplication(Main application) { this.application = application; }

}
