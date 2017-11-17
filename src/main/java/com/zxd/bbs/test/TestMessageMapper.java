package com.zxd.bbs.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.zxd.bbs.dao.MessageMapper;
import com.zxd.bbs.dao.ReplyMapper;
import com.zxd.bbs.dao.UserMapper;
import com.zxd.bbs.pojo.Message;
import com.zxd.bbs.pojo.Reply;
import com.zxd.bbs.pojo.User;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月9日 上午11:07:52
* @version 1.0
*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestMessageMapper {
	
	Logger logger = LoggerFactory.getLogger(TestMessageMapper.class);
	
	@Autowired
	MessageMapper messageMapper;
	
	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void testSelectAllWithUserAndReply(){
		
		logger.info("test");
		PageHelper.startPage(1, 10);
		List<Message> messages = messageMapper.selectAllWithUserAndReply();
		
		for (Message message : messages) {
			System.out.println(message.getUser().getName()+" 的帖子："+message.getContent()+"---"+message.getCreatetime()+"-->回复:");
			List<Reply> replies = message.getReplies();
			for (Reply reply : replies) {
				System.out.println("  "+reply.getContent()+"--"+reply.getCreatetime()+"--By"+reply.getUser().getName());
			}
		}
		
	}
	
	
	
	@Test
	public void testSelectByUserNameWithUserAndReply(){
		PageHelper.startPage(1, 10);
		List<Message> messages = messageMapper.selectByUserNameWithUserAndReply("201403080433");
		
		for (Message message : messages) {
			System.out.println(message.getUser().getName()+" 的帖子："+message.getContent()+"---"+message.getCreatetime()+"-->回复:");
			List<Reply> replies = message.getReplies();
			for (Reply reply : replies) {
				System.out.println("  "+reply.getContent()+"--"+reply.getCreatetime()+"--By"+reply.getUser().getName());
			}
		}
	}
	
	
	@Test
	public void testInsert(){
		
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		User user = userMapper.selectByUserNameWithMsg("201403080433").get(0);
		
		Date date = new Date();
		
		Timestamp createtime = new Timestamp(date.getTime());
		
		
		
		Message message = new Message();
		
		message.setUser(user);
		message.setContent("mybatis 插入的");
		message.setCreatetime(createtime);
		
		int rows = messageMapper.insert(message);
		
		System.out.println(rows);
		
	}
	
	
	@Test
	public void testDeleteMessageById() {
		
		Integer id = 673;
		
		/**
		 * 先根据帖子的id查询到该帖子
		 */
		Message message = messageMapper.selectByIdWithUserAndReply(id).get(0);
		
		/**
		 * 获取该帖子下的所有回复
		 */
		List<Reply> replies = message.getReplies();
		
		System.out.println(replies.size());
		
		/**
		 * 遍历删除回复
		 */
		for (Reply reply : replies) {
			System.out.println(reply.getId());
			replyMapper.deleteById(reply.getId());
		}
		
		
		
		/**
		 * 删除回复之后再删除帖子
		 */
		messageMapper.deleteById(id);
		
	}
	
	
	
	
	
	
	

}
