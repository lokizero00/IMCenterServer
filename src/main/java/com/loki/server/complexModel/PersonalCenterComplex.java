package com.loki.server.complexModel;

import java.io.Serializable;

import com.loki.server.model.EnterpriseCertification;
import com.loki.server.model.IdentityCertification;
import com.loki.server.model.Intention;
import com.loki.server.model.User;

public class PersonalCenterComplex implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private User user;
	private IdentityCertification identityCertification;
	private EnterpriseCertification enterpriseCertification;
	private Intention intention;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public IdentityCertification getIdentityCertification() {
		return identityCertification;
	}
	public void setIdentityCertification(IdentityCertification identityCertification) {
		this.identityCertification = identityCertification;
	}
	public EnterpriseCertification getEnterpriseCertification() {
		return enterpriseCertification;
	}
	public void setEnterpriseCertification(EnterpriseCertification enterpriseCertification) {
		this.enterpriseCertification = enterpriseCertification;
	}
	public Intention getIntention() {
		return intention;
	}
	public void setIntention(Intention intention) {
		this.intention = intention;
	}
	
	
}
