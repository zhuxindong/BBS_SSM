package com.zxd.bbs.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;



/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月8日 下午2:38:51
* @version 1.0
*/

public class Message {

	private Integer id;
	private String content;
	private Timestamp createtime;
	
	private User user;
	private List<Reply> replies;
	
	public Message() {
		
	}

	public Message(Integer id, String content, Timestamp createtime, User user, List<Reply> replies) {
		super();
		this.id = id;
		this.content = content;
		this.createtime = createtime;
		this.user = user;
		this.replies = replies;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", content=" + content + ", createtime=" + createtime + ", user=" + user
				+ ", replies=" + replies + "]";
	}
	
	
	
	
	
	
	
}
