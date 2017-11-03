package com.zxd.bbs.pojo;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年11月3日 下午5:21:51
* @version 1.0
*/

public class UserToken {
	
	private String username;
	
	private String password;
	
	private String rpassword;
	
	private String name;
	
	private String sex;
	
	private String description;
	
	private String rememberMe;
	
	public UserToken() {
		
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

	public String getRpassword() {
		return rpassword;
	}

	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
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

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String toString() {
		return "UserToken [username=" + username + ", password=" + password + ", rpassword=" + rpassword + ", name="
				+ name + ", sex=" + sex + ", description=" + description + ", rememberMe=" + rememberMe + "]";
	}
	
	
	
	
	
	
	

}
