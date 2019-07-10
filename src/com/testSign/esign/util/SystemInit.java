package com.testSign.esign.util;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.testSign.esign.esign.SignHelper;

public class SystemInit implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("初始化e签宝 -------开始");
		SignHelper.initProject();
		System.out.println("初始化e签宝 -----完成");
	}

}
