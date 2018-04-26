package org.wing.jfx.game.core.map.tk;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.scene.image.Image;
/**
 * 贴图集资源管理
 * @author Wing Mei
 */
public class WAltasResourceManager {
	private List<AltasResource> altasResources = new ArrayList<WAltasResourceManager.AltasResource>();
	private static WAltasResourceManager altasResourceManager;

	public static WAltasResourceManager getInstance() {
		if (altasResourceManager == null) {
			altasResourceManager = new WAltasResourceManager();
		}
		return altasResourceManager;
	}

	public void addResource(String altasId, String path,Image image) {
		altasResources.add(new AltasResource(altasId, path, image));
	}

	public void removeResource(String altasId) {
		AltasResource ar = getResourceById(altasId);
		if (ar != null) {
			altasResources.remove(ar);
		}
	}
	
	public void removeResource(int index){
		altasResources.remove(index);
	}
	
	public void removeAll(){
		altasResources.clear();
	}

	public AltasResource getResourceById(String altasId) {
		AltasResource ar = null;
		for (AltasResource resource : altasResources) {
			if (resource.getAltasId().equals(altasId)) {
				ar = resource;
			}
		}
		return ar;
	}
	
	public AltasResource getResourceByPath(String path) {
		AltasResource ar = null;
		for (AltasResource resource : altasResources) {
			if (resource.getPathStr().equals(path)) {
				ar = resource;
			}
		}
		return ar;
	}
	

	public static String createAltasId() {
		return UUID.randomUUID().toString();
	}
	
	public List<AltasResource> getResources(){
		return altasResources;
	}

	public class AltasResource {
		private String altasId;
		private String pathStr;
        private Image image;
		public AltasResource(String altasId, String pathStr, Image image) {
			this.altasId = altasId;
			this.pathStr = pathStr;
			this.image = image;
		}

		public String getAltasId() {
			return altasId;
		}

		public void setAltasId(String altasId) {
			this.altasId = altasId;
		}

		public String getPathStr() {
			return pathStr;
		}

		public void setPathStr(String pathStr) {
			this.pathStr = pathStr;
		}

		public Image getImage() {
			return image;
		}

		public void setImage(Image image) {
			this.image = image;
		}

	}
}
