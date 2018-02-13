package com.loki.server.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String userName;
	private String password;
	private String nickName;
	private String phone;
	private boolean phoneBind;
	private String email;
	private boolean emailBind;
	private String avatar;
	private String payPwd;
	private Timestamp registTime;
	private String registIp;
	private int identityId;
	private int enterpriseId;
	private String easeId;
	private String easePwd;
	private String status;
	
	private String avatarUrl;
	private String identityStatusName;
	private String enterpriseStatusName;
	private String statusName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isPhoneBind() {
		return phoneBind;
	}
	public void setPhoneBind(boolean phoneBind) {
		this.phoneBind = phoneBind;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEmailBind() {
		return emailBind;
	}
	public void setEmailBind(boolean emailBind) {
		this.emailBind = emailBind;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPayPwd() {
		return payPwd;
	}
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
	public Timestamp getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime;
	}
	public String getRegistIp() {
		return registIp;
	}
	public void setRegistIp(String registIp) {
		this.registIp = registIp;
	}
	public int getIdentityId() {
		return identityId;
	}
	public void setIdentityId(int identityId) {
		this.identityId = identityId;
	}
	public int getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(int enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getEaseId() {
		return easeId;
	}
	public void setEaseId(String easeId) {
		this.easeId = easeId;
	}
	public String getEasePwd() {
		return easePwd;
	}
	public void setEasePwd(String easePwd) {
		this.easePwd = easePwd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getIdentityStatusName() {
		return identityStatusName;
	}
	public void setIdentityStatusName(String identityStatusName) {
		this.identityStatusName = identityStatusName;
	}
	public String getEnterpriseStatusName() {
		return enterpriseStatusName;
	}
	public void setEnterpriseStatusName(String enterpriseStatusName) {
		this.enterpriseStatusName = enterpriseStatusName;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}
