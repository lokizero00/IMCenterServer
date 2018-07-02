package com.loki.server.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.loki.server.entity.TradeReportAttachment;

public class TradeReportInformationDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int creatorId;
	private int updaterId;
	private int tradeReportId;
	private int tradeId;
	private String type;
	private String content;
	
	private String creatorName;
	private String updaterName;
	private String typeName;
	private List<TradeReportAttachment> attachmentList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public int getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(int updaterId) {
		this.updaterId = updaterId;
	}
	public int getTradeReportId() {
		return tradeReportId;
	}
	public void setTradeReportId(int tradeReportId) {
		this.tradeReportId = tradeReportId;
	}
	public int getTradeId() {
		return tradeId;
	}
	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getUpdaterName() {
		return updaterName;
	}
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<TradeReportAttachment> getAttachmentList() {
		return attachmentList;
	}
	public void setAttachmentList(List<TradeReportAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
}
