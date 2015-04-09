package com.wing.game.mapeditor.dialog;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertDialog extends AnchorPane {
	@FXML
	private TextArea messageLabel;

	private static AlertDialog wiAlertDialog;
	private static Stage newAlertDialog;

	private AlertDialog(String message, String title) {
		FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("AlertDialog.fxml"));
		fXMLLoader.setRoot(AlertDialog.this);
		fXMLLoader.setController(AlertDialog.this);
		try {
			fXMLLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		messageLabel.setText(message);
	}

	public static void showAlertDialog(String message, String title) {
			newAlertDialog = new Stage(StageStyle.DECORATED);
			newAlertDialog.setResizable(false);
			wiAlertDialog = new AlertDialog(message, title);
			newAlertDialog.setTitle("提示");
			newAlertDialog.setScene(new Scene(wiAlertDialog));
			newAlertDialog.show();
	}

	public static void showAlertDialog(String message) {
		showAlertDialog(message, null);
	}

	public static void hideAlertDialog() {
		if (newAlertDialog != null) {
			newAlertDialog.hide();
		}
	}

	@FXML
	private void onAlertOkAction(ActionEvent event) {
		AlertDialog.hideAlertDialog();
	}
}
