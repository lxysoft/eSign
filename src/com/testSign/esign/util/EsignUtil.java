package com.testSign.esign.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.testSign.esign.admin.po.EsignTemplate;
import com.testSign.esign.esign.SignHelper;
import com.timevale.esign.sdk.tech.bean.PersonBean;
import com.timevale.esign.sdk.tech.bean.PosBean;
import com.timevale.esign.sdk.tech.bean.SignPDFStreamBean;
import com.timevale.esign.sdk.tech.bean.result.AddAccountResult;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;
import com.timevale.esign.sdk.tech.impl.constants.SignType;
import com.timevale.esign.sdk.tech.service.AccountService;
import com.timevale.esign.sdk.tech.service.SelfSignService;
import com.timevale.esign.sdk.tech.service.UserSignService;
import com.timevale.esign.sdk.tech.service.factory.AccountServiceFactory;
import com.timevale.esign.sdk.tech.service.factory.SelfSignServiceFactory;
import com.timevale.esign.sdk.tech.service.factory.UserSignServiceFactory;

public class EsignUtil {
	private static Logger LOG = LoggerFactory.getLogger(SignHelper.class);
	
	/***
	 * 创建个人账户 使用到接口：accountService.addAccount(organizeBean);
	 */
	public static Map<String, Object> addPersonAccount(String name , String idCard, Integer location) {
		
		PersonBean personBean = new PersonBean();
		// 邮箱地址,可空
		// personBean.setEmail(null);
		// 手机号码,可空
//		personBean.setMobile("");
		// 姓名
		personBean.setName(name);
		// 身份证号/护照号
		personBean.setIdNo(idCard);
		// 个人归属地：0-大陆，1-香港，2-澳门，3-台湾，4-外籍，默认0
		personBean.setPersonArea(location);
		// 所属公司,可空
		// personBean.setOrgan("客户的企业");
		// 职位,可空
		// personBean.setTitle("部门经理");
		// 常用地址,可空
		// personBean.setAddress("XXX街道");

		System.out.println("----开始创建个人账户...");
		AccountService accountService = AccountServiceFactory.instance();
		AddAccountResult addAccountResult = accountService.addAccount(personBean);
		Map<String, Object> result = new HashMap<String, Object>();
		if (0 != addAccountResult.getErrCode()) {
			result.put("result", false);
			result.put("errCode", addAccountResult.getErrCode());
			result.put("message", addAccountResult.getMsg());
		} else {
			result.put("result", true);
			result.put("accountId", addAccountResult.getAccountId());
		}
		return result;

	}
	
	
	
	/***
	 * 平台自身PDF摘要签署（文件二进制流）； 盖章位置通过坐标定位； 使用到接口：SelfSignServiceFactory.instance();
	 * selfSignService.localSignPdf(signPDFStreamBean, posBean, sealId,
	 * SignType.Single);
	 */
	public static Map<String , Object> platformSignByStreamm(String srcPdfFile , EsignTemplate esignTemplate) {
		// 设置文件流签署的PDF文档信息
		SignPDFStreamBean signPDFStreamBean = setSignPDFStreamBean(FileHelper.getBytes(srcPdfFile));
		// 设置坐标定位签署的PosBean，坐标定位方式支持单页签章、多页签章和骑缝章，但对关键字签章指定页码无效；
		PosBean posBean = setXYPosBean(esignTemplate.getSignPage().toString(),esignTemplate.getSignX(),esignTemplate.getSignY());
		// 设置签署类型为 单页签章，坐标定位方式支持单页签章、多页签章和骑缝章
		SignType signType = SignType.Single;
		// 设置签署印章，www.tsign.cn官网设置的默认签名sealId = 0
		int sealId = 0;

		System.out.println("----开始平台自身PDF摘要签署...");
		SelfSignService selfSignService = SelfSignServiceFactory.instance();
		FileDigestSignResult fileDigestSignResult = selfSignService.localSignPdf(signPDFStreamBean, posBean, sealId,
				signType);
		Map<String, Object> result = new HashMap<String, Object>();
		if (0 != fileDigestSignResult.getErrCode()) {
			result.put("result", false);
			result.put("errCode", fileDigestSignResult.getErrCode());
			result.put("message", fileDigestSignResult.getMsg());
		} else {
			result.put("result", true);
			result.put("fileDigestSignResult", fileDigestSignResult);
		}
		return result;
	}
	
	/***
	 * 平台下企业用户PDF摘要签署（文件二进制流）；盖章位置通过关键字定位； 使用到接口：UserSignServiceFactory.instance();
	 * userSignService.localSignPDF(accountId,addSealResult.getSealData(),
	 * signPDFStreamBean, posBean, SignType.Single);
	 */
	public static Map<String , Object> userOrganizeSignByStream(byte[] pdfFileStream,  String accountId, EsignTemplate esignTemplate,
			String sealData) {

		// 设置文件流签署的PDF文档信息
		SignPDFStreamBean signPDFStreamBean = setSignPDFStreamBean(pdfFileStream);
		// 设置坐标定位签署的PosBean，坐标定位方式支持单页签章、多页签章和骑缝章，但对关键字签章指定页码无效；
		PosBean posBean = setXYPosBean(esignTemplate.getSignPage().toString(),esignTemplate.getSignOtherX(),esignTemplate.getSignOtherY());
		// 设置签署类型为 关键字签章
		SignType signType = SignType.Single;
		
		System.out.println("----开始平台企业客户的PDF摘要签署...");
		UserSignService userSignService = UserSignServiceFactory.instance();
		FileDigestSignResult fileDigestSignResult = userSignService.localSignPDF(accountId, sealData, signPDFStreamBean,
				posBean, signType);
		
		Map<String, Object> result = new HashMap<String, Object>();
		if (0 != fileDigestSignResult.getErrCode()) {
			result.put("result", false);
			result.put("errCode", fileDigestSignResult.getErrCode());
			result.put("message", fileDigestSignResult.getMsg());
		} else {
			result.put("result", true);
			result.put("fileDigestSignResult", fileDigestSignResult);
		}
		return result;
	}
	
	/***
	 * 保存签署后的文件流
	 */
	public static boolean saveSignedByStream(byte[] signedStream,String signedFolder,String signedFileName) {
		System.out.println("----开始保存签署后文件...");
		boolean isSuccess = false;
		Map<String,String> fileResult = FileHelper.saveFileByStream(signedStream, signedFolder,signedFileName);
		if (0 != Integer.parseInt(fileResult.get("errCode"))) {
			LOG.info("保存签署后文件失败，errCode=" + fileResult.get("errCode") + " msg=" + fileResult.get("msg"));
		} else {
			isSuccess = true;
			System.out.println("保存签署后文件成功！errCode=" + fileResult.get("errCode") + " msg=" + fileResult.get("msg"));
		}
		return isSuccess;

	}
	
	/***
	 * 文件流签署的PDF文档信息
	 */
	public static SignPDFStreamBean setSignPDFStreamBean(byte[] pdfFileStream) {
		SignPDFStreamBean signPDFStreamBean = new SignPDFStreamBean();
		// 待签署文档本地二进制数据
		signPDFStreamBean.setStream(pdfFileStream);
		// 文档名称，e签宝签署日志对应的文档名，若为空则取文档路径中的名称
		// signPDFStreamBean.setFileName("pdf文件名");
		// 文档编辑密码，当目标PDF设置权限密码保护时必填 */
		// signPDFStreamBean.setOwnerPassword(null);
		return signPDFStreamBean;
	}
	
	/***
	 * 坐标定位签署的PosBean
	 */
	public static PosBean setXYPosBean(String page,int x,int y) {
		PosBean posBean = new PosBean();
		// 定位类型，0-坐标定位，1-关键字定位，默认0，若选择关键字定位，签署类型(signType)必须指定为关键字签署才会生效。
		posBean.setPosType(0);
		// 签署页码，若为多页签章，支持页码格式“1-3,5,8“，若为坐标定位时，不可空
		posBean.setPosPage(page);
		// 签署位置X坐标，默认值为0，以pdf页面的左下角作为原点，控制横向移动距离，单位为px
		posBean.setPosX(x);
		// 签署位置Y坐标，默认值为0，以pdf页面的左下角作为原点，控制纵向移动距离，单位为px
		posBean.setPosY(y);
		// 印章展现宽度，将以此宽度对印章图片做同比缩放。详细查阅接口文档的15 PosBean描述
		posBean.setWidth(159);
		return posBean;
	}
}
