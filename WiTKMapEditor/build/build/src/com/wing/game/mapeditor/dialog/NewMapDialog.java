package com.wing.game.mapeditor.dialog;

import java.io.IOException;

import com.wing.game.mapeditor.property.TiledMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewMapDialog extends AnchorPane {
	@FXML
	private TextField tileWidthTf, tileHeightTf, mapWidthTf, mapHeightTf;

	private Stage newAlertDialog;
	private OnNewMapDialogActionListener onNewMapDialogActionListener;

	public NewMapDialog() {
		FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("NewMapDialog.fxml"));
		fXMLLoader.setRoot(NewMapDialog.this);
		fXMLLoader.setController(NewMapDialog.this);
		try {
			fXMLLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void showAlertDialog() {
		if (newAlertDialog == null) {
			newAlertDialog = new Stage(StageStyle.TRANSPARENT);
			newAlertDialog.setResizable(false);
			newAlertDialog.setScene(new Scene(this));
			newAlertDialog.show();
		} else {
			newAlertDialog.show();
		}
	}

	public void hideAlertDialog() {
		if (newAlertDialog != null) {
			newAlertDialog.hide();
		}
	}

	@FXML
	private void onNewMapAction(ActionEvent event) {
		int tileWidth = Integer.parseInt(tileWidthTf.getText());
		int tileHeight = Integer.parseInt(tileHeightTf.getText());
		int mapWidth = Integer.parseInt(mapWidthTf.getText());
		int mapHeight = Integer.parseInt(mapHeightTf.getText());
		TiledMap.getInstance().setMapProperty(tileWidth, tileHeight, mapWidth, mapHeight);
		if (onNewMapDialogActionListener != null) {
			onNewMapDialogActionListener.onNewMapOkAction();
		}
		hideAlertDialog();
	}

	@FXML
	private void onNewMapCancelAction(ActionEvent event) {
		if (onNewMapDialogActionListener != null) {
			onNewMapDialogActionListener.onNewMapCancelAction();
		}
		hideAlertDialog();
	}

	public OnNewMapDialogActionListener getOnNewMapDialogActionListener() {
		return onNewMapDialogActionListener;
	}

	public void setOnNewMapDialogActionListener(OnNewMapDialogActionListener onNewMapDialogActionListener) {
		this.onNewMapDialogActionListener = onNewMapDialogActionListener;
	}

	public interface OnNewMapDialogActionListener {
		public void onNewMapOkAction();

		public void onNewMapCancelAction();
	}
}
