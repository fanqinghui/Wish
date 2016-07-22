package com.foundation.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		//"classpath*:spring/applicationContext-redis.xml",
	//"classpath*:spring/spring-mvc.xml",
	//"classpath*:spring/applicationContext-rabbit.xml",
	/*"classpath*:spring/applicationContext.xml"*/})
public class BankCardsServiceBeanTest {
	
	@Autowired
	//BankCardsServiceBean bankCardsServiceBean;

	@Test
	public void getBankCardsByUid() {
		
		try {
			Integer userId = 1;
			
			/*List<BankCards> list = bankCardsServiceBean.getBankCardsByUid(userId);*/
			
			/*System.out.println("list= "+(list==null?null:list.size()));*/
			
		} catch (Exception e) {

			e.printStackTrace();
			fail();
			
		}
		
	}

}
