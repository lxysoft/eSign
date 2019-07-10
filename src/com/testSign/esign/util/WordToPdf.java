package com.testSign.esign.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.timevale.tgtext.text.pdf.fi;

public class WordToPdf{
  
  /*public static void main(String[] args) {
	  try {
		doc2Pdf("D:/doc5.doc" , "d:/doc5.pdf");
	} catch (ConnectException e) {
		
		e.printStackTrace();
	}
  }*/
  
  /*public static void doc2Pdf(String docPath, String pdfPath) throws ConnectException  {  
      File inputFile = new File(docPath);//预转文件  
      File outputFile = new File(pdfPath);//pdf文件  
      OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
      System.out.println(1.11);
      
      try {
    	  connection.connect();//建立连接  
	} catch (Exception e) {
		// TODO: handle exception
	}
      
      System.out.println(1.12);
      DocumentConverter converter = new OpenOfficeDocumentConverter(connection);  
      DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();     
      DocumentFormat txt = formatReg.getFormatByFileExtension("odt") ;//设定文件格式  
      DocumentFormat pdf = formatReg.getFormatByFileExtension("pdf") ;//设定文件格式  
      converter.convert(inputFile, txt, outputFile, pdf);//文件转换  
      connection.disconnect();//关闭连接  
  }*/
  
  public static void doc2Pdf(String sourceFile, String destFile) throws ConnectException {  
	  OpenOfficeConnection connection = null;
	  Process pro = null;
      try {  
          File inputFile = new File(sourceFile);  
          //找不到源文件
          if (!inputFile.exists()) {  
          }  

          // 如果目标路径不存在, 则新建该路径  
          File outputFile = new File(destFile);  
          if (!outputFile.getParentFile().exists()) {  
              outputFile.getParentFile().mkdirs();  
          }  
        //这里是OpenOffice的安装目录, 在我的项目中,为了便于拓展接口,没有直接写成这个样子,但是这样是绝对没问题的
          String OpenOffice_HOME = "";
          String command = "";
          String xt = System.getProperty("os.name").split(" ")[0];
          // 启动OpenOffice的服务  
          if("Windows".contentEquals(xt) || System.getProperty("os.name").indexOf("Windows") != -1){
        	  OpenOffice_HOME = "E:\\OpenOffice.org3\\OpenOffice.org3\\"; 
        	  command = OpenOffice_HOME  
                      + "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";  
          }else{
        	  command = "soffice -headless -accept=\"socket,host=127.0.0.1,port=8100:urp;\" -nofirststartwizard &";  
          }
          
          pro = Runtime.getRuntime().exec(command);
          // connect to an OpenOffice.org instance running on port 8100  
          connection = new SocketOpenOfficeConnection(  
                  "127.0.0.1", 8100);
         try {
        	 connection.connect();  
		} catch (Exception e) {
			e.printStackTrace();
		}
          // convert  
          DocumentConverter converter = new StreamOpenOfficeDocumentConverter(  
                  connection);  
          converter.convert(inputFile, outputFile);  

      } catch (FileNotFoundException e) {  
          e.printStackTrace();  
      } catch (IOException e) {  
          e.printStackTrace();  
      }finally{
    	// close the connection  
          connection.disconnect();  
          // 关闭OpenOffice服务的进程  
          pro.destroy();
      }
  } 
}