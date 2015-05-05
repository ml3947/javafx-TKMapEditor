package org.wing.jfx.game.core.map.tk;

import javafx.beans.property.SimpleStringProperty;

public class WTKLayer {
	private WTKMapTile[][] mapTiles;
	private SimpleStringProperty layerName = new SimpleStringProperty();
    private boolean isVisible = true;
    private double alpha = 1.0d;
	public WTKLayer(int width, int height) {
		mapTiles = new WTKMapTile[height][width];
		for(int i = 0;i < height;i ++){
			for(int j = 0;j < width; j++){
				mapTiles[i][j] = new WTKMapTile();
			}
		}
	}

	public WTKLayer() {
		this(WTKMap.getInstance().getMapWidth(), WTKMap.getInstance().getMapHeight());
	}

	public void setMapTile(int x, int y, WTKMapTile mapTile) {
		mapTiles[y][x] = mapTile;
	}

	public void setMapTile(WTKMapTile[][] mapTiles) {
		this.mapTiles = mapTiles;
	}

	public WTKMapTile[][] getMapTiles() {
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
				if (y == mapTiles.length - 1 && x == mapTiles.length - 1) {
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
		int mapWidth = WTKMap.getInstance().getMapWidth();
		int mapHeight = WTKMap.getInstance().getMapHeight();
		if (mapTiles == null) {
			mapTiles = new WTKMapTile[mapHeight][mapWidth];
		}
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
                mapTiles[y][x] = new WTKMapTile();
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
}
