package com.yc;

import javax.imageio.ImageIO;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 比较两张图片的相似度
 */
public class SimilarityComparer {
	// 改变成二进制码
	private static String[][] getPX(BufferedImage image) {
		int[] rgb = new int[3];
		int width = image.getWidth();
		int height = image.getHeight();
		int minx = image.getMinX();
		int miny = image.getMinY();
		String[][] list = new String[width][height];
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				int pixel = image.getRGB(i, j);
				rgb[0] = (pixel & 0xff0000) >> 16;
			rgb[1] = (pixel & 0xff00) >> 8;
			rgb[2] = (pixel & 0xff);
			list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];
			}
		}
		return list;
	}


	public static boolean compareImage(BufferedImage image1, BufferedImage image2) {
		boolean result = false;
		// 分析图片相似度 begin
		String[][] list1 = getPX(image1);
		String[][] list2 = getPX(image2);
		int xiangsi = 0;
		int busi = 0;
		int i = 0, j = 0;
		for (String[] strings : list1) {
			if ((i + 1) == list1.length) {
				continue;
			}
			for (int m = 0; m < strings.length; m++) {
				try {
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					int k = 0;
					for (int n = 0; n < value2.length; n++) {
						if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 3) {
							xiangsi++;
						} else {
							busi++;
						}
					}
				} catch (RuntimeException e) {
					continue;
				}
				j++;
			}
			i++;
		}
		list1 = getPX(image2);
		list2 = getPX(image1);
		i = 0;
		j = 0;
		for (String[] strings : list1) {
			if ((i + 1) == list1.length) {
				continue;
			}
			for (int m = 0; m < strings.length; m++) {
				try {
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					int k = 0;
					for (int n = 0; n < value2.length; n++) {
						if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 3) {
							xiangsi++;
						} else {
							busi++;
						}
					}
				} catch (RuntimeException e) {
					continue;
				}
				j++;
			}
			i++;
		}
		if (busi == 0) {
			result = true;
		}
		return result;
	}

	
	public static String readPicAsString(String path){
		try {
			FileInputStream fis=new FileInputStream(path);
			BufferedInputStream bis=new BufferedInputStream(fis);
			byte[] buffer=new byte[256];
			StringBuffer sbf=new StringBuffer();
			BASE64Encoder encoder=new BASE64Encoder();
			while(bis.read(buffer)>=0){
				sbf.append(Base64.encode(buffer));
			}
			fis.close();
			bis.close();
			return sbf.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) throws IOException {
		InputStream stream = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer( readPicAsString("1.jpg") ));
		BufferedImage n6 = ImageIO.read(stream);
		stream = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(  readPicAsString("2.jpg")  ));
		BufferedImage n9 = ImageIO.read(stream);
		System.out.println(SimilarityComparer.compareImage(n6, n9));
	}
}