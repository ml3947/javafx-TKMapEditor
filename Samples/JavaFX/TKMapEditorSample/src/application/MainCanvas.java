package application;

import java.util.ArrayList;
import java.util.List;

import org.wing.jfx.game.core.map.tk.WTKTiledMap;
import org.wing.jfx.game.core.tools.WResource;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MainCanvas extends Canvas {
	private WTKTiledMap wtkTiledMap;
	public MainCanvas(double width, double height) {
		super(width, height);
		
		List<Image> images = new ArrayList<>();
		images.add(new Image(WResource.getResourceInRes("TileA5.png")));
		images.add(new Image(WResource.getResourceInRes("TileB.png")));
		images.add(new Image(WResource.getResourceInRes("TileD.png")));
		wtkTiledMap = new WTKTiledMap(images, WResource.getResourceAsStreamInRes("testmap.xml"));
		wtkTiledMap.init();
		draw(getGraphicsContext2D());
	}
	
	public void draw(GraphicsContext gc){
		wtkTiledMap.draw(gc);
	}
}
