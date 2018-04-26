package com.wing.game.mapeditor.dialog;

import javafx.beans.property.SimpleStringProperty;

public class PropertyData {
	private SimpleStringProperty key = new SimpleStringProperty("");
	private SimpleStringProperty value = new SimpleStringProperty("");
    public PropertyData(String key,String value){
    	setKey(key);
    	setValue(value);
    }
    
    public PropertyData(){}
	public String getKey() {
		return key.get();
	}

	public void setKey(String key) {
		this.key.set(key);
	}
	

	public String getValue() {
		return value.get();
	}

	public void setValue(String value) {
		this.value.set(value);
	}
}
