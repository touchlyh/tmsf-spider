package org.yuanhong.li.tmsf;

import java.io.Serializable;
import java.util.List;

/**
 * 预售项目信息
 * @see http://www.tmsf.com/newhouse/OpenReport_show.htm
 * @author yuanhong.li
 * Date 2018-07-31
 */
public class ProjectInfo implements Serializable{

	private static final long serialVersionUID = 1503862367157838335L;
	
	private String projectUrl;

	private String  project;
	
	private String preSaleNum;
	
	private String loc;
	
	private String saleDate;
	
	private String company;
	
	private String address;
	
	private String saleAddress;
	
	private String total;
	
	public ProjectInfo(List<String> info) {
		this.projectUrl = info.get(0);
		this.project = info.get(1);
		this.preSaleNum = info.get(2);
		this.loc = info.get(3);
		this.saleDate = info.get(4);
		this.company = info.get(5);
		this.address = info.get(6);
		this.saleAddress = info.get(7);
		this.total = info.get(8);
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPreSaleNum() {
		return preSaleNum;
	}

	public void setPreSaleNum(String preSaleNum) {
		this.preSaleNum = preSaleNum;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSaleAddress() {
		return saleAddress;
	}

	public void setSaleAddress(String saleAddress) {
		this.saleAddress = saleAddress;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getProjectUrl() {
		return projectUrl;
	}

	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}

}
