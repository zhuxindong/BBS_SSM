package com.zxd.bbs.pojo;

import java.util.List;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月8日 下午2:34:18
* @version 1.0
*/

public class User {

	private Integer id;
	private String username;
	private String password;
	private String name;
	private String sex;
	private String description;
	
	private List<Message> messages;
	private Score score;
	
	public User() {
	
	}

	public User(Integer id, String username, String password, String name, String sex, String description,
			List<Message> messages, Score score) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.sex = sex;
		this.description = description;
		this.messages = messages;
		this.score = score;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", sex="
				+ sex + ", description=" + description + ", messages=" + messages + ", score=" + score + "]";
	}
	
	
	
	
	
	
	
}
