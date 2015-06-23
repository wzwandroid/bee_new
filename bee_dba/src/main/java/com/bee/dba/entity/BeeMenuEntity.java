package com.bee.dba.entity;

import java.io.Serializable;
import java.util.Date;

public class BeeMenuEntity implements Serializable{


	private static final long serialVersionUID = -8066606858049053002L;
	private Integer id;
	private String code;
	private String parentCode;
	private String menuName;
	private String menuUrl;
	private String level;
	private String sort;
	private String status;
	private String operatorId;
	private String remark;
	private Date createTime;
    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BeeMenuEntity that = (BeeMenuEntity) o;

		if (code != null ? !code.equals(that.code) : that.code != null) return false;
		if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (level != null ? !level.equals(that.level) : that.level != null) return false;
		if (menuName != null ? !menuName.equals(that.menuName) : that.menuName != null) return false;
		if (menuUrl != null ? !menuUrl.equals(that.menuUrl) : that.menuUrl != null) return false;
		if (operatorId != null ? !operatorId.equals(that.operatorId) : that.operatorId != null) return false;
		if (parentCode != null ? !parentCode.equals(that.parentCode) : that.parentCode != null) return false;
		if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
		if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
		if (status != null ? !status.equals(that.status) : that.status != null) return false;
		if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (code != null ? code.hashCode() : 0);
		result = 31 * result + (parentCode != null ? parentCode.hashCode() : 0);
		result = 31 * result + (menuName != null ? menuName.hashCode() : 0);
		result = 31 * result + (menuUrl != null ? menuUrl.hashCode() : 0);
		result = 31 * result + (level != null ? level.hashCode() : 0);
		result = 31 * result + (sort != null ? sort.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
		result = 31 * result + (remark != null ? remark.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
		return result;
	}
}
