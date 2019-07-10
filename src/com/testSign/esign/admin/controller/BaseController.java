package com.testSign.esign.admin.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.ModelAttribute;


/**
 * Created by ltlxy on 2016/9/22.
 */
public class BaseController {
    
    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;
    
    protected SimpleDateFormat timeSign;
    
    protected final Integer ROWS_10 = 10; 
    
    protected final Integer ROWS_5 = 5;
    
    protected JSONObject jsonObject = new JSONObject();
    protected JSONObject jsonObject2 = new JSONObject();
    protected JSONArray jsonArray = new JSONArray();
    protected String projectPath = "";

    @ModelAttribute
    public void initController(HttpServletRequest request,HttpServletResponse response){
        this.request=request;
        this.response=response;
        this.session=request.getSession();
        projectPath = this.request.getServletContext().getRealPath("/");
        timeSign = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    }

    protected void jsonclear(){
       jsonArray.clear();
       jsonObject.clear();
       jsonObject2.clear();
    }

    protected void writeJSONStr(String str) {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter out = null;
		try {
			out = this.response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out.write(str);
    }

    protected void writeJSONPStr(String str,String call){
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out = null;
	   try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.print(""+call+"("+str+")");
			out.flush();
			if (out != null) {
				out.close();
			}
		}
	}
    

}
