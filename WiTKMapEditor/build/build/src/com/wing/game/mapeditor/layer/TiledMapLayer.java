package com.wing.game.mapeditor.layer;

import javafx.beans.property.SimpleStringProperty;

import com.wing.game.mapeditor.property.TiledMap;

public class TiledMapLayer {
	private MapTile[][] mapTiles;
	private SimpleStringProperty layerName = new SimpleStringProperty();
    private boolean isVisible = true;
    private boolean isCollider = false;
    private double alpha = 1.0d;
	public TiledMapLayer(int width, int height) {
		mapTiles = new MapTile[height][width];
		for(int i = 0;i < height;i ++){
			for(int j = 0;j < width; j++){
				mapTiles[i][j] = new MapTile();
			}
		}
	}

	public TiledMapLayer() {
		this(TiledMap.getInstance().getMapWidth(), TiledMap.getInstance().getMapHeight());
	}

	public void setMapTile(int x, int y, MapTile mapTile) {
		mapTiles[y][x] = mapTile;
	}

	public void setMapTile(MapTile[][] mapTiles) {
		this.mapTiles = mapTiles;
	}

	public MapTile[][] getMapTiles() {
		return mapTiles;
	}

	public SimpleStringProperty LayerNameProperty() {
		return layerName;
	}

	public String getLayerName() {
		return layerName.get();
	}

	public void setLayerName(String name) {
		layerName.set(name);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < mapTiles.length; y++) {
			for (int x = 0; x < mapTiles[0].length; x++) {
				if (y == mapTiles.length - 1 && x == mapTiles[0].length - 1) {
					sb.append(mapTiles[y][x].toString());
				} else {
					sb.append(mapTiles[y][x].toString() + "T");
				}
			}
		}
		return sb.toString();
	}

	public void ConvertFromString(String str) {
		String[] data = str.split("T");
		System.out.println("Data的长度:" + data.length);
		int mapWidth = TiledMap.getInstance().getMapWidth();
		int mapHeight = TiledMap.getInstance().getMapHeight();
		if (mapTiles == null) {
			mapTiles = new MapTile[mapHeight][mapWidth];
		}
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
                mapTiles[y][x] = new MapTile();
                mapTiles[y][x].CovertFromString(data[y * mapWidth + x]);
			}
		}
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public boolean isCollider() {
		return isCollider;
	}

	public void setCollider(boolean isCollider) {
		this.isCollider = isCollider;
	}
}
