package com.wing.game.mapeditor.layer;

public class MapTile {
    private String altasId = "";
    private int altasIndex = -1;
    
	public String getAltasId() {
		return altasId;
	}
	public void setAltasId(String altasId) {
		this.altasId = altasId;
	}
	public int getAltasIndex() {
		return altasIndex;
	}
	public void setAltasIndex(int altasIndex) {
		this.altasIndex = altasIndex;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[" + altasId + "," + altasIndex + "]");
		return sb.toString();
	}
	
	public void CovertFromString(String str){
		String[] data = str.replace("[", "").replace("]", "").replace(" ", "").split(",");
		//System.out.println(data.length + "," + str + "," + data[0] + "," + data[1]);
		altasId = data[0];
		altasIndex = Integer.parseInt(data[1]);
	}
}
