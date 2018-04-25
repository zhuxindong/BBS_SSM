package com.zxd.bbs.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zxd.bbs.dao.UserMapper;
import com.zxd.bbs.pojo.Message;
import com.zxd.bbs.pojo.User;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月8日 下午3:41:13
* @version 1.0
*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestUserMapper {
	
	
	@Autowired
	UserMapper mapper;
	
	
	@Test
	public void testSelectAll(){
		

		List<User> users = mapper.selectAllWithMsg();
		
		for (User user : users) {
			List<Message> messages = user.getMessages();
			System.out.println(user.getName()+"----->:");
			for (Message message : messages) {
				System.out.println(message.getContent()+" -- "+message.getCreatetime());
			}
		}
	
	}
	
	
	@Test
	public void testSelectByUserName(){
		

		List<User> users = mapper.selectByUserNameWithMsg("201403080433");
		
		for (User user : users) {
			
			System.out.println("name:"+user.getName());
			System.out.println("desc:"+user.getDescription());
			
			List<Message> messages = user.getMessages();
			
			for (Message message : messages) {
				System.out.println(message.getContent()+" -- "+message.getCreatetime());
			}
		}
		
	}
	
	@Test
	public void testInsertSelective(){
		
		User user = new User();
		
		user.setUsername("admin");
		user.setPassword("admin");
		user.setName("admin");
		user.setSex("男");
		
		int rows = mapper.insertSelective(user);
		
		System.out.println(rows);
		
		
	}
	
	
	@Test
	public void testUpdateByUserNameSelective(){
		
		User user = new User();
		user.setUsername("201403080433");
		user.setPassword("123456");
		
		int rows = mapper.updateByUserNameSelective(user);
		
		System.out.println(rows);
	}
	

}
