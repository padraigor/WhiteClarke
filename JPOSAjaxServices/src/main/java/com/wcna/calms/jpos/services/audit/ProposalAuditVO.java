package com.wcna.calms.jpos.services.audit;

import java.util.Date;

public class ProposalAuditVO {
	private String valueFrom;
	private String valueTo;
	private Date changedDate;
	private String changedBy;
	private String fieldType;

	private String change;
	private String appId;

	private String fieldName;
	private String moduleName;

	private Integer indexPosition;
	private Long refId;


	public void setValueFrom(String valueFrom) {
		this.valueFrom = valueFrom;
	}

	public String getValueFrom() {
		return valueFrom;
	}

	public void setValueTo(String valueTo) {
		this.valueTo = valueTo;
	}

	public String getValueTo() {
		return valueTo;
	}

	public void setChangedDate(Date changedDate) {
		this.changedDate = changedDate;
	}

	public Date getChangedDate() {
		return changedDate;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getChange() {
		return change;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppId() {
		return appId;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setIndexPosition(Integer indexPosition) {
		this.indexPosition = indexPosition;
	}

	public Integer getIndexPosition() {
		return indexPosition;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public Long getRefId() {
		return refId;
	}
}
