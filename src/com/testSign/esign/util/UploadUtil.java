package com.testSign.esign.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ltlxy on 2016/9/22.
 */
public class UploadUtil{

    public static final String FILE_PATH_BRAND = "brand";
    public static final String FILE_PATH_STUDIO = "studio";
    public static final String FILE_PATH_MEMBER = "member";
    public static final String FILE_PATH_USER = "user";
    public static final String FILE_PATH_EXCEL="excel";
    public static String getPath(String name){
    	 HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
    	 String extName = name.substring(name.lastIndexOf("."));
    	 String perName=name.substring(0, name.lastIndexOf("."));
    	 String newName = perName+System.currentTimeMillis()+extName;
    	 String path = request.getServletContext().getRealPath("/")+"static/"+newName;
    	return path;
    }
    public static String upload(MultipartFile file,String path , String name) throws IOException {
    	if (file.isEmpty()){
            return null;
        }
    	
        String fileName = file.getOriginalFilename();
        String extName = fileName.substring(fileName.lastIndexOf("."));
        String newName = name + extName;
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
        //保存路径
        String savePath = request.getServletContext().getRealPath("/")+"static"+"/"+path+"/"+newName;
        //虚拟路径
        String vPath = "static"+"/"+path+"/"+newName;
        File newFile = new File(savePath);
        File dirPath = newFile.getParentFile();
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
        InputStream in = file.getInputStream();
        FileOutputStream out = new FileOutputStream(newFile);
        IOUtils.copy(in,out);
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);
        return vPath;
    }


    public static void main(String[] args) {

        System.out.println(Byte.parseByte("0"));

    }
    
    
public static String saveImg(MultipartFile file,String pre){
	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String officePicPath = request.getServletContext().getRealPath("/")+"static"+"/"+pre+"/";
		String fileName=System.currentTimeMillis()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		InputStream in = null;
		OutputStream os = null;
		byte[] buffer = new byte[1024];
		int length = 0;
		try {
			try {
				in = file.getInputStream();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			os = new FileOutputStream(officePicPath+fileName);
            try{
				while((length = in.read(buffer)) > 0){
				     os.write(buffer, 0, length);
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try{
					in.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(os!=null){
				try{
					os.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		
		
		return pre+"/"+fileName;
	}

	/**
	 * 删除图片
	 * @param savePath  文件在数据库中的保存路径
	 * @return 删除成功：true 删除失败：false
	 */
	public static boolean deleteUploadImg(String savePath) {
	
		@SuppressWarnings("deprecation")
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
		String oldSavePath =  request.getServletContext().getRealPath("/"+savePath);
		
		boolean flag = false;
		File file = new File(oldSavePath);
		if (file.exists()) {
			if (file.delete()) {
				flag = true;
			}
		}
		return flag;
	}


}
