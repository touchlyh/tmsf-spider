package org.yuanhong.li.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.yuanhong.li.tmsf.ProjectInfo;
import org.yuanhong.li.tmsf.TMSFModel;

/**
 * 数据库持久化
 * 使用spring轻量级别的jdbcTemplate
 * @author yuanhong.li
 * Date 2018-07-31
 */
@Component("tmsfService")
public class TmsfService {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public boolean saveProjectInfo(List<ProjectInfo> projectList) {
		if(CollectionUtils.isEmpty(projectList)) {
			return false;
		}
		String sql = "insert into sp_project(project,project_url,pre_sale_num,loc,sale_date,company,address,sale_address,total) values(?,?,?,?,?,?,?,?,?)";
		for(ProjectInfo project : projectList) {
			final ProjectInfo info = project;
			PreparedStatementSetter ps = new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, info.getProject());
					ps.setString(2, info.getProjectUrl());
					ps.setString(3, info.getPreSaleNum());
					ps.setString(4, info.getLoc());
					ps.setString(5, info.getSaleDate());
					ps.setString(6, info.getCompany());
					ps.setString(7, info.getAddress());
					ps.setString(8, info.getSaleAddress());
					ps.setString(9, info.getTotal());
				}
			};
			jdbcTemplate.update(sql, ps);
		}
		return true;
	}
	
	public boolean saveSaleData(List<TMSFModel> models) {
		if(CollectionUtils.isEmpty(models)) {
			return false;
		}
		String sql = "insert into sp_project_sale(project,company,sale_date,loc,rom_num,area,inner_area,acquire_rate,price,fit_price,total_price,sale_status) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		for(TMSFModel model : models) {
			final TMSFModel info = model;
			PreparedStatementSetter ps = new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, info.getProject());
					ps.setString(2, info.getCompany());
					ps.setString(3, info.getSaleDate());
					ps.setString(4, info.getLoc());
					ps.setString(5, info.getRomNum());
					ps.setString(6, info.getArea());
					ps.setString(7, info.getInnerArea());
					ps.setString(8, info.getAcquireRate());
					ps.setString(9, info.getPrice());
					ps.setString(10, info.getFitPrice());
					ps.setString(11, info.getTotalPrice());
					ps.setString(12, info.getSaleStatus());
				}
			};
			jdbcTemplate.update(sql, ps);
		}
		return true;
	}
}
