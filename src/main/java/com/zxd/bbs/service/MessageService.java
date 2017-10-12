package com.zxd.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxd.bbs.dao.MessageMapper;
import com.zxd.bbs.dao.ReplyMapper;
import com.zxd.bbs.pojo.Message;
import com.zxd.bbs.pojo.Reply;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月11日 下午5:54:01
* @version 1.0
*/

@Service
public class MessageService {
	
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	
	/**
	 * 查询所有帖子
	 * @return
	 */
	public List<Message> getAll(){
		
		return messageMapper.selectAllWithUserAndReply();
		
	}
	
	
	/**
	 * 根据帖子的id删除帖子，需要先删除该帖子下的所有回复
	 * @param id
	 */
	public void deleteMessageById(int id) {
		
		/**
		 * 先根据帖子的id查询到该帖子
		 */
		Message message = messageMapper.selectByIdWithUserAndReply(id).get(0);
		
		/**
		 * 获取该帖子下的所有回复
		 */
		List<Reply> replies = message.getReplies();
		
		/**
		 * 遍历删除回复
		 */
		for (Reply reply : replies) {
			replyMapper.deleteById(reply.getId());
		}
		
		
		/**
		 * 删除回复之后再删除帖子
		 */
		messageMapper.deleteById(id);				
		
	}
	

}
