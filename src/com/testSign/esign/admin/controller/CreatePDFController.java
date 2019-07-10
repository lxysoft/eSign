package com.testSign.esign.admin.controller;

import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.testSign.esign.admin.po.EsignTemplate;
import com.testSign.esign.esign.SignHelper;
import com.testSign.esign.util.EsignUtil;
import com.testSign.esign.util.UploadUtil;
import com.testSign.esign.util.WordToPdf;
import com.timevale.esign.sdk.tech.bean.result.AddSealResult;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;

@Controller
public class CreatePDFController extends BaseController {
	
	@RequestMapping("/zjEsignPC/templateTest")
	public String testPage(WebRequest request){
		return "esign/templateTest/templateTest";
	}
	
	@RequestMapping("/zjEsignPC/test/word2Pdf")
	@ResponseBody
	public void createPDF(@RequestParam(value = "file",required = false) MultipartFile file){
		String path = "";
		//上传文件，保存到本地
		if(file != null && file.getSize() > 0){
			try {
				path = UploadUtil.upload(file, "esignDoc/test" , "test");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String wordPth = projectPath + "/" + path;
		String pdfPath = wordPth.replace(".doc", ".pdf");
        Map<String, Object> result = new HashMap<String ,Object>();
		try {
			WordToPdf.doc2Pdf(wordPth, pdfPath);
			result.put("path", pdfPath.replace(projectPath, ""));
			result.put("result", true);
			//DownLoadUtil.downLoad(pdfPath, response, true);
		} catch (ConnectException e) {
			result.put("result", false);
			result.put("message", "word生成PDF失败！");
			e.printStackTrace();
		} catch (Exception e) {
			result.put("result", false);
			result.put("message", "文件在线预览失败！");
			e.printStackTrace();
		}finally{
			writeJSONStr(new Gson().toJson(result));
		}
	}
	
	@RequestMapping("/zjEsignPC/test/testSignPDF")
	@ResponseBody
	public void signPDF(Integer signX , Integer signY , Integer signOtherX , Integer signOtherY , Integer signPage){
		Map<String, Object> result = new HashMap<String ,Object>();
		EsignTemplate template = new EsignTemplate();
		template.setSignX(signX);
		template.setSignY(signY);
		template.setSignOtherX(signOtherX);
		template.setSignOtherY(signOtherY);
		template.setSignPage(signPage);
		String srcPdfFile = projectPath + "/static/esignDoc/test/test.pdf";
	    //签署后保存路径
		String signedFolder = projectPath + "/static/esignDoc/test/";
	    //签署后文件保存名
		String signedFileName = "testsign.pdf";
		// 贵公司签署，签署方式：坐标定位,以文件流的方式传递pdf文档
		Map<String, Object> mapSign1 = null;
		try {
			mapSign1 = EsignUtil.platformSignByStreamm(srcPdfFile, template);
		} catch (Exception e) {
			result.put("result", false);
			result.put("message", "签署页数过大");
			return ;
		}
		
		FileDigestSignResult signResult = (FileDigestSignResult)mapSign1.get("fileDigestSignResult");;
		boolean x = template.getSignOtherX() != null && !"".equals(template.getSignOtherX());
		boolean y = template.getSignOtherY() != null && !"".equals(template.getSignOtherY());
		if(x && y){
			// 创建企业客户账号
			String userOrganizeAccountId = SignHelper.addOrganizeAccount();
        	// 创建企业印章
			AddSealResult userOrganizeSealData = SignHelper.addOrganizeTemplateSeal(userOrganizeAccountId);
			signResult = (FileDigestSignResult) EsignUtil.userOrganizeSignByStream(signResult.getStream(), userOrganizeAccountId, template ,userOrganizeSealData.getSealData()).get("fileDigestSignResult");
        }
		
		
		// 所有签署完成,将最终签署后的文件流保存到本地
		if (0 == signResult.getErrCode()) {
			SignHelper.saveSignedByStream(signResult.getStream(), signedFolder, signedFileName);
		}
		try {
			result.put("path", signedFolder.replace(projectPath, "") + signedFileName);
			result.put("result", true);
		} catch (Exception e) {
			result.put("result", true);
			result.put("message", "合同测试签署失败");
			e.printStackTrace();
		}finally{
			writeJSONStr(new Gson().toJson(result));
		}
	}
}
