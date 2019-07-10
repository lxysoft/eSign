package com.testSign.esign.test;

import java.io.File;

import com.testSign.esign.esign.SignHelper;
import com.timevale.esign.sdk.tech.bean.result.AddSealResult;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;

/***
 * e签宝快捷签-电子签名Demo
 * 
 * @author Ching
 *
 */
public class TestSignDemo {

	public static void main(String[] args) {

		// 待签署的PDF文件路径
		String srcPdfFile = "pdf" + File.separator + "test.pdf";
		// 最终签署后的PDF文件路径
		String signedFolder = "pdf" + File.separator;
		// 最终签署后PDF文件名称
		String signedFileName = "signed.pdf";

		// 初始化项目，做全局使用，只初始化一次即可
		SignHelper.initProject();

		// 应用场景演示
		int scene = 0;
		switch (scene) {
		case 0:
			System.out.println("----<场景演示：使用标准的模板印章签署，签署人之间用文件二进制流传递>----");
			// 使用标准的模板印章签署
			doSignWithTemplateSealByStream(srcPdfFile, signedFolder, signedFileName);
			break;
		case 1:
			System.out.println("----<场景演示：使用上传的印章图片签署，签署人之间用文件二进制流传递>----");
			// 使用上传的印章图片签署
			doSignWithImageSealByStream(srcPdfFile, signedFolder, signedFileName);
			break;
		case 2:
			System.out.println("----<场景演示：修改个人手机号>----");
			// 更新个人账户信息，如修改手机号,需要accountId
			SignHelper.updatePersonAccount("***", "***");
			break;
		case 3:
			System.out.println("----<场景演示：修改企业手机号>----");
			// 更新企业账户信息，如修改手机号,需要accountId
			SignHelper.updateOrganizeAccount("***", "***");
			break;
		case 4:
			System.out.println("----<场景演示：使用标准的模板印章签署，签署人之间用文件传递>----");
			// 原始待签署文档
			String srcPdf = "pdf" + File.separator + "test.pdf";
			// 贵公司签署后文档
			String platformSignedPdf = "pdf" + File.separator + "SignedByPlatform.pdf";
			// 贵公司个人用户签署后文档
			String personSignedPdf = "pdf" + File.separator + "SignedByPerson.pdf";
			// 使用标准的模板印章签署，签署人之间用文件传递
			doSignWithTemplateSealByFile(srcPdf,platformSignedPdf,personSignedPdf);
			break;
		default:
			System.out.println("---- 提示！请选择应用场景...");
			break;
		}

	}

	/***
	 * 签署人之间用文件二进制流传递,标准模板印章签署，所用印章SealData为addTemplateSeal接口创建的模板印章返回的SealData
	 * 
	 * @param srcPdfFile
	 * @param signedFolder
	 * @param signedFileName
	 */
	public static void doSignWithTemplateSealByStream(String srcPdfFile, String signedFolder, String signedFileName) {
		// 创建个人客户账户
		String userPersonAccountId = SignHelper.addPersonAccount();
		// 创建企业客户账号
		String userOrganizeAccountId = SignHelper.addOrganizeAccount();
		// 创建个人印章
		AddSealResult userPersonSealData = SignHelper.addPersonTemplateSeal(userPersonAccountId);
		// 创建企业印章
		AddSealResult userOrganizeSealData = SignHelper.addOrganizeTemplateSeal(userOrganizeAccountId);

		// 贵公司签署，签署方式：坐标定位,以文件流的方式传递pdf文档
		FileDigestSignResult platformSignResult = SignHelper.platformSignByStreamm(srcPdfFile);

		// 个人客户签署，签署方式：关键字定位,以文件流的方式传递pdf文档
		FileDigestSignResult userPersonSignResult = SignHelper.userPersonSignByStream(platformSignResult.getStream(),
				userPersonAccountId, userPersonSealData.getSealData());

		// 企业客户签署,坐标定位,以文件流的方式传递pdf文档
		FileDigestSignResult userOrganizeSignResult = SignHelper.userOrganizeSignByStream(
				userPersonSignResult.getStream(), userOrganizeAccountId, userOrganizeSealData.getSealData());

		// 所有签署完成,将最终签署后的文件流保存到本地
		if (0 == userOrganizeSignResult.getErrCode()) {
			SignHelper.saveSignedByStream(userOrganizeSignResult.getStream(), signedFolder, signedFileName);
		}
	}

	/***
	 * 签署人之间用文件传递,标准模板印章签署，所用印章SealData为addTemplateSeal接口创建的模板印章返回的SealData
	 * 
	 * @param srcPdfFile
	 * @param signedFolder
	 * @param signedFileName
	 */
	public static void doSignWithTemplateSealByFile(String srcPdf, String platformSignedPdf, String personSignedPdf) {
		// 创建个人客户账户
		String userPersonAccountId = SignHelper.addPersonAccount();

		// 创建个人印章
		AddSealResult userPersonSealData = SignHelper.addPersonTemplateSeal(userPersonAccountId);

		// 贵公司签署，签署方式：坐标定位,以文件的方式传递pdf文档
		SignHelper.platformSignByFile(srcPdf, platformSignedPdf);

		// 个人客户签署，签署方式：关键字定位,以文件的方式传递pdf文档
		SignHelper.userPersonSignByFile(platformSignedPdf, personSignedPdf, userPersonAccountId,
				userPersonSealData.getSealData());
	}

	/***
	 * 上传印章图片签署，所用印章SealData为印章图片的Base64数据
	 * 
	 * @param srcPdfFile
	 * @param signedFolder
	 * @param signedFileName
	 */
	public static void doSignWithImageSealByStream(String srcPdfFile, String signedFolder, String signedFileName) {
		// 个人印章图片文件路径
		String personImgFilePath = "images" + File.separator + "PersonSeal.png";
		// 企业印章图片文件路径
		String organizeImgFilePath = "images" + File.separator + "OrganizeSeal.png";
		// 创建个人客户账户
		String userPersonAccountId = SignHelper.addPersonAccount();
		// 创建企业客户账号
		String userOrganizeAccountId = SignHelper.addOrganizeAccount();
		// 通过上传的印章图片获取个人印章数据
		String personSealData = SignHelper.getSealDataByImage(personImgFilePath);
		// 通过上传的印章图片获取企业印章数据
		String organizeSealData = SignHelper.getSealDataByImage(organizeImgFilePath);

		// 贵公司签署，签署方式：坐标定位,以文件流的方式传递pdf文档
		FileDigestSignResult platformSignResult = SignHelper.platformSignByStreamm(srcPdfFile);

		// 个人客户签署，签署方式：关键字定位,以文件流的方式传递pdf文档
		FileDigestSignResult userPersonSignResult = SignHelper.userPersonSignByStream(platformSignResult.getStream(),
				userPersonAccountId, personSealData);

		// 企业客户签署,坐标定位,以文件流的方式传递pdf文档
		FileDigestSignResult userOrganizeSignResult = SignHelper
				.userOrganizeSignByStream(userPersonSignResult.getStream(), userOrganizeAccountId, organizeSealData);

		// 所有签署完成,将最终签署后的文件流保存到本地
		if (0 == userOrganizeSignResult.getErrCode()) {
			SignHelper.saveSignedByStream(userOrganizeSignResult.getStream(), signedFolder, signedFileName);
		}
	}

	public static void toDoList() {
		// 注销个人或企业账户,需要accountId
		// SignHelper.deleteAccount("");
		// 更新个人账户信息，如修改手机号,需要accountId
		SignHelper.updatePersonAccount("", "");
	}

}
