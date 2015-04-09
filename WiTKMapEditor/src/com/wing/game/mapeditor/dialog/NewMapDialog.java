package com.wing.game.mapeditor.dialog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.wing.game.mapeditor.property.TiledMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewMapDialog extends AnchorPane implements Initializable {
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * tileWidthTf.lengthProperty().addListener(new
		 * ChangeListener<Number>(){
		 * 
		 * @Override public void changed(ObservableValue<? extends Number>
		 * observable, Number oldValue, Number newValue) {
		 * if(newValue.intValue() > oldValue.intValue()){ char ch =
		 * tileWidthTf.getText().charAt(oldValue.intValue()); //Check if the new
		 * character is the number or other's if(!(ch >= '0' && ch <= '9' )){
		 * tileWidthTf
		 * .setText(tileWidthTf.getText().substring(0,tileWidthTf.getText
		 * ().length()-1)); } } } });
		 */
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
		String tileWidthStr = tileWidthTf.getText();
		String tileHeightStr = tileHeightTf.getText();
		String mapWidthStr = mapWidthTf.getText();
		String mapHeightStr = mapHeightTf.getText();
		if (!tileWidthStr.trim().equals("") && !tileHeightStr.trim().equals("") && !mapWidthStr.trim().equals("")
				&& !mapHeightStr.trim().equals("")) {
			try {
				int tileWidth = Integer.parseInt(tileWidthStr);
				int tileHeight = Integer.parseInt(tileHeightStr);
				int mapWidth = Integer.parseInt(mapWidthStr);
				int mapHeight = Integer.parseInt(mapHeightStr);
				TiledMap.getInstance().setMapProperty(tileWidth, tileHeight, mapWidth, mapHeight);
				if (onNewMapDialogActionListener != null) {
					onNewMapDialogActionListener.onNewMapOkAction();
				}
				hideAlertDialog();
			} catch (NumberFormatException e) {
                //e.printStackTrace();
			}
		}
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
