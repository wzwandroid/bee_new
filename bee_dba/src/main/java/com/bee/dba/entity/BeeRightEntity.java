package com.bee.dba.entity;

import java.io.Serializable;
import java.util.Date;

public class BeeRightEntity implements Serializable{


	private static final long serialVersionUID = 7342277849305667371L;
	private Integer id;
	private String roleLCode;
	private String menuCode;
	private String status;
	private String operatorId;
	private Date createTime;
    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleLCode() {
		return roleLCode;
	}

	public void setRoleLCode(String roleLCode) {
		this.roleLCode = roleLCode;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
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

		BeeRightEntity that = (BeeRightEntity) o;

		if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (menuCode != null ? !menuCode.equals(that.menuCode) : that.menuCode != null) return false;
		if (operatorId != null ? !operatorId.equals(that.operatorId) : that.operatorId != null) return false;
		if (roleLCode != null ? !roleLCode.equals(that.roleLCode) : that.roleLCode != null) return false;
		if (status != null ? !status.equals(that.status) : that.status != null) return false;
		if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (roleLCode != null ? roleLCode.hashCode() : 0);
		result = 31 * result + (menuCode != null ? menuCode.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
		return result;
	}
}
