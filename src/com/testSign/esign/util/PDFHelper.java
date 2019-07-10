package com.testSign.esign.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFHelper {
	private static Logger LOG = LoggerFactory.getLogger(PDFHelper.class);
	/***
	 * PDF文件转PNG图片，全部页数
	 * 
	 * @param PdfFilePath
	 * @param imgFilePath
	 * @param dpi
	 * @return
	 */
	public static Map<String , Object> pdf2Image(String PdfFilePath, String dstImgFolder, int dpi , String projectPath) {
		File file = new File(PdfFilePath);
		PDDocument pdDocument;
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer imgFilePath = null;
		List<String> list = new ArrayList<>();
		try {
			String imgPDFPath = file.getParent();
			int dot = file.getName().lastIndexOf('.');
			String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
			String imgFolderPath = null;
			if (dstImgFolder.equals("")) {
				imgFolderPath = imgPDFPath;// 获取图片存放的文件夹路径
			} else {
				imgFolderPath = dstImgFolder;
			}

			if (FileHelper.createDirectory(imgFolderPath)) {
				pdDocument = PDDocument.load(file);
				PDFRenderer renderer = new PDFRenderer(pdDocument);
				/* dpi越大转换后越清晰，相对转换速度越慢 */
				int pages = pdDocument.getNumberOfPages();
				for (int i = 0; i < pages; i++) {
					String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
					imgFilePath = new StringBuffer();
					imgFilePath.append(imgFilePathPrefix);
					imgFilePath.append("_");
					imgFilePath.append(String.valueOf(i + 1));
					imgFilePath.append(".png");
					File dstFile = new File(imgFilePath.toString());
					BufferedImage image = renderer.renderImageWithDPI(i, dpi);
					ImageIO.write(image, "png", dstFile);
					list.add(imgFilePath.toString().replace(projectPath, ""));
				}

			} else {
				result.put("result", false);
				result.put("message", "创建" + imgFolderPath + "失败");
			}

		} catch (IOException e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("message", e.getMessage());
			
		}
		result.put("result", true);
		result.put("path", list);
		return result;
	}	
}
