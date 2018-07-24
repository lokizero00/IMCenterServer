package com.loki.server.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class TradeComplex implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int userId;
	private String userNickName;
	private String sn;
	private String title;
	private String content;
	private String type;
	private String typeName;
	private String provinceName;
	private String cityName;
	private String townName;
	private int quantity;
	private Timestamp deliveryTime;
	private BigDecimal budget;
	private String resourceName;
	private int capacity;
	private String status;
	private String statusName;
	private BigDecimal intention;
	private int identityId;
	private int enterpriseId;
	private int dockingId;
	private int collectionCount;
	private int readCount;
	private int dockingCount;
	private String industryName;
	private String invoiceName;
	private String paycodeName;
	private String previewImageName;
	private List<TradeIndustry> tradeIndustryList;
	private List<TradeInvoice> tradeInvoiceList;
	private List<TradePaycode> tradePaycodeList;
	private List<TradeAttachment> tradeAttachmentList;
	private int collectionId;
	private String identifier;
	private String dockEnterpriseName;
	private String dockIdentityName;
	public String getDockIdentityName() {
		return dockIdentityName;
	}
	public void setDockIdentityName(String dockIdentityName) {
		this.dockIdentityName = dockIdentityName;
	}
	private BigDecimal dockOffer;
	private String publishEaseId;
	private String dockEaseId;
	
	private String identityName;
	private String enterpriseName;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public BigDecimal getBudget() {
		return budget;
	}
	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getIntention() {
		return intention;
	}
	public void setIntention(BigDecimal intention) {
		this.intention = intention;
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
	public int getDockingId() {
		return dockingId;
	}
	public void setDockingId(int dockingId) {
		this.dockingId = dockingId;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getInvoiceName() {
		return invoiceName;
	}
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	public String getPaycodeName() {
		return paycodeName;
	}
	public void setPaycodeName(String paycodeName) {
		this.paycodeName = paycodeName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public List<TradeIndustry> getTradeIndustryList() {
		return tradeIndustryList;
	}
	public void setTradeIndustryList(List<TradeIndustry> tradeIndustryList) {
		this.tradeIndustryList = tradeIndustryList;
	}
	public List<TradeInvoice> getTradeInvoiceList() {
		return tradeInvoiceList;
	}
	public void setTradeInvoiceList(List<TradeInvoice> tradeInvoiceList) {
		this.tradeInvoiceList = tradeInvoiceList;
	}
	public List<TradePaycode> getTradePaycodeList() {
		return tradePaycodeList;
	}
	public void setTradePaycodeList(List<TradePaycode> tradePaycodeList) {
		this.tradePaycodeList = tradePaycodeList;
	}
	public List<TradeAttachment> getTradeAttachmentList() {
		return tradeAttachmentList;
	}
	public void setTradeAttachmentList(List<TradeAttachment> tradeAttachmentList) {
		this.tradeAttachmentList = tradeAttachmentList;
	}
	public String getPreviewImageName() {
		return previewImageName;
	}
	public void setPreviewImageName(String previewImageName) {
		this.previewImageName = previewImageName;
	}
	public int getDockingCount() {
		return dockingCount;
	}
	public void setDockingCount(int dockingCount) {
		this.dockingCount = dockingCount;
	}
	public int getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}
	public int getCollectionCount() {
		return collectionCount;
	}
	public void setCollectionCount(int collectionCount) {
		this.collectionCount = collectionCount;
	}
	public String getIdentityName() {
		return identityName;
	}
	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getDockEnterpriseName() {
		return dockEnterpriseName;
	}
	public void setDockEnterpriseName(String dockEnterpriseName) {
		this.dockEnterpriseName = dockEnterpriseName;
	}
	public BigDecimal getDockOffer() {
		return dockOffer;
	}
	public void setDockOffer(BigDecimal dockOffer) {
		this.dockOffer = dockOffer;
	}
	public String getPublishEaseId() {
		return publishEaseId;
	}
	public void setPublishEaseId(String publishEaseId) {
		this.publishEaseId = publishEaseId;
	}
	public String getDockEaseId() {
		return dockEaseId;
	}
	public void setDockEaseId(String dockEaseId) {
		this.dockEaseId = dockEaseId;
	}
}
