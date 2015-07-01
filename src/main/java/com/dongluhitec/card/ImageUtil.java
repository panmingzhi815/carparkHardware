package com.dongluhitec.card;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ImageUtil {

	private static final String imageFolder = "image";
	
	private static Map<String,Image> imageMap = new HashMap<String,Image>();
	
	public static Image get(String name){
		Image image = imageMap.get(name);
		if(image != null){
			return image;
		}
		
		String imagePath = imageFolder + File.separator + name + ".png";
		InputStream resourceAsStream = ImageUtil.class.getClassLoader().getResourceAsStream(imagePath);
		image= new Image(Display.getCurrent(),resourceAsStream);
		imageMap.put(name,image);
		return image;
	}
	
	public static byte[] getBytes(String name){
		String imagePath = imageFolder + File.separator + name;
		try(InputStream resourceAsStream = ImageUtil.class.getClassLoader().getResourceAsStream(imagePath);){
			int available = resourceAsStream.available();
			byte[] bytes = new byte[available];
			resourceAsStream.read(bytes);
			return bytes;
		}catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}
	}
	
	
	
}
