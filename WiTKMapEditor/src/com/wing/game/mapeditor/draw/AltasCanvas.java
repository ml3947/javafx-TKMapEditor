package com.wing.game.mapeditor.draw;

import com.wing.game.mapeditor.property.TiledMap;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class AltasCanvas extends Canvas {
	private GraphicsContext gContext2D;
	private double tileWidth, tileHeight;
	private SimpleIntegerProperty cellXCountProperty = new SimpleIntegerProperty(0);
	private SimpleIntegerProperty cellYCountProperty = new SimpleIntegerProperty(0);
	private Image image;
	// private SimpleIntegerProperty nowChooseProperty = new
	// SimpleIntegerProperty(-1);
	private SimpleListProperty<Integer> nowChooseProperty = new SimpleListProperty<>();
	private ObservableList<Integer> chooseList = FXCollections.observableArrayList();
	private SimpleBooleanProperty showGridProperty = new SimpleBooleanProperty(true);
	private SimpleIntegerProperty brushTypeProperty = new SimpleIntegerProperty(0);

	private double startX, startY;
	private double mouseX, mouseY;
	private boolean isDrag = false;

	public AltasCanvas(double width, double height) {
		super(width, height);
		init();
	}

	public AltasCanvas() {
		this(0, 0);
	}

	private void init() {
		gContext2D = getGraphicsContext2D();
		nowChooseProperty.set(chooseList);
		setOnMouseClicked(e -> {
			switch (brushTypeProperty.get()) {
			case 0:
			case 1:
				if (e.getButton() == MouseButton.PRIMARY) {
					double x = e.getX();
					double y = e.getY();
					int index = (int) (y / tileHeight) * cellXCountProperty.get() + (int) (x / tileWidth);
					if (!chooseList.contains(index))
						chooseList.add(index);
				} else if (e.getButton() == MouseButton.SECONDARY) {
					chooseList.clear();
				}
				break;
			}
		});
		setOnMouseDragged(e -> {
			switch (brushTypeProperty.get()) {
			case 0:
				mouseX = e.getX();
				mouseY = e.getY();
				isDrag = true;
				int minX = (int) Math.min(mouseX, startX);
				int maxX = (int) Math.max(mouseX, startX);
				int minY = (int) Math.min(mouseY, startY);
				int maxY = (int) Math.max(mouseY, startY);
				chooseList.clear();
				for (int y = (int) (minY / tileHeight); y < (int) (maxY / tileHeight) + 1; y++) {
					for (int x = (int) (minX / tileWidth); x < (int) (maxX / tileWidth) + 1; x++) {
						int index = y * cellXCountProperty.get() + x;
						if (!chooseList.contains(index))
							chooseList.add(index);
					}
				}
				break;
			}
		});
		setOnMouseDragExited(e -> {

		});
		setOnMousePressed(e -> {
			startX = e.getX();
			startY = e.getY();
			chooseList.clear();
		});
		setOnMouseReleased(e -> {
			startX = 0;
			startY = 0;
			isDrag = false;
		});
	}

	public void draw() {
		gContext2D.save();
		gContext2D.setFill(Color.WHITE);
		gContext2D.clearRect(0, 0, getWidth(), getHeight());
		gContext2D.setStroke(Color.BLACK);
		tileWidth = TiledMap.getInstance().getTileWidth();
		tileHeight = TiledMap.getInstance().getTileHeight();
		if (image != null) {
			gContext2D.drawImage(image, 0, 0);
			if (getNowChoose() != null && getNowChoose().size() > 0) {
				gContext2D.setGlobalAlpha(0.5f);
				gContext2D.setFill(Color.YELLOW);
				for (Integer index : getNowChoose()) {
					gContext2D.fillRect(index % cellXCountProperty.get() * tileWidth, index / cellXCountProperty.get()
							* tileHeight, tileWidth, tileHeight);
				}
			}
			if (isShowGrid()) {
				gContext2D.setGlobalAlpha(1.0f);
				gContext2D.setLineWidth(0.5f);
				for (int i = 0; i < cellXCountProperty.get(); i++) {
					for (int j = 0; j < cellYCountProperty.get(); j++) {
						gContext2D.strokeRect(i * tileWidth, j * tileHeight, tileWidth, tileHeight);
					}
				}
			}
			if (isDrag) {
				gContext2D.setGlobalAlpha(1.0f);
				gContext2D.setStroke(Color.GREENYELLOW);
				gContext2D.strokeRect(Math.min(mouseX, startX), Math.min(mouseY, startY), Math.abs(mouseX - startX),
						Math.abs(mouseY - startY));
			}
		}
		gContext2D.restore();
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
		if (image != null) {
			widthProperty().bind(image.widthProperty());
			heightProperty().bind(image.heightProperty());
			setCellXCount((int) (image.getWidth() / tileWidth));
			setCellYCount((int) (image.getHeight() / tileHeight));
		}
	}

	public ObservableList<Integer> getNowChoose() {
		return nowChooseProperty.get();
	}

	public void setNowChoose(ObservableList<Integer> nowChoose) {
		this.nowChooseProperty.set(nowChoose);
	}

	public SimpleListProperty<Integer> NowChooseProperty() {
		return nowChooseProperty;
	}

	public SimpleIntegerProperty CellXCountProperty() {
		return cellXCountProperty;
	}

	public void setCellXCount(int cellXCount) {
		cellXCountProperty.set(cellXCount);
	}

	public SimpleIntegerProperty CellYCountProperty() {
		return cellYCountProperty;
	}

	public void setCellYCount(int cellYCount) {
		cellYCountProperty.set(cellYCount);
	}

	public SimpleBooleanProperty ShowGridProperty() {
		return showGridProperty;
	}

	public boolean isShowGrid() {
		return showGridProperty.get();
	}

	public void setShowGrid(boolean isShowGrid) {
		this.showGridProperty.set(isShowGrid);
	}

	public SimpleIntegerProperty BrushTypeProperty() {
		return brushTypeProperty;
	}
}
