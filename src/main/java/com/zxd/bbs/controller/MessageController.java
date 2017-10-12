package com.zxd.bbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxd.bbs.pojo.Message;
import com.zxd.bbs.pojo.Msg;
import com.zxd.bbs.service.MessageService;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月11日 下午5:52:26
* @version 1.0
*/

@Controller
public class MessageController {
	
	
	@Autowired
	private MessageService messageService;
	
	
	/**
	 * 分页查询帖子信息，同时查询出帖子对应的回复列表，返回json数据
	 * @param pn
	 * @return
	 */
	@RequestMapping("/messages")
	@ResponseBody
	public Msg getMessagesWithJosn(@RequestParam(value="pn",defaultValue="1") Integer pn){
		
		/**
		 * 利用pagehelper来分页查询
		 */
		PageHelper.startPage(pn, 10);
		List<Message> messages = messageService.getAll();
		
		/**
		 * 封装成pageinfo对象
		 */
		PageInfo page = new PageInfo(messages);		
		return Msg.success().add("pageinfo", page);
		
	}
	
	
	
	public Msg deleteMessageWithReplyById(int id) {
		
		return null;
	}

}
