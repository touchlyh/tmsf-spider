package org.yuanhong.li.tmsf;

import java.io.Serializable;

/**
 * 楼盘详细信息
 * @see http://www.tmsf.com/newhouse/presell_33_514158189_1164996755.htm
 * @author yuanhong.li
 * Date 2018-07-31
 */
public class TMSFModel implements Serializable{

	private static final long serialVersionUID = -1481374770106420829L;
	
	private String project;
	
	private String company;
	
	private String saleDate;
	
	private String loc;
	
	private String romNum;
	
	private String area;
	
	private String innerArea;
	
	private String acquireRate;
	
	private String price;
	
	private String fitPrice;
	
	private String totalPrice;
	
	private String saleStatus;
	
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getRomNum() {
		return romNum;
	}

	public void setRomNum(String romNum) {
		this.romNum = romNum;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getInnerArea() {
		return innerArea;
	}

	public void setInnerArea(String innerArea) {
		this.innerArea = innerArea;
	}

	public String getAcquireRate() {
		return acquireRate;
	}

	public void setAcquireRate(String acquireRate) {
		this.acquireRate = acquireRate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getFitPrice() {
		return fitPrice;
	}

	public void setFitPrice(String fitPrice) {
		this.fitPrice = fitPrice;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.project).append(",");
		sb.append(this.company).append(",");
		sb.append(this.saleDate).append(",");
		sb.append(this.loc).append(",");
		sb.append(this.romNum).append(",");
		sb.append(this.area).append(",");
		sb.append(this.innerArea).append(",");
		sb.append(this.acquireRate).append(",");
		sb.append(this.price).append(",");
		sb.append(this.fitPrice).append(",");
		sb.append(this.totalPrice).append(",");
		sb.append(this.saleStatus);
		return sb.toString();
	}

}
