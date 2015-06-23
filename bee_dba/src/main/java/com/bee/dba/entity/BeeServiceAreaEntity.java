package com.bee.dba.entity;

import java.io.Serializable;
import java.util.Date;

public class BeeServiceAreaEntity implements Serializable {


	private static final long serialVersionUID = 8699371291694795314L;
	private Integer id;
	private String areaId;
	private String areaName; 
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

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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

		BeeServiceAreaEntity that = (BeeServiceAreaEntity) o;

		if (areaId != null ? !areaId.equals(that.areaId) : that.areaId != null) return false;
		if (areaName != null ? !areaName.equals(that.areaName) : that.areaName != null) return false;
		if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (operatorId != null ? !operatorId.equals(that.operatorId) : that.operatorId != null) return false;
		if (status != null ? !status.equals(that.status) : that.status != null) return false;
		if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (areaId != null ? areaId.hashCode() : 0);
		result = 31 * result + (areaName != null ? areaName.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
		return result;
	}
}
