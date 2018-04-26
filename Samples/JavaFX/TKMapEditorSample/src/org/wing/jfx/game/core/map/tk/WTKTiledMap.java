package org.wing.jfx.game.core.map.tk;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.wing.jfx.game.core.map.tk.WAltasResourceManager.AltasResource;
import org.wing.jfx.game.core.map.tk.XMLElements;
import org.wing.jfx.game.core.tools.WLog;

public class WTKTiledMap {
	private List<WTKLayer> mapLayerList = new ArrayList<>();
	private List<Image> images = new ArrayList<>();
	private SAXReader saxReader = new SAXReader();
	private boolean isLoadFinished = false;
	private File mapFile;
	private InputStream mapInputStream;
	private double x,y;

	public WTKTiledMap(List<Image> images, File mapFile) {
		this.images = images;
		this.mapFile = mapFile;

	}

	public WTKTiledMap(List<Image> images, InputStream mapInputStream) {
		this.images = images;
		this.mapInputStream = mapInputStream;
	}
	
	public WTKTiledMap(){}

	public void init() {
		if (mapFile != null) {
			readMap(mapFile);
		} else {
			if (mapInputStream != null) {
				readMap(mapInputStream);
			}
		}
	}

	/**
	 * 读取地图
	 * 
	 * @param file
	 *            地图文件
	 */
	public void readMap(File file) {
		isLoadFinished = false;
		mapLayerList.clear();
		// 清空所有资源
		try {
			Document document = saxReader.read(file);
			readMap(document);
			isLoadFinished = true;
		} catch (DocumentException e) {
			WLog.logInFile("Load Map File Error:" + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 读取地图
	 * 
	 * @param is
	 *            地图文件流
	 */
	public void readMap(InputStream is) {
		isLoadFinished = false;
		mapLayerList.clear();
		// 清空所有资源
		try {
			Document document = saxReader.read(is);
			readMap(document);
			isLoadFinished = true;
		} catch (DocumentException e) {
			WLog.logInFile("Load Map File Error:" + e.toString());
			e.printStackTrace();
		}
	}

	private void readMap(Document document) {
		Element rootElement = document.getRootElement();
		for (Iterator<Element> i = rootElement.elementIterator(); i.hasNext();) {
			Element e = i.next();
			if (e.getName().equals(XMLElements.ELEMENT_MAP_SETTING)) {
				// 读取地图信息
				int mapWidth = Integer.parseInt(e.elementText(XMLElements.ELEMENT_MAP_WIDTH));
				int mapHeight = Integer.parseInt(e.elementText(XMLElements.ELEMENT_MAP_HEIGHT));
				int tileWidth = Integer.parseInt(e.elementText(XMLElements.ELEMENT_TILE_WIDTH));
				int tileHeight = Integer.parseInt(e.elementText(XMLElements.ELEMENT_TILE_HEIGHT));
				WTKMap.getInstance().setMapProperty(tileWidth, tileHeight, mapWidth, mapHeight);
			} else if (e.getName().equals(XMLElements.ELEMENT_MAP_RESOURCE)) {
				// 读取地图资源,并添加到资源管理中
				WAltasResourceManager.getInstance().removeAll();
				int index = 0;
				for (Iterator<Element> j = e.elementIterator(); j.hasNext();) {
					Element ej = j.next();
					String altasID = ej.elementText(XMLElements.ELEMENT_ALTAS_ID);
					String altasPath = ej.elementText(XMLElements.ELEMENT_ALTAS_PATH);
					// String fileName =
					// altasPath.substring(altasPath.lastIndexOf("\\") + 1);
					WAltasResourceManager.getInstance().addResource(altasID, altasPath, images.get(index));
					index++;
				}

			} else if (e.getName().equals(XMLElements.ELEMENT_MAP_DATA)) {
				// 读取图层数据，并转化为地图数据
				for (Iterator<Element> j = e.elementIterator(); j.hasNext();) {
					WTKLayer tiledMapLayer = new WTKLayer();
					Element ej = j.next();
					String layerName = ej.attributeValue(XMLElements.ATTRIBUTE_NAME);
					String visibleStr = ej.attributeValue(XMLElements.ATTRIBUTE_VISIBLE);
					String alphaStr = ej.attributeValue(XMLElements.ATTRIBUTE_ALPHA);
					String mapData = ej.getText();
					tiledMapLayer.setLayerName(layerName);
					if (visibleStr != null)
						tiledMapLayer.setVisible(Boolean.parseBoolean(visibleStr));
					if (alphaStr != null)
						tiledMapLayer.setAlpha(Double.parseDouble(alphaStr));
					tiledMapLayer.ConvertFromString(mapData);
					// 读取的图层添加到列表中
					mapLayerList.add(tiledMapLayer);
				}
			} else if (e.getName().equals(XMLElements.ELEMENT_MAP_PROPERTY)){
				//读取地图时清除属性列表
				WTKMap.getInstance().getPropertyList().clear();
				// 读取图层数据，并转化为地图数据
				for (Iterator<Element> j = e.elementIterator(); j.hasNext();) {
					TileProperty tileProperty = new TileProperty();
					Element ej = j.next();
					String col = ej.attributeValue(XMLElements.ATTRIBUTE_COL);
					String row = ej.attributeValue(XMLElements.ATTRIBUTE_ROW);
					tileProperty.setCol(Integer.parseInt(col));
					tileProperty.setRow(Integer.parseInt(row));
					
					for (Iterator<Element> oj = ej.elementIterator(); oj.hasNext();) {
						Element property = oj.next();
						String key = property.attributeValue(XMLElements.ATTRIBUTE_KEY);
						String value = property.attributeValue(XMLElements.ATTRIBUTE_VALUE);
						tileProperty.insertValue(key, value);
					}
					WTKMap.getInstance().getPropertyList().add(tileProperty);
				}
			}
		}
	}

	public void draw(GraphicsContext gContext2D) {
		if (isLoadFinished) {
			// 绘制多图层地图
			int length = mapLayerList.size();
			int tileWidth = WTKMap.getInstance().getTileWidth();
			int tileHeight = WTKMap.getInstance().getTileHeight();
			if (length > 0) {
				for (int i = length - 1; i >= 0; i--) {
					WTKLayer mapLayer = mapLayerList.get(i);
					if (mapLayer.isVisible()) {
						WTKMapTile[][] tiles = mapLayer.getMapTiles();
						gContext2D.setGlobalAlpha(mapLayer.getAlpha());
						if (tiles != null) {
							for (int y = 0; y < tiles.length; y++) {
								for (int x = 0; x < tiles[0].length; x++) {
									if (tiles[y][x] != null) {
										AltasResource resource = WAltasResourceManager.getInstance().getResourceById(
												tiles[y][x].getAltasId());
										if (resource != null) {
											Image image = resource.getImage();
											int index = tiles[y][x].getAltasIndex();
											if (index != -1) {
												int cellX = (int) (image.getWidth() / tileWidth);
												int col = index % cellX;
												int row = index / cellX;
												gContext2D.drawImage(image, col * tileWidth, row * tileHeight,
														tileWidth, tileHeight, getX() + x * tileWidth, getY() + y
																* tileHeight, tileWidth, tileHeight);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public boolean isLoadFinished() {
		return isLoadFinished;
	}

	public void setLoadFinished(boolean isReadFinished) {
		this.isLoadFinished = isReadFinished;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
