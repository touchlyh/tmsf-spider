package org.yuanhong.li.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.yuanhong.li.mm.MMPageInfo;

@Component("mmPicService")
public class MMPicService {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public MMPageInfo getByHref(String href) {
		if(href == null) {
			return null;
		}
		String sql = "select * from mm_page_info where href=?";
		
		List<MMPageInfo> pageList =  jdbcTemplate.query(sql, new String[] {href}, new RowMapper<MMPageInfo>() {

			@Override
			public MMPageInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				MMPageInfo pageInfo = new MMPageInfo();
				pageInfo.setHref(rs.getString("href"));
				pageInfo.setPublish(rs.getString("publish"));
				pageInfo.setTitle(rs.getString("title"));
				pageInfo.setView(rs.getString("view_num"));
				return pageInfo;
			}
		});
		if(pageList == null || pageList.size()==0) {
			return null;
		}
		if(pageList.size() == 1) {
			return pageList.get(0);
		}
		throw new RuntimeException("data base md5sum found more than one record."+href);
	}
	
	public boolean addPageInfo(final MMPageInfo info) {
		String sql = "insert into mm_page_info(href,publish,title,view_num,gmt_create) values(?,?,?,?,now())";
		PreparedStatementSetter ps = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, info.getHref());
				ps.setString(2, info.getPublish());
				ps.setString(3, info.getTitle());
				ps.setString(4, info.getView());
			}
		};
		return jdbcTemplate.update(sql, ps) == 1;
	}
}
