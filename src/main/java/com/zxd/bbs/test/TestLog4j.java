package com.zxd.bbs.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年11月3日 下午7:16:40
* @version 1.0
*/

public class TestLog4j {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestLog4j.class);
	
	@Test
	public void TestLog4j() {
		
		LOGGER.info("test:123");
		
	}
	

}
