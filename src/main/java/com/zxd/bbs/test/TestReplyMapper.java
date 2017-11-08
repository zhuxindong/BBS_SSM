package com.zxd.bbs.test;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.zxd.bbs.dao.MessageMapper;
import com.zxd.bbs.dao.ReplyMapper;
import com.zxd.bbs.dao.UserMapper;
import com.zxd.bbs.pojo.Message;
import com.zxd.bbs.pojo.Reply;
import com.zxd.bbs.pojo.User;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月11日 下午5:33:03
* @version 1.0
*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestReplyMapper {
	
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void testInsert(){
		
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
		ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
		
		User user = userMapper.selectByUserNameWithMsg("201403080433").get(0);
		Message message = messageMapper.selectByUserNameWithUserAndReply("201403080433").get(0);
		
		Reply reply = new Reply();
		Timestamp createtime = new Timestamp(System.currentTimeMillis());
		
		reply.setContent("Mybatis 插入的回复");
		reply.setUser(user);
		reply.setMessage(message);
		reply.setCreatetime(createtime);
		
		int rows = replyMapper.insert(reply);
		
		System.out.println(rows);
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	

}
